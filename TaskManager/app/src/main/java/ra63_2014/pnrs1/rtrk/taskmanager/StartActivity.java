package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class StartActivity extends AppCompatActivity {

    private Button btnNoviZad;
    private Button btnStat;
    private ListView listView;
    private TaskAdapter adapter;
    private String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        adapter = new TaskAdapter(getBaseContext());

        listView = (ListView)findViewById(R.id.listView);
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

        listView.setAdapter(adapter);

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
        Log.d(TAG, "onActivityResult: USAO OVDE");
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Task task = (Task)data.getSerializableExtra(getResources().getString(R.string.result));
                adapter.addTask(task);
            }
        }
    }

}
