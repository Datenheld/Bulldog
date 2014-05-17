package org.bulldog.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
				TestAbstractBoard.class, 
				TestPin.class 
			  })
public class CompleteTestSuite {

}