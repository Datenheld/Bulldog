################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/main/c/i2c/i2c.c 

OBJS += \
./src/main/c/i2c/i2c.o 

C_DEPS += \
./src/main/c/i2c/i2c.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/c/i2c/%.o: ../src/main/c/i2c/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-a8 -mthumb -mfloat-abi=softfp -Os -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g -I"E:\Projects\Java\github\Bulldog\bulldog.tools\jdk\jdk1.7.0_55_arm_softfp\include" -I"E:\Projects\Java\github\Bulldog\bulldog.tools\jdk\jdk1.7.0_55_arm_softfp\include\linux" -std=gnu11 -O3 -fPIC -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


