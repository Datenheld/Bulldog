################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeGpio.c \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeI2c.c \
../src/main/c/jni/org_bulldog_beagleboneblack_jni_NativePwm.c 

OBJS += \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeGpio.o \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeI2c.o \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativePwm.o 

C_DEPS += \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeGpio.d \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativeI2c.d \
./src/main/c/jni/org_bulldog_beagleboneblack_jni_NativePwm.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/c/jni/%.o: ../src/main/c/jni/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -Os -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g -std=gnu11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


