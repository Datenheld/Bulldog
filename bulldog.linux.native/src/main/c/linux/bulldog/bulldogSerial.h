#ifndef BULLDOG_SERIAL_H
#define BULLDOG_SERIAL_H

#ifdef __cplusplus
extern "C" {
#endif

#define SERIAL_BLOCK 			1
#define SERIAL_NO_BLOCK 		0
#define SERIAL_DEFAULT_TIMEOUT	5

extern int serialOpen(char* portname, int baud, int parity, int blocking, int readTimeout, int dataBits, int stopBits);
extern int serialOpenSimple(char* portname, int baud);
extern int serialClose(int fd);

extern int serialWriteCharacter(int fileDescriptor, unsigned char data);
extern int serialWriteBuffer(int fileDescriptor, char* data);

extern int serialReadBuffer(int fileDescriptor, char* buffer, int bufferSize);
extern unsigned char serialReadCharacter(int fileDescriptor);
extern int serialDataAvailable(int fd);

extern int serialSetAttributes(int fd, int speed, int parity, int readTimeout, int dataBits, int stopBits);
extern void serialSetBlocking(int fd, int block, int readTimeout);

#ifdef __cplusplus
}
#endif

#endif

