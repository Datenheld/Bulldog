/*
 * SPI testing utility (using spidev driver)
 *
 * Copyright (c) 2007  MontaVista Software, Inc.
 * Copyright (c) 2007  Anton Vorontsov <avorontsov@ru.mvista.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * Cross-compile with cross-gcc -I/path/to/cross-kernel/include
 */

#include <stdint.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/types.h>
#include <linux/spi/spidev.h>
#include <string.h>
#include <errno.h>
#include "bulldog.h"
#include "bulldogSpi.h"

int spiOpen(char* busDevice, int mode, int speed, int bitsPerWord, int lsbFirst) {
	int fd;
	int ret;

	if ((fd = open (busDevice, O_RDWR)) < 0) {
		errorMessage("open failed: %s", strerror(errno));
		return -1;
	}

	if(spiConfig(fd, mode, speed, bitsPerWord, lsbFirst) < 0) {
		errorMessage("open failed: %s", strerror(errno));
		return -1;
	}

	return fd;
}

int spiConfig(int fileDescriptor, int mode, int speed, int bitsPerWord, int lsbFirst) {

	printf("MODE: %i\n", mode);
	if(ioctl(fileDescriptor, SPI_IOC_WR_MODE, &mode) < 0) {
		errorMessage("cannot set spi mode: %s", strerror(errno));
		return -1 ;
	}

	if(ioctl(fileDescriptor, SPI_IOC_RD_MODE, &mode) < 0) {
		errorMessage("cannot get spi mode: %s", strerror(errno));
		return -1 ;
	}

	printf("BPW: %i\n", bitsPerWord);
	if(ioctl(fileDescriptor, SPI_IOC_WR_BITS_PER_WORD, &bitsPerWord) < 0) {
		errorMessage("cannot set spi bits per word: %s", strerror(errno));
		return -1;
	}

	if(ioctl(fileDescriptor, SPI_IOC_RD_BITS_PER_WORD, &bitsPerWord) < 0) {
		errorMessage("cannot get spi bits per word: %s", strerror(errno));
		return -1;
	}

	if(ioctl(fileDescriptor, SPI_IOC_WR_MAX_SPEED_HZ, &speed) < 0) {
		errorMessage("cannot set spi speed: %s", strerror(errno));
		return -1;
	}

	if(ioctl(fileDescriptor, SPI_IOC_RD_MAX_SPEED_HZ, &speed) < 0) {
		errorMessage("cannot get spi speed: %s", strerror(errno));
		return -1;
	}

	int lsb = lsbFirst > 0 ? SPI_LSB_FIRST : 0;

	if(ioctl(fileDescriptor, SPI_IOC_WR_LSB_FIRST, &lsb) < 0) {
		errorMessage("cannot set lsb first: %s", strerror(errno));
		return -1;
	}

	if(ioctl(fileDescriptor, SPI_IOC_RD_LSB_FIRST, &lsb) < 0) {
		errorMessage("cannot get lsb first: %s", strerror(errno));
		return -1;
	}


	return 0;
}

int spiTransfer(int fileDescriptor, unsigned int* txBuffer, unsigned int* rxBuffer, int transferLength, int delay, int speed, int bitsPerWord) {
	struct spi_ioc_transfer tr = {
		 tr.tx_buf = (unsigned long)txBuffer,
		 tr.rx_buf = (unsigned long)rxBuffer,
		 tr.len = (uint32_t)transferLength,
		 tr.delay_usecs = (uint16_t)delay,
		 tr.speed_hz = (uint32_t)speed,
		 tr.bits_per_word = (uint8_t)bitsPerWord,
	 };

	 int ret = ioctl(fileDescriptor, SPI_IOC_MESSAGE(1), &tr);
	 if(ret < 0) {
		 errorMessage("spi message transfer failed: %s", strerror(errno));
	 }

	 return ret;
}

int spiClose(int fileDescriptor) {
	return close(fileDescriptor);
}
