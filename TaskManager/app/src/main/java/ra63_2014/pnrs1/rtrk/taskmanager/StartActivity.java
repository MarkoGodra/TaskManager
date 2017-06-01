package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity{

    private Button btnNoviZad;
    private Button btnStat;
    private ListView listView;
    private TaskAdapter adapter;
    private TimerService timerService;
    private ArrayList<Task> tasks = new ArrayList<>();
    private DatabaseHelper db;

    public static NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        db = new DatabaseHelper(this);

        tasks = TaskAdapter.getList();

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(getBaseContext(), TimerService.class);
        i.putExtra("Task", tasks);



        ServiceConnection myServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                timerService = ((TimerService.ReminderBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        bindService(i, myServiceConnection, BIND_AUTO_CREATE);

        adapter = new TaskAdapter(getBaseContext());



        listView = (ListView) findViewById(R.id.listView);
        btnNoviZad = (Button) findViewById(R.id.button1);
        btnStat = (Button) findViewById(R.id.button2);

        btnNoviZad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedOnAddActivity();
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedOnStatsActivity();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), NewTask.class);
                Task task = (Task) listView.getItemAtPosition(position);
                i.putExtra("zadatak_edit", task);
                startActivity(i);
                return true;
            }
        });

        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Log.d("DEBUG", "usao u on resume");

        Task[] tasks = db.readTasks();
//        if(tasks != null) {
//            for(int ii = 0; ii < tasks.length; ii++) {
//                Log.d("Database items", tasks[ii].getIme().toString() + " Finished" + tasks[ii].isZavrsen() + "");
//            }
//        } else {
//            Log.d("Database items", "Databse is empty");
//        }


        adapter.update(tasks);
    }

    public void proceedOnAddActivity() {
        Intent i = new Intent(getBaseContext(), NewTask.class);
        startActivityForResult(i, 1);

    }

    public void proceedOnStatsActivity() {
        Intent intent = new Intent(StartActivity.this, Stats.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                if (data.hasExtra("result")) {

                    Task task = (Task) data.getSerializableExtra(getResources().getString(R.string.result));
                    //adapter.addTask(task);
                    db.insert(task);
                    Task[] tasks = db.readTasks();
                    adapter.update(tasks);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(StartActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Task Manager")
                            .setContentText(task.getIme() + " - dodat");

                    notificationManager.notify(5, builder.build());

                    timerService.update(tasks);
                }
                Task[] tasks = db.readTasks();
                adapter.update(tasks);
                timerService.update(tasks);
            }
        }
    }


}
