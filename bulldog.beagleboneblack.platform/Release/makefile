################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.init

RM := rm -rf

# All of the sources participating in the build are defined here
-include sources.mk
-include src/main/c/jni/subdir.mk
-include src/main/c/bulldog/subdir.mk
-include src/main/c/BBBIOlib/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/sml6r3f25hmoj1hjc0ek5d0pj/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/6f9knjbt0vln184cn0n0cnms79/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/617ehd4151pq5drcfdvfmdhpc5/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/6094pqdc7e1pg208u1lemk6rkk/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/5lkpoajoli4apupuhsiv9u4a7s/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/4b374hja9347gt4ifo6ogn2oui/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/44ob5i9jur4fd2uf55h4epqnm1/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/422lg8k8nr958tnik7f09v6a9q/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/3pjq4gr1s2sph7rko83uerevq2/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/3gsl6sg2gkrfn04g62ecpkm6hr/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/3667vunebml35jigmsjf4b262o/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/2aspb5e5a6226fhrt9914v03tn/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/19jctokk9ahth9cfotjsfj649e/subdir.mk
-include build/objectFiles/bulldogSharedLibrary/bulldogC/16ac9656dkp8v7l3i4obf6p3qf/subdir.mk
-include subdir.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 

# All Target
all: libbulldog.beagleboneblack.platform.so

# Tool invocations
libbulldog.beagleboneblack.platform.so: $(OBJS) $(USER_OBJS)
	@echo 'Building target: $@'
	@echo 'Invoking: Cross ARM C Linker'
	arm-none-eabi-gcc -mcpu=cortex-a8 -mthumb -mfloat-abi=softfp -Os -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g -Xlinker --gc-sections -Wl,-Map,"bulldog.beagleboneblack.platform.map" -shared -o "libbulldog.beagleboneblack.platform.so" $(OBJS) $(USER_OBJS) $(LIBS)
	@echo 'Finished building target: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) $(OBJS)$(C_DEPS)$(ASM_DEPS)$(S_UPPER_DEPS) libbulldog.beagleboneblack.platform.so
	-@echo ' '

.PHONY: all clean dependents
.SECONDARY:

-include ../makefile.targets
