#include <jni.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <stdlib.h>
#include "org_bulldog_linux_jni_NativeTools.h"


/*
 * Class:     org_bulldog_linux_jni_NativeTools
 * Method:    getJavaDescriptor
 * Signature: (I)[Ljava/io/FileDescriptor
 */
JNIEXPORT jobject JNICALL Java_org_bulldog_linux_jni_NativeTools_getJavaDescriptor
(JNIEnv * env, jclass clazz, int descriptor) {
	  jfieldID field_fd;
	  jmethodID const_fdesc;
	  jclass class_fdesc, class_ioex;
	  jobject ret;
	  int fd;
	  char *fname;

	  class_ioex = (*env)->FindClass(env, "java/io/IOException");
	  if (class_ioex == NULL) return NULL;
	  class_fdesc = (*env)->FindClass(env, "java/io/FileDescriptor");
	  if (class_fdesc == NULL) return NULL;

	  // construct a new FileDescriptor
	  const_fdesc = (*env)->GetMethodID(env, class_fdesc, "<init>", "()V");
	  if (const_fdesc == NULL) return NULL;
	  ret = (*env)->NewObject(env, class_fdesc, const_fdesc);

	  // poke the "fd" field with the file descriptor
	  field_fd = (*env)->GetFieldID(env, class_fdesc, "fd", "I");
	  if (field_fd == NULL) return NULL;
	  (*env)->SetIntField(env, ret, field_fd, descriptor);

	  // and return it
	  return ret;

}

/*
 * Class:     org_bulldog_linux_jni_NativeTools
 * Method:    open
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeTools_open
(JNIEnv * env, jclass clazz, jstring path, jint flags) {
	char fileName[256];
	int len = (*env)->GetStringLength(env, path);
	(*env)->GetStringUTFRegion(env, path, 0, len, fileName);
	return open(fileName, flags);

}

/*
 * Class:     org_bulldog_linux_jni_NativeTools
 * Method:    close
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeTools_close
(JNIEnv * env, jclass clazz, jint fd) {
	return close(fd);
}


/*
 * Class:     org_bulldog_linux_jni_NativeTools
 * Method:    sleepMicros
 * Signature: (I)I
 */
JNIEXPORT void JNICALL Java_org_bulldog_linux_jni_NativeTools_sleepMicros
  (JNIEnv * env, jclass clazz, jint microSeconds) {
	if(usleep(microSeconds) < 0) {
		if(errno == EINTR) {
			jclass interruptedExceptionType = (*env)->FindClass(env, "java/lang/InterruptedException");
			(*env)->ThrowNew(env, interruptedExceptionType, "Signal catched during sleep!");
		} else if(errno == EINVAL) {
			jclass illegalArgumentExceptionType = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
			(*env)->ThrowNew(env, illegalArgumentExceptionType, "Invalid value for sleepMicros. Value needs to be between 0 and 1 000 000!");
		}
	}
}

/*
 * Class:     org_bulldog_linux_jni_NativeTools
 * Method:    sleepNanos
 * Signature: (I)I
 */
JNIEXPORT void JNICALL Java_org_bulldog_linux_jni_NativeTools_sleepNanos
  (JNIEnv * env, jclass clazz, jint nanoseconds) {
	 struct timespec *req, *rem;
	 int returnValue = 0;

	 req = malloc(sizeof(struct timespec));
	 rem = malloc(sizeof(struct timespec));

	 req->tv_sec = nanoseconds / (1000 * 1000 * 1000);
	 req->tv_nsec = nanoseconds % (1000 * 1000 * 1000);
	 while((returnValue = nanosleep(req, rem)) && errno==EINTR){
	     struct timespec *tmp = req;
	     req = rem;
	     rem = tmp;
	 }

	 if (returnValue) {
		if(errno == EINTR) {
			 jclass interruptedExceptionType = (*env)->FindClass(env, "java/lang/InterruptedException");
			 (*env)->ThrowNew(env, interruptedExceptionType, "Signal catched during sleep!");
		} else if(errno == EINVAL) {
			 jclass illegalArgumentExceptionType = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
			 (*env)->ThrowNew(env, illegalArgumentExceptionType, "Invalid value for sleepNanos! Value needs to be between 0 and 999 999 999!");
		} else if(errno == EFAULT) {
			 jclass runtimeExceptionType = (*env)->FindClass(env, "java/lang/RuntimeException");
			 (*env)->ThrowNew(env, runtimeExceptionType, "Error while copying information from user space during sleep");
		}
	 }
}
