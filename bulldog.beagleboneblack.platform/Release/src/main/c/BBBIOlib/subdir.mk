################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/main/c/BBBIOlib/BBBiolib.c \
../src/main/c/BBBIOlib/BBBiolib_ADCTSC.c \
../src/main/c/BBBIOlib/BBBiolib_McSPI.c \
../src/main/c/BBBIOlib/BBBiolib_PWMSS.c 

OBJS += \
./src/main/c/BBBIOlib/BBBiolib.o \
./src/main/c/BBBIOlib/BBBiolib_ADCTSC.o \
./src/main/c/BBBIOlib/BBBiolib_McSPI.o \
./src/main/c/BBBIOlib/BBBiolib_PWMSS.o 

C_DEPS += \
./src/main/c/BBBIOlib/BBBiolib.d \
./src/main/c/BBBIOlib/BBBiolib_ADCTSC.d \
./src/main/c/BBBIOlib/BBBiolib_McSPI.d \
./src/main/c/BBBIOlib/BBBiolib_PWMSS.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/c/BBBIOlib/%.o: ../src/main/c/BBBIOlib/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-a8 -mthumb -mfloat-abi=softfp -Os -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g -I"E:\Projects\Java\github\Bulldog\bulldog.tools\jdk\jdk1.7.0_55_arm_softfp\include" -I"E:\Projects\Java\github\Bulldog\bulldog.tools\jdk\jdk1.7.0_55_arm_softfp\include\linux" -std=gnu11 -O3 -fPIC -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


