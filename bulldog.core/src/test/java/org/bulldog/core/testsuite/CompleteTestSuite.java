package org.bulldog.core.testsuite;

import org.bulldog.core.TestSignal;
import org.bulldog.core.io.bus.TestBusConnection;
import org.bulldog.core.io.bus.serial.TestSerialDataEventArgs;
import org.bulldog.core.pinfeatures.base.TestAbstractAnalogInput;
import org.bulldog.core.pinfeatures.base.TestAbstractDigitalInput;
import org.bulldog.core.pinfeatures.base.TestAbstractDigitalOutput;
import org.bulldog.core.pinfeatures.base.TestAbstractPinFeature;
import org.bulldog.core.pinfeatures.base.TestAbstractPwm;
import org.bulldog.core.pinfeatures.base.TestDigitalIOFeature;
import org.bulldog.core.pinfeatures.base.TestPin;
import org.bulldog.core.pinfeatures.util.TestSoftPwm;
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
				TestDigitalIOFeature.class,
				TestAbstractAnalogInput.class,
				TestAbstractPwm.class,
				TestSignal.class,
				TestSoftPwm.class,
				TestBusConnection.class,
				TestBulldogUtil.class,
				TestBitMagic.class,
				TestSerialDataEventArgs.class
			  })
public class CompleteTestSuite {

}