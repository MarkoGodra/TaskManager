package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Notification;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

/**
 * Created by godra on 16.5.17..
 */

public class TimerClass extends ITimerAidlInterface.Stub {

    private static int ADD_ID = 1;
    private NotificationCompat.Builder addNotification;

    public TimerClass(NotificationCompat.Builder addBuilder){
        this.addNotification = addBuilder;
    };

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void notifyTaskAdded(String taskName) throws RemoteException {
        addNotification.setContentText(taskName + " dodat");
        StartActivity.notificationManager.notify(ADD_ID, addNotification.build());

    }

    @Override
    public void notifyTaskRemoved(String taskName) throws RemoteException {

    }

    @Override
    public void notifyTaskUpdated(String taskName) throws RemoteException {

    }
}
