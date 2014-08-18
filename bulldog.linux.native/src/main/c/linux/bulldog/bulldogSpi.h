#ifndef BULLDOG_SPI_H
#define BULLDOG_SPI_H

#ifdef __cplusplus
extern "C" {
#endif

extern int spiOpen(char* busDevice, int mode, int speed, int bitsPerWord, int lsbFirst);
extern int spiConfig(int fileDescriptor, int mode, int speed, int bitsPerWord, int lsbFirst);
extern int spiTransfer(int fileDescriptor, unsigned int* txBuffer, unsigned int* rxBuffer, int transferLength, int delay, int speed, int bitsPerWord);
extern int spiClose(int fileDescriptor);

#ifdef __cplusplus
}
#endif

#endif

