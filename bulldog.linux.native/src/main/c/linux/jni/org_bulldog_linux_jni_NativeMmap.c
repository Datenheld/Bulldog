#include <jni.h>
#include <stdint.h>
#include <sys/mman.h>

/*
 * Class:     org_bulldog_linux_jni_NativeMmap
 * Method:    createMap
 * Signature: (JJIIJ)J
 */
JNIEXPORT jlong JNICALL Java_org_bulldog_linux_jni_NativeMmap_createMap
  (JNIEnv * env, jobject clazz, jlong address, jlong length, jint prot, jint flags, jint fileDescriptor, jlong offset) {
	return (jlong)(intptr_t)mmap((int*)address, length, prot, flags, fileDescriptor, offset);
}

/*
 * Class:     org_bulldog_linux_jni_NativeMmap
 * Method:    deleteMap
 * Signature: (J)V
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeMmap_deleteMap
  (JNIEnv * env, jobject clazz, jlong address, jlong length) {
	return (jint)(intptr_t)munmap(address, length);
}

/*
 * Class:     org_bulldog_linux_jni_NativeMmap
 * Method:    setValueAt
 * Signature: (LI)V
 */
JNIEXPORT void JNICALL Java_org_bulldog_linux_jni_NativeMmap_setValueAt
  (JNIEnv * env, jobject clazz, jlong address, jint value) {
	int * ptr = (int *)address;
	*ptr = value;
}

/*
 * Class:     org_bulldog_linux_jni_NativeMmap
 * Method:    getValueAt
 * Signature: (L)I
 */
JNIEXPORT jint JNICALL Java_org_bulldog_linux_jni_NativeMmap_getValueAt
  (JNIEnv * env, jobject clazz, jlong address) {
	int * ptr = (int *)address;
	return (jint)(*ptr);
}
