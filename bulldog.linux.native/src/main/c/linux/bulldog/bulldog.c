#include <stdarg.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include "bulldog.h"


char* intToStr(int value, char* result, int base) {
	// check that the base if valid
	if (base < 2 || base > 36) {
		*result = '\0';
		return result;
	}

	char* ptr = result, *ptr1 = result, tmp_char;
	int tmp_value;

	do {
		tmp_value = value;
		value /= base;
		*ptr++ =
				"zyxwvutsrqponmlkjihgfedcba9876543210123456789abcdefghijklmnopqrstuvwxyz"[35
						+ (tmp_value - value * base)];
	} while (value);

	// Apply negative sign
	if (tmp_value < 0) {
		*ptr++ = '-';
	}

	*ptr-- = '\0';

	while (ptr1 < ptr) {
		tmp_char = *ptr;
		*ptr-- = *ptr1;
		*ptr1++ = tmp_char;
	}

	return result;
}

void errorMessage(char* messageFormat, ...) {
	const char *p;
	va_list args;
	int i;
	char* s;
	char* fmtbuf;

	fmtbuf = malloc(256);

	va_start(args, messageFormat);
	for (p = messageFormat; *p != '\0'; p++) {
		if (*p != '%') {
			putc(*p, stderr);
			continue;
		}

		switch (*++p) {
		case 'c':
			i = va_arg(args, int);
			putc((char )i, stderr);
			break;

		case 'd':
			i = va_arg(args, int);
			s = intToStr(i, fmtbuf, 10);
			fputs(s, stderr);
			break;

		case 'i':
			i = va_arg(args, int);
			s = intToStr(i, fmtbuf, 10);
			fputs(s, stderr);
			break;

		case 's':
			s = va_arg(args, char *);
			fputs(s, stderr);
			break;

		case 'x':
			i = va_arg(args, int);
			s = intToStr(i, fmtbuf, 16);
			fputs(s, stderr);
			break;

		case '%':
			putc('%', stderr);
			break;
		}
	}
	va_end(args);
}

char* descriptorPath(int fd) {
	struct stat sb;
	char* buffer;
	char* linkname;
	char* filename;

	buffer = malloc(10);

	sprintf(buffer, "%i", fd);
	filename = concat("/proc/self/fd/", buffer);

	if (lstat(filename, &sb) == -1) {
		return NULL;
	}

	linkname = malloc(sb.st_size + 1);

	readlink(filename, linkname, sb.st_size + 1);
	free(buffer);
	free(filename);
	return linkname;
}

char* concat(char *s1, char *s2) {
	char *result = malloc(strlen(s1) + strlen(s2) + 1); //+1 for the zero-terminator
	strcpy(result, s1);
	strcat(result, s2);
	return result;
}

