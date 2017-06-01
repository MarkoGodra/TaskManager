package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class TimerService extends Service {

    private ArrayList<Task> taskList = new ArrayList<>();
    private IBinder binder = new ReminderBinder();
    private DatabaseHelper db;

    private static int REMINDER_ID = 5;

    public TimerService() {
        db = new DatabaseHelper(this);
        final Handler handler = new Handler();
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                taskList.clear();

                //Log.d("dbg ", "\n######## NOVA ITERACIJA ######\n");

                Task[] tasks = db.readTasks();
                for(int i = 0; i < tasks.length; i++){
//                    Log.d("dbg ", tasks[i].getIme().toString() + " vreme isteka : "
//                            + tasks[i].getCalendar().get(Calendar.HOUR_OF_DAY )+ "" + "/"
//                            + tasks[i].getCalendar().get(Calendar.MINUTE) + "" + "\n" + " Reminder "
//                            + tasks[i].isReminder() + "" + " zavrsen : "
//                            + tasks[i].isZavrsen() + "");
                    taskList.add(tasks[i]);
                }

                for (Task task : taskList) {

                    Calendar cal = task.getCalendar();
                    Calendar cal_now = Calendar.getInstance();

                    if(cal.getTimeInMillis() - cal_now.getTimeInMillis() <= 0){
                        task.setZavrsen(true);
                        task.setReminder(false);
                        db.update(task, task.getIme().toString(),
                                task.getOpis().toString(),
                                task.getCalendar(),
                                task.getPrioritet(),
                                task.isZavrsen(),
                                task.isReminder());
                    }

                    if (task.isReminder() && !task.isZavrsen()) {

                        if (cal.getTimeInMillis() - cal_now.getTimeInMillis() < 900000) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(TimerService.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Task Manager")
                                    .setContentText(task.getIme() + " - 15 minuta do isteka!");

                            StartActivity.notificationManager.notify(REMINDER_ID, builder.build());
                            task.setReminder(false);
                            db.update(task, task.getIme().toString(),
                                    task.getOpis().toString(),
                                    task.getCalendar(),
                                    task.getPrioritet(),
                                    task.isZavrsen(),
                                    task.isReminder());
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


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        Bundle bundle = intent.getExtras();
        taskList = ((ArrayList<Task>) bundle.get("Task"));

        if (!taskList.isEmpty()) {
            Log.d("DEBUG", "List not empty");
        }

        return binder;
    }

    public class ReminderBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    public void update(Task[] tasks) {
        taskList.clear();

//        if(taskList.isEmpty()) {
//            Log.d("timer_service", "local task list cleared");
//        }

        for(int i = 0; i < tasks.length; i++) {
            //Log.d("timer_service ", tasks[i].getIme().toString() + " Added to the local list");
            taskList.add(tasks[i]);
        }

        //Log.d("\ntimer_service", "Tasks in local list");
//        for(Task task : taskList){
//            Log.d("timer_service ", task.getIme().toString() + " vreme isteka : "
//                    + task.getCalendar().get(Calendar.HOUR_OF_DAY )+ "" + "/"
//                    + task.getCalendar().get(Calendar.MINUTE) + "" + "\n" + " Reminder "
//                    + task.isReminder() + "");
//        }
    }
}
