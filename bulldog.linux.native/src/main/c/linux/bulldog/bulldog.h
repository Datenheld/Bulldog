#ifndef BULLDOG_H
#define BULLDOG_H


#ifdef __cplusplus
extern "C" {
#endif

extern void errorMessage(char* messageFormat, ...);
extern char* descriptorPath(int fd);
extern char* concat(char *s1, char *s2);
extern char* intToStr(int value, char* result, int base);

#ifdef __cplusplus
}
#endif

#endif
