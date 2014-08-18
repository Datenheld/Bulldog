#ifndef BULLDOG_I2C_H
#define BULLDOG_I2C_H


#ifdef __cplusplus
extern "C" {
#endif

extern unsigned char i2cRead(int fileDescriptor);
extern int i2cWrite(int fileDescriptor, unsigned char data);
extern int i2cSelectSlave(int fd, int slaveAddress);
extern int i2cOpen(char* busDevice);
extern int i2cClose(int fileDescriptor);

#ifdef __cplusplus
}
#endif

#endif
