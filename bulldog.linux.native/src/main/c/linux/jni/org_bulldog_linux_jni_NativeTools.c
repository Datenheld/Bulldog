#include <jni.h>
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
