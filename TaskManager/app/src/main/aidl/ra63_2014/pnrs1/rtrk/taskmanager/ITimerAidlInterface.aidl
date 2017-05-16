// ITimerAidlInterface.aidl
package ra63_2014.pnrs1.rtrk.taskmanager;

// Declare any non-default types here with import statements

interface ITimerAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void notifyTaskAdded(String taskName);
    void notifyTaskRemoved(String taskName);
    void notifyTaskUpdated(String taskName);
}
