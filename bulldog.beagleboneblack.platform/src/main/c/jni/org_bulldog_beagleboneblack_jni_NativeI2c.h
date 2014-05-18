/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_bulldog_beagleboneblack_jni_I2CNative */

#ifndef _Included_org_bulldog_beagleboneblack_jni_NativeI2c
#define _Included_org_bulldog_beagleboneblack_jni_NativeI2c
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cRead
 * Signature: (I)B
 */
JNIEXPORT jbyte JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cRead
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cWrite
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cWrite
  (JNIEnv *, jclass, jint, jbyte);

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cSelectSlave
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cSelectSlave
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cOpen
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cOpen
  (JNIEnv *, jclass, jstring);

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cOpenDescriptor
 * Signature: (I)(I)[Ljava/io/FileDescriptor
 */
JNIEXPORT jobject JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cGetJavaDescriptor
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_bulldog_beagleboneblack_jni_I2CNative
 * Method:    i2cClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_beagleboneblack_jni_NativeI2c_i2cClose
  (JNIEnv *, jclass, jint);

#ifdef __cplusplus
}
#endif
#endif
