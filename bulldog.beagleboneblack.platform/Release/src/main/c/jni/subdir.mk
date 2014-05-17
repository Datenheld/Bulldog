################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeAdc.c \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeEpoll.c \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeGpio.c \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeI2c.c \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativePwm.c 

OBJS += \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeAdc.o \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeEpoll.o \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeGpio.o \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeI2c.o \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativePwm.o 

C_DEPS += \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeAdc.d \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeEpoll.d \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeGpio.d \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeI2c.d \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativePwm.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/c/jni/%.o: ../src/main/c/jni/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-a8 -mthumb -mfloat-abi=softfp -Os -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g -I"E:\Projects\Java\github\Bulldog\bulldog.tools\jdk\jdk1.7.0_55_arm_softfp\include" -I"E:\Projects\Java\github\Bulldog\bulldog.tools\jdk\jdk1.7.0_55_arm_softfp\include\linux" -std=gnu11 -O3 -fPIC -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


