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


JNIEXPORT void JNICALL Java_org_bulldog_linux_jni_NativeMmap_setValueAt
  (JNIEnv * env, jobject clazz, jlong address, jbyte value) {
	*address = value;
}
