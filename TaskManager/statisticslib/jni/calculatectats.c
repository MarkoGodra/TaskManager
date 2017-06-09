#include "calculatectats.h"

#include <jni.h>
#include <math.h>

JNIEXPORT jint JNICALL Java_ra63_12014_pnrs1_rtrk_taskmanager_CalculateStatsNative_calculatePercentage
  (JNIEnv *env, jobject obj, jint total, jint completed)
{
    if(total == 0)
    {
        return 0;
    }
    return (jint) floor(((double)completed/(double)total)*100);
}
