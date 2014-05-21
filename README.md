Bulldog
=======

Bulldog is a GPIO API for the Beaglebone Black written in Java.

It is currently under development, but many features are already usable. If you're interested in the sources, visit the Devline1 branch. Yet, it is far from being a fully fledged API.

**Intention :**

Bulldog provides GPIO and low-level IO capabilities for embedded linux systems. It currently only supports the BeagleBone Black but is written with portability in mind.

  It's major concept for GPIO is the PinFeature API.
```java
  DigitalOutput output = board.getPin("P8_12").as(DigitalOutput.class);
  DigitalInput input = board.getPin("P8_11").as(DigitalInput.class);
```

See the __bulldog.examples__ project to get an idea.

That way, the responsibilities are encapsulated and we don't have a Pin-Class that takes too many responsibilities. It is also easily extensible.

**Supports :**

Bulldog currently supports the following features on the BeagleBoneBlack:
 1. Digital Input/Output on Pins
 2. Native Interrupts via epoll (easily usable on DigitalInputs)
 3. Native PWM
 4. Native ADC
 5. I2c
 6. All UARTs (including dynamic setup via capemgr on request)
 7. A few devices: Simple button API, Incremental Rotary Encoder, Servos

**Build :**

You'll need gradle installed and a cross compiler for arm. You'll have to adjust the toolchain path in bulldog.build/gradle.properties
Afterwards, just run this command to build the Beaglebone Black distribution 
```java
   gradle bulldogSharedLibrary build bbbSingleJar
```
It will produce a single jar called bulldog-beagleboneblack.jar and a native library called "libbulldog-linux.so". The latter can be found in the bulldog.linux.native build directory.
    
**Usage :**

You can just reference the jar in your classpath. Make sure the native library (libbulldog-linux.so) is in the same directory as the jar. You can find the most recent binaries in the ```dist``` directory.

For API usage see the __bulldog.examples__ project.


**External reference :**

To achieve high pin toggling speeds and maximum performance, it uses VegetableAvenger's excellent BBIOlib: https://github.com/VegetableAvenger/BBBIOlib
The only working lib that was found for the 3.8 kernel!

Thanks VegetableAvenger!

