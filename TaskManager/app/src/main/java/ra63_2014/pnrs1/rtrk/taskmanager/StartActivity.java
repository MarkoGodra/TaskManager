package ra63_2014.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class StartActivity extends AppCompatActivity {

    private Button btnNoviZad;
    private Button btnStat;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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

    }

    public void proceedOnAddActivity() {

        Intent intent = new Intent(StartActivity.this, NewTask.class);
        startActivity(intent);
        //finish();
    }

    public void proceedOnStatsActivity() {

        Intent intent = new Intent(StartActivity.this, Stats.class);
        startActivity(intent);
        //finish();

    }
}
