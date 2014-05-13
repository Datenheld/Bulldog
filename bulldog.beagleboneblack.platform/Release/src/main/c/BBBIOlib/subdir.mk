################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/main/c/BBBIOlib/BBBiolib.c \
../src/main/c/BBBIOlib/BBBiolib_ADCTSC.c \
../src/main/c/BBBIOlib/BBBiolib_McSPI.c \
../src/main/c/BBBIOlib/BBBiolib_PWMSS.c \
../src/main/c/BBBIOlib/test.c 

OBJS += \
./src/main/c/BBBIOlib/BBBiolib.o \
./src/main/c/BBBIOlib/BBBiolib_ADCTSC.o \
./src/main/c/BBBIOlib/BBBiolib_McSPI.o \
./src/main/c/BBBIOlib/BBBiolib_PWMSS.o \
./src/main/c/BBBIOlib/test.o 

C_DEPS += \
./src/main/c/BBBIOlib/BBBiolib.d \
./src/main/c/BBBIOlib/BBBiolib_ADCTSC.d \
./src/main/c/BBBIOlib/BBBiolib_McSPI.d \
./src/main/c/BBBIOlib/BBBiolib_PWMSS.d \
./src/main/c/BBBIOlib/test.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/c/BBBIOlib/%.o: ../src/main/c/BBBIOlib/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -Os -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g -std=gnu11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


