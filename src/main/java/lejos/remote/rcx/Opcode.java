package lejos.remote.rcx;

/**
 * Opcode constants.
 * 
 */
public interface Opcode
{
  byte OPCODE_MASK = (byte)0xf7;

  byte OPCODE_ALIVE = (byte)0x10;
  byte OPCODE_GET_VALUE = (byte)0x12;
  byte OPCODE_SET_MOTOR_POWER = (byte)0x13;
  byte OPCODE_SET_VARIABLE = (byte)0x14;
  byte OPCODE_GET_VERSIONS = (byte)0x15;
  byte OPCODE_SET_MOTOR_DIRECTION_REPLY = (byte)0x16;
  byte OPCODE_CALL_SUBROUTINE = (byte)0x17;

  byte OPCODE_GET_MEMORY_MAP = (byte)0x20;
  byte OPCODE_SET_MOTOR_ON_OFF = (byte)0x21;
  byte OPCODE_SET_TIME = (byte)0x22;
  byte OPCODE_PLAY_TONE = (byte)0x23;
  byte OPCODE_ADD_TO_VARIABLE = (byte)0x24;
  byte OPCODE_START_TASK_DOWNLOAD = (byte)0x25;
  byte OPCODE_CLEAR_SENSOR_VALUE_REPLY = (byte)0x26;
  byte OPCODE_BRANCH_ALWAYS_NEAR = (byte)0x27;

  byte OPCODE_GET_BATTERY_POWER = (byte)0x30;
  byte OPCODE_SET_TRANSMITTER_RANGE = (byte)0x31;
  byte OPCODE_SET_SENSOR_TYPE = (byte)0x32;
  byte OPCODE_SET_DISPLAY = (byte)0x33;
  byte OPCODE_SUBTRACT_FROM_VARIABLE = (byte)0x34;
  byte OPCODE_START_SUBROUTINE_DOWNLOAD = (byte)0x35;
  byte OPCODE_DELETE_SUBROUTINE_REPLY = (byte)0x36;
  byte OPCODE_DECREMENT_LOOP_COUNTER_NEAR = (byte)0x37;

  byte OPCODE_DELETE_ALL_TASKS = (byte)0x40;
  byte OPCODE_SET_SENSOR_MODE = (byte)0x42;
  byte OPCODE_WAIT = (byte)0x43;
  byte OPCODE_DIVIDE_VARIABLE = (byte)0x44;
  byte OPCODE_TRANSFER_DATA = (byte)0x45;
  byte OPCODE_SET_POWER_DOWN_DELAY_REPLY = (byte)0x46;

  byte OPCODE_STOP_ALL_TASKS = (byte)0x50;
  byte OPCODE_SET_DATALOG_SIZE = (byte)0x52;
  byte OPCODE_UNLOCK_FIRMWARE_REPLY = (byte)0x52;
  byte OPCODE_UPLOAD_DATALOG_REPLY = (byte)0x53;
  byte OPCODE_MULTIPLY_VARIABLE = (byte)0x54;
  byte OPCODE_CLEAR_TIMER_REPLY = (byte)0x56;

  byte OPCODE_POWER_OFF = (byte)0x60;
  byte OPCODE_DELETE_TASK = (byte)0x61;
  byte OPCODE_DATALOG_NEXT = (byte)0x62;
  byte OPCODE_OR_VARIABLE_REPLY = (byte)0x63;
  byte OPCODE_SIGN_VARIABLE = (byte)0x64;
  byte OPCODE_DELETE_FIRMWARE = (byte)0x65;
  byte OPCODE_SET_PROGRAM_NUMBER_REPLY = (byte)0x66;

  byte OPCODE_DELETE_ALL_SUBROUTINES = (byte)0x70;
  byte OPCODE_START_TASK = (byte)0x71;
  byte OPCODE_BRANCH_ALWAYS_FAR = (byte)0x72;
  byte OPCODE_AND_VARIABLE_REPLY = (byte)0x73;
  byte OPCODE_ABSOLUTE_VALUE = (byte)0x74;
  byte OPCODE_START_FIRMWARE_DOWNLOAD = (byte)0x75;
  byte OPCODE_STOP_TASK_REPLY = (byte)0x76;

  byte OPCODE_STOP_TASK = (byte)0x81;
  byte OPCODE_START_FIRMWARE_DOWNLOAD_REPLY = (byte)0x82;
  byte OPCODE_SET_LOOP_COUNTER = (byte)0x82;
  byte OPCODE_ABSOLUTE_VALUE_REPLY = (byte)0x83;
  byte OPCODE_AND_VARIABLE = (byte)0x84;
  byte OPCODE_TEST_AND_BRANCH_NEAR = (byte)0x85;
  byte OPCODE_START_TASK_REPLY = (byte)0x86;
  byte OPCODE_DELETE_ALL_SUBROUTINES_REPLY = (byte)0x87;

  byte OPCODE_CLEAR_MESSAGE = (byte)0x90;
  byte OPCODE_SET_PROGRAM_NUMBER = (byte)0x91;
  byte OPCODE_DELETE_FIRMWARE_REPLY = (byte)0x92;
  byte OPCODE_DECREMENT_LOOP_COUNTER_FAR = (byte)0x92;
  byte OPCODE_SIGN_VARIABLE_REPLY = (byte)0x93;
  byte OPCODE_OR_VARIABLE = (byte)0x94;
  byte OPCODE_DATALOG_NEXT_REPLY = (byte)0x95;
  byte OPCODE_TEST_AND_BRANCH_FAR = (byte)0x95;
  byte OPCODE_DELETE_TASK_REPLY = (byte)0x96;
  byte OPCODE_POWER_OFF_REPLY = (byte)0x97;

  byte OPCODE_CLEAR_TIMER = (byte)0xa1;
  byte OPCODE_MULTIPLY_VARIABLE_REPLY = (byte)0xa3;
  byte OPCODE_UPLOAD_DATALOG = (byte)0xa4;
  byte OPCODE_UNLOCK_FIRMWARE = (byte)0xa5;
  byte OPCODE_SET_DATALOG_SIZE_REPLY = (byte)0xa5;
  byte OPCODE_PLAY_SOUND_REPLY = (byte)0xa6;
  byte OPCODE_STOP_ALL_TASKS_REPLY = (byte)0xa7;

  byte OPCODE_SET_POWER_DOWN_DELAY = (byte)0xb1;
  byte OPCODE_TRANSFER_DATA_REPLY = (byte)0xb2;
  byte OPCODE_SEND_MESSAGE = (byte)0xb2;
  byte OPCODE_DIVIDE_VARIABLE_REPLY = (byte)0xb3;
  byte OPCODE_SET_SENSOR_MODE_REPLY = (byte)0xb5;
  byte OPCODE_DELETE_ALL_TASKS_REPLY = (byte)0xb7;

  byte OPCODE_DELETE_SUBROUTINE = (byte)0xc1;
  byte OPCODE_START_SUBROUTINE_DOWNLOAD_REPLY = (byte)0xc2;
  byte OPCODE_SUBTRACT_FROM_VARIABLE_REPLY = (byte)0xc3;
  byte OPCODE_SET_DISPLAY_REPLY = (byte)0xc4;
  byte OPCODE_SET_SENSOR_TYPE_REPLY = (byte)0xc5;
  byte OPCODE_SET_TRANSMITTER_RANGE_REPLY = (byte)0xc6;
  byte OPCODE_GET_BATTERY_POWER_REPLY = (byte)0xc7;

  byte OPCODE_CLEAR_SENSOR_VALUE = (byte)0xd1;
  byte OPCODE_START_TASK_DOWNLOAD_REPLY = (byte)0xd2;
  byte OPCODE_REMOTE_COMMAND = (byte)0xd2;
  byte OPCODE_ADD_TO_VARIABLE_REPLY = (byte)0xd3;
  byte OPCODE_PLAY_TONE_REPLY = (byte)0xd4;
  byte OPCODE_SET_TIME_REPLY = (byte)0xd5;
  byte OPCODE_SET_MOTOR_ON_OFF_REPLY = (byte)0xd6;
  byte OPCODE_GET_MEMORY_MAP_REPLY = (byte)0xd7;

  byte OPCODE_SET_MOTOR_DIRECTION = (byte)0xe1;
  byte OPCODE_GET_VERSIONS_REPLY = (byte)0xe2;
  byte OPCODE_SET_VARIABLE_REPLY = (byte)0xe3;
  byte OPCODE_SET_MOTOR_POWER_REPLY = (byte)0xe4;
  byte OPCODE_GET_VALUE_REPLY = (byte)0xe5;
  byte OPCODE_ALIVE_REPLY = (byte)0xe7;

  byte OPCODE_SET_MESSAGE = (byte)0xf7;
}


