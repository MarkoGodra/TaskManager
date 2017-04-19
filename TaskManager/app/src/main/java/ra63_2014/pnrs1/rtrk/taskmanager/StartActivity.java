package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class StartActivity extends AppCompatActivity {

    private Button btnNoviZad;
    private Button btnStat;
    private ListView listView;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
                Task task = (Task) data.getSerializableExtra(getResources().getString(R.string.result));
                adapter.addTask(task);
            }
        }
    }

}
