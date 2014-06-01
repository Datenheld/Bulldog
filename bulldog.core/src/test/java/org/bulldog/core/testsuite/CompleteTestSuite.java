package org.bulldog.core.testsuite;

import org.bulldog.core.TestSignal;
import org.bulldog.core.bus.TestBusConnection;
import org.bulldog.core.gpio.TestAbstractAnalogInput;
import org.bulldog.core.gpio.TestAbstractDigitalInput;
import org.bulldog.core.gpio.TestAbstractDigitalOutput;
import org.bulldog.core.gpio.TestAbstractPinFeature;
import org.bulldog.core.gpio.TestAbstractPwm;
import org.bulldog.core.gpio.TestPin;
import org.bulldog.core.platform.TestAbstractBoard;
import org.bulldog.core.platform.TestAbstractPinProvider;
import org.bulldog.core.util.TestBitMagic;
import org.bulldog.core.util.TestBulldogUtil;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
				TestAbstractPinProvider.class,
				TestAbstractBoard.class, 
				TestPin.class,
				TestAbstractPinFeature.class,
				TestAbstractDigitalOutput.class,
				TestAbstractDigitalInput.class,
				TestAbstractAnalogInput.class,
				TestAbstractPwm.class,
				TestSignal.class,
				TestBusConnection.class,
				TestBulldogUtil.class,
				TestBitMagic.class
			  })
public class CompleteTestSuite {

}