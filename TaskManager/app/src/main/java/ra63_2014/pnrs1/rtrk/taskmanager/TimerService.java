package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class TimerService extends Service {

    private ArrayList<Task> taskList;
    private TimerClass timerObject;

    private static int REMINDER_ID = 5;

    public TimerService() {
        final Handler handler = new Handler();
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                taskList = TaskAdapter.getList();
                for(Task task : taskList) {
                    if(task.isReminder()) {
                        Calendar cal = task.getCalendar();
                        Calendar cal_now = Calendar.getInstance();

                        if(cal.getTimeInMillis() - cal_now.getTimeInMillis() < 900000){
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(TimerService.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Task Manager")
                                    .setContentText(task.getIme() + " - 15 minuta do isteka!");

                            StartActivity.notificationManager.notify(REMINDER_ID, builder.build());
                            task.setReminder(false);
                        }
                    }
                }
                handler.postDelayed(this, 60000);
            }
        };

        handler.postDelayed(thread, 1000);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Task Manager");

        timerObject = new TimerClass(builder);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return timerObject;
    }
}
