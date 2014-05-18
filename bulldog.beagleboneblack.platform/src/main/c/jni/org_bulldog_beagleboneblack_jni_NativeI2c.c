#include <jni.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include "../i2c/i2c.h"
#include "org_bulldog_beagleboneblack_jni_NativeI2c.h"
 
/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cRead
 * Signature: (I)B
 */
JNIEXPORT jbyte JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cRead
  (JNIEnv * env, jclass clazz, jint fd) {
	return i2cRead(fd);
}


/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cWrite
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cWrite
  (JNIEnv * env, jclass clazz, jint fd, jbyte data) {
	return i2cWrite(fd, data);
}


/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cSelectSlave
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cSelectSlave
  (JNIEnv * env, jclass clazz, jint fd, jint addr) {
	return i2cSelectSlave(fd, addr);
}

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cOpen
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cOpen
  (JNIEnv * env, jclass clazz, jstring path) {
	char fileName[256];
	int len = (*env)->GetStringLength(env, path);
	(*env)->GetStringUTFRegion(env, path, 0, len, fileName);

 	return i2cOpen(fileName);
}

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cClose
 * Signature: (I)[Ljava/io/FileDescriptor
 */
JNIEXPORT jobject JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cGetJavaDescriptor
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
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cClose
  (JNIEnv * env, jclass clazz, jint fd) {
	return i2cClose(fd);
}

