#include <jni.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
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
