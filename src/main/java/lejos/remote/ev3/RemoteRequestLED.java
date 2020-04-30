package lejos.remote.ev3;

import lejos.hardware.LED;
import lejos.utility.AsyncExecutor;
import lejos.utility.ExceptionWrapper;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Future;

public class RemoteRequestLED implements LED {
    private ObjectOutputStream os;
    private ObjectInputStream is;

    public RemoteRequestLED(ObjectInputStream is, ObjectOutputStream os) {
        this.os = os;
        this.is = is;
    }

    @Override
    public Future<ExceptionWrapper> setPattern(int pattern) {
        EV3Request req = new EV3Request();
        req.request = EV3Request.Request.LED_PATTERN;
        req.intValue = pattern;
        return AsyncExecutor.execute(() -> {
            sendRequest(req, false);
        });
    }

    private EV3Reply sendRequest(EV3Request req, boolean replyRequired) {
        EV3Reply reply = null;
        req.replyRequired = replyRequired;
        try {
            os.reset();
            os.writeObject(req);
            if (replyRequired) {
                reply = (EV3Reply) is.readObject();
                if (reply.e != null) throw new RemoteRequestException(reply.e);
            }
            return reply;
        } catch (Exception e) {
            throw new RemoteRequestException(e);
        }
    }
}
