#include <jni.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include "../bulldog/bulldogI2c.h"
#include "org_bulldog_linux_jni_NativeI2c.h"
 
/*
 * Class:     org_bulldog_linux_jni_I2CNative
 * Method:    i2cRead
 * Signature: (I)B
 */
JNIEXPORT jbyte JNICALL Java_org_bulldog_linux_jni_NativeI2c_i2cRead
  (JNIEnv * env, jclass clazz, jint fd) {
	return i2cRead(fd);
}


/*
 * Class:     org_bulldog_linux_jni_I2CNative
 * Method:    i2cWrite
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeI2c_i2cWrite
  (JNIEnv * env, jclass clazz, jint fd, jbyte data) {
	return i2cWrite(fd, data);
}


/*
 * Class:     org_bulldog_linux_jni_I2CNative
 * Method:    i2cSelectSlave
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeI2c_i2cSelectSlave
  (JNIEnv * env, jclass clazz, jint fd, jint addr) {
	return i2cSelectSlave(fd, addr);
}

/*
 * Class:     org_bulldog_linux_jni_I2CNative
 * Method:    i2cOpen
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeI2c_i2cOpen
  (JNIEnv * env, jclass clazz, jstring path) {
	char fileName[256];
	int len = (*env)->GetStringLength(env, path);
	(*env)->GetStringUTFRegion(env, path, 0, len, fileName);
 	return i2cOpen(fileName);
}


/*
 * Class:     org_bulldog_linux_jni_I2CNative
 * Method:    i2cClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeI2c_i2cClose
  (JNIEnv * env, jclass clazz, jint fd) {
	return i2cClose(fd);
}

