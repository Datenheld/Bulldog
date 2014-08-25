package org.bulldog.devices.sensors;

import java.io.IOException;

import org.bulldog.core.io.bus.i2c.I2cBus;
import org.bulldog.core.io.bus.i2c.I2cConnection;
import org.bulldog.core.io.bus.i2c.I2cDevice;
import org.bulldog.core.util.BitMagic;
import org.bulldog.core.util.BulldogUtil;

public class BH1750LightIntensitySensor extends I2cDevice {

	public static final int MODE_HIGH_RES_1LX_CONTINUOUS 	= 0b00010000;
	public static final int MODE_HIGH_RES_1LX_ONE_TIME 		= 0b00100000;
	public static final int MODE_HIGH_RES_05_LX_CONTINUOUS 	= 0b00010001;
	public static final int MODE_HIGH_RES_05_LX_ONE_TIME 	= 0b00100001;
	public static final int MODE_LOW_RES_4LX_CONTINUOUS 	= 0b00010011;
	public static final int MODE_LOW_RES_4LX_ONE_TIME 		= 0b00100011;
	
	public static final int COMMAND_POWER_OFF = 0b00000000;
	public static final int COMMAND_POWER_ON  = 0b00000001;
	public static final int COMMAND_RESET 	  = 0b00000111;
	
	
	private int activeMode = 0;
	
	public BH1750LightIntensitySensor(I2cBus bus, int address) {
		super(bus, address);
	}

	public BH1750LightIntensitySensor(I2cConnection connection) {
		super(connection);
	}
	
	public void initMode(int mode) throws IOException {
		getBusConnection().writeByte(mode);
		activeMode = mode;
	}

	public double readLuminance() throws IOException {
		if(BitMagic.isBitSet(activeMode, 6)) {
			getBusConnection().writeByte(COMMAND_POWER_ON);
			getBusConnection().writeByte(activeMode);
		}
		
		byte[] buffer = new byte[2];
		getBusConnection().readBytes(buffer);
		int intValue = (((BulldogUtil.getUnsignedByte(buffer[0]) << 8)) | BulldogUtil.getUnsignedByte(buffer[1]));
		double value = intValue / 1.2;
		return value;
	}
	
	public double readLuminanceNormalized() throws IOException {
		double luminance = readLuminance();
		double normalized =  Math.log10(luminance) / 5.0;
		if(normalized < 0 || Double.isInfinite(normalized)) {
			return 0.0;
		}
		
		return normalized;
	}
}
