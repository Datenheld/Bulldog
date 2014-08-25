/*
 * (C) Copyright 2014 libbulldog (http://libbulldog.org/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.bulldog.core.gpio;

/**
 * The Interface DigitalIO is a composition of the
 * interfaces {@link DigitalInput} and {@link DigitalOutput}.
 *
 * The pin is dynamically configured as either an output or an input
 * depending on its usage. A call to read will configure it as an input,
 * a call to write will configure it as an output.
 *
 * The pin can also be dynamically reconfigured - it is not stuck
 * in its configuration but will rather adjust to the requested operation.
 *
 * @see DigitalInput
 * @see DigitalOutput
 */
public interface DigitalIO extends DigitalInput, DigitalOutput {

	/**
	 * Checks if the pin is currently used as an input.
	 *
	 * @return true, if the pin is currently used as an input.
	 */
	boolean isInputActive();

	/**
	 * Checks if the pin is currently used as an output.
	 *
	 * @return true, if the pin is currently used as an output.
	 */
	boolean isOutputActive();

}
