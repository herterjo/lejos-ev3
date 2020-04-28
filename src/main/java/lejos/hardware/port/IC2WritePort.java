package lejos.hardware.port;

public interface IC2WritePort {
    /**
     * High level i2c interface. Perform a complete i2c transaction in fire and forget.
     * Writes the specified data to the device.
     *
     * @param deviceAddress The I2C device address.
     * @param writeBuf      The buffer containing data to be written to the device.
     * @param writeOffset   The offset of the data within the write buffer
     * @param writeLen      The number of bytes to write.
     */
    public void i2cWrite(int deviceAddress, byte[] writeBuf, int writeOffset, int writeLen);
}
