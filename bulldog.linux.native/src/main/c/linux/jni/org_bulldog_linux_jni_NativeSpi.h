/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_bulldog_linux_jni_NativeSpi */

#ifndef _Included_org_bulldog_linux_jni_NativeSpi
#define _Included_org_bulldog_linux_jni_NativeSpi
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_bulldog_linux_jni_NativeSpi
 * Method:    spiOpen
 * Signature: (Ljava/lang/String;III)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeSpi_spiOpen
  (JNIEnv *, jclass, jstring, jint, jint, jint, jboolean);


/*
 * Class:     org_bulldog_linux_jni_NativeSpi
 * Method:    spiOpen
 * Signature: (Ljava/lang/String;III)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeSpi_spiConfig
  (JNIEnv *, jclass, jint, jint, jint, jint, jboolean);

/*
 * Class:     org_bulldog_linux_jni_NativeSpi
 * Method:    spiClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeSpi_spiClose
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_bulldog_linux_jni_NativeSpi
 * Method:    spiTransfer
 * Signature: (IJJIIII)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeSpi_spiTransfer
  (JNIEnv *, jclass, jint, jobject, jobject, jint, jint, jint, jint);

#ifdef __cplusplus
}
#endif
#endif
