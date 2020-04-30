package lejos.remote.nxt;

/**
 * LEGO Communication Protocol constants.
 *
 */
public interface NXTProtocol {
	// Command types constants. Indicates type of packet being sent or received.
    byte DIRECT_COMMAND_REPLY = 0x00;
	byte SYSTEM_COMMAND_REPLY = 0x01;
	byte REPLY_COMMAND = 0x02;
	byte DIRECT_COMMAND_NOREPLY = (byte)0x80; // Avoids ~100ms latency
	byte SYSTEM_COMMAND_NOREPLY = (byte)0x81; // Avoids ~100ms latency
	
	// System Commands:
    byte OPEN_READ = (byte)0x80;
	byte OPEN_WRITE = (byte)0x81;
	byte READ = (byte)0x82;
	byte WRITE = (byte)0x83;
	byte CLOSE = (byte)0x84;
	byte DELETE = (byte)0x85;
	byte FIND_FIRST = (byte)0x86;
	byte FIND_NEXT = (byte)0x87;
	byte GET_FIRMWARE_VERSION = (byte)0x88;
	byte OPEN_WRITE_LINEAR = (byte)0x89;
	byte OPEN_READ_LINEAR = (byte)0x8A;
	byte OPEN_WRITE_DATA = (byte)0x8B;
	byte OPEN_APPEND_DATA = (byte)0x8C;
	// Many commands could be hidden between 0x8D and 0x96!
    byte BOOT = (byte)0x97;
	byte SET_BRICK_NAME = (byte)0x98;
	// public static final byte MYSTERY_COMMAND = (byte)0x99;
	// public static final byte MYSTERY_COMMAND = (byte)0x9A;
    byte GET_DEVICE_INFO = (byte)0x9B;
	// commands could be hidden here...
    byte DELETE_USER_FLASH = (byte)0xA0;
	byte POLL_LENGTH = (byte)0xA1;
	byte POLL = (byte)0xA2;
	
	// Poll constants:
    byte POLL_BUFFER = (byte)0x00;
	byte HIGH_SPEED_BUFFER = (byte)0x01;
		
	// Direct Commands
    byte START_PROGRAM = 0x00;
	byte STOP_PROGRAM = 0x01;
	byte PLAY_SOUND_FILE = 0x02;
	byte PLAY_TONE = 0x03;
	byte SET_OUTPUT_STATE = 0x04;
	byte SET_INPUT_MODE = 0x05;
	byte GET_OUTPUT_STATE = 0x06;
	byte GET_INPUT_VALUES = 0x07;
	byte RESET_SCALED_INPUT_VALUE = 0x08;
	byte MESSAGE_WRITE = 0x09;
	byte RESET_MOTOR_POSITION = 0x0A;
	byte GET_BATTERY_LEVEL = 0x0B;
	byte STOP_SOUND_PLAYBACK = 0x0C;
	byte KEEP_ALIVE = 0x0D;
	byte LS_GET_STATUS = 0x0E;
	byte LS_WRITE = 0x0F;
	byte LS_READ = 0x10;
	byte GET_CURRENT_PROGRAM_NAME = 0x11;
	// public static final byte MYSTERY_OPCODE = 0x12; // ????
    byte MESSAGE_READ = 0x13;
	// public static final byte POSSIBLY_MORE_HIDDEN = 0x14; // ????
	
	// NXJ additions
	// TODO NXJ commands should be direct commands.
	// At the moment, the NXJ commands are system commands. However, the LEGO firmware
	// does not respond with an error code to unknown system commands. Instead, it seems to
	// return the reply of the last successful command or something similar.
	// Hence, the NXJ opcodes should be declared as direct commands. The LEGO firmware will
	// reply with error 0xBE (Unknown command opcode) for all direct command
	// opcodes from 0x22 to 0xff (tested with Firmware 1.31). I suggest the usage of
	// opcodes >= 0x80.
    byte NXJ_DISCONNECT = 0x20;
	byte NXJ_DEFRAG = 0x21;
	byte NXJ_SET_DEFAULT_PROGRAM = 0x22;
	byte NXJ_SET_SLEEP_TIME = 0x23;
	byte NXJ_SET_VOLUME = 0x24;
	byte NXJ_SET_KEY_CLICK_VOLUME = 0x25;
	byte NXJ_SET_AUTO_RUN = 0x26;
	byte NXJ_GET_VERSION = 0x27;
	byte NXJ_GET_DEFAULT_PROGRAM = 0x28;
	byte NXJ_GET_SLEEP_TIME = 0x29;
	byte NXJ_GET_VOLUME = 0x2A;
	byte NXJ_GET_KEY_CLICK_VOLUME = 0x2B;
	byte NXJ_GET_AUTO_RUN = 0x2C;
    byte NXJ_PACKET_MODE = (byte)0xff;
		
	// Output state constants 
	// "Mode":
	/** Turn on the specified motor */
    byte MOTORON = 0x01;
	/** Use run/brake instead of run/float in PWM */
    byte BRAKE = 0x02;
	/** Turns on the regulation */
    byte REGULATED = 0x04;

	// "Regulation Mode":
	/** No regulation will be enabled */
    byte REGULATION_MODE_IDLE = 0x00;
	/** Power control will be enabled on specified output */
    byte REGULATION_MODE_MOTOR_SPEED = 0x01;
	/** Synchronization will be enabled (Needs enabled on two output) */
    byte REGULATION_MODE_MOTOR_SYNC = 0x02;

	// "RunState":
	/** Output will be idle */
    byte MOTOR_RUN_STATE_IDLE = 0x00;
	/** Output will ramp-up */
    byte MOTOR_RUN_STATE_RAMPUP = 0x10;
	/** Output will be running */
    byte MOTOR_RUN_STATE_RUNNING = 0x20;
	/** Output will ramp-down */
    byte MOTOR_RUN_STATE_RAMPDOWN = 0x40;
	
	// Input Mode Constants
	// "Port Type":
	/**  */
    byte NO_SENSOR = 0x00;
	/**  */
    byte SWITCH = 0x01;
	/**  */
    byte TEMPERATURE = 0x02;
	/**  */
    byte REFLECTION = 0x03;
	/**  */
    byte ANGLE = 0x04;
	/**  */
    byte LIGHT_ACTIVE = 0x05;
	/**  */
    byte LIGHT_INACTIVE = 0x06;
	/**  */
    byte SOUND_DB = 0x07;
	/**  */
    byte SOUND_DBA = 0x08;
	/**  */
    byte CUSTOM = 0x09;
	/**  */
    byte LOWSPEED = 0x0A;
	/**  */
    byte LOWSPEED_9V = 0x0B;
	/**  */
    byte NO_OF_SENSOR_TYPES = 0x0C;

	// "Port Mode":
	/**  */
    byte RAWMODE = 0x00;
	/**  */
    byte BOOLEANMODE = 0x20;
	/**  */
    byte TRANSITIONCNTMODE = 0x40;
	/**  */
    byte PERIODCOUNTERMODE = 0x60;
	/**  */
    byte PCTFULLSCALEMODE = (byte)0x80;
	/**  */
    byte CELSIUSMODE = (byte)0xA0;
	/**  */
    byte FAHRENHEITMODE = (byte)0xC0;
	/**  */
    byte ANGLESTEPSMODE = (byte)0xE0;
	/**  */
    byte SLOPEMASK = 0x1F;
	/**  */
    byte MODEMASK = (byte)0xE0;
}


