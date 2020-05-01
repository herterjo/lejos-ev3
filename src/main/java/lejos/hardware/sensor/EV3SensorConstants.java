package lejos.hardware.sensor;

/**
 * Basic constants for use when accessing EV3 Sensors.
 * @author andy
 *
 */
public interface EV3SensorConstants
{
    int PORTS = 4;
    int MOTORS = 4;
    
    int CONN_UNKNOWN    = 111;  //!< Connection is fake (test)
    int CONN_DAISYCHAIN = 117;  //!< Connection is daisy chained
    int CONN_NXT_COLOR  = 118;  //!< Connection type is NXT color sensor
    int CONN_NXT_DUMB   = 119;  //!< Connection type is NXT analog sensor
    int CONN_NXT_IIC    = 120;  //!< Connection type is NXT IIC sensor
    int CONN_INPUT_DUMB = 121;  //!< Connection type is LMS2012 input device with ID resistor
    int CONN_INPUT_UART = 122;  //!< Connection type is LMS2012 UART sensor
    int CONN_OUTPUT_DUMB= 123;  //!< Connection type is LMS2012 output device with ID resistor^
    int CONN_OUTPUT_INTELLIGENT= 124;  //!< Connection type is LMS2012 output device with communication
    int CONN_OUTPUT_TACHO= 125;  //!< Connection type is LMS2012 tacho motor with series ID resistance
    int CONN_NONE       = 126;  //!< Port empty or not available
    int CONN_ERROR      = 127;  //!< Port not empty and type is invalid^M

    int TYPE_NXT_TOUCH                =   1;  //!< Device is NXT touch sensor
    int TYPE_NXT_LIGHT                =   2;  //!< Device is NXT light sensor
    int TYPE_NXT_SOUND                =   3;  //!< Device is NXT sound sensor
    int TYPE_NXT_COLOR                =   4;  //!< Device is NXT color sensor
    int TYPE_TACHO                    =   7;  //!< Device is a tacho motor
    int TYPE_MINITACHO                =   8;  //!< Device is a mini tacho motor
    int TYPE_NEWTACHO                 =   9;  //!< Device is a new tacho motor
    int TYPE_THIRD_PARTY_START        =  50;
    int TYPE_THIRD_PARTY_END          =  99;
    int TYPE_IIC_UNKNOWN              = 100;
    int TYPE_NXT_TEST                 = 101;  //!< Device is a NXT ADC test sensor
    int TYPE_NXT_IIC                  = 123;  //!< Device is NXT IIC sensor
    int TYPE_TERMINAL                 = 124;  //!< Port is connected to a terminal
    int TYPE_UNKNOWN                  = 125;  //!< Port not empty but type has not been determined
    int TYPE_NONE                     = 126;  //!< Port empty or not available
    int TYPE_ERROR                    = 127;  //!< Port not empty and type is invalid


    int UART_MAX_MODES = 8;
    int MAX_DEVICE_DATALENGTH = 32;
    int IIC_DATA_LENGTH = MAX_DEVICE_DATALENGTH;
    int STATUS_OK = 0;
    int STATUS_BUSY = 1;
    int STATUS_FAIL = 2;
    int STATUS_STOP = 4;
    
    byte CMD_NONE = (byte)'-';
    byte CMD_FLOAT = (byte)'f';
    byte CMD_SET = (byte)'0';
    byte CMD_AUTOMATIC = (byte) 'a';
    byte CMD_CONNECTED = (byte) 'c';
    byte CMD_DISCONNECTED = (byte) 'd';
    byte CMD_COL_COL = 0xd;
    byte CMD_COL_RED = 0xe;
    byte CMD_COL_GRN = 0xf;
    byte CMD_COL_BLU = 0x11;
    byte CMD_COL_AMB = 0x12;
    byte CMD_PIN1 = 0x1;
    byte CMD_PIN5 = 0x2;
    
    float ADC_REF = 5.0f; // 5.0 Volts
    int ADC_RES = 4095;
}
