package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Stats extends AppCompatActivity {

    private RelativeLayout layout;
    private Button btnBack;
    StatisticsView statisticsView;
    private DatabaseHelper db;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        statisticsView.animation.cancel(true);
        Intent intent = new Intent(getBaseContext(), StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        int redSum = 0;
        int redFinishedSum = 0;
        int yellowSum = 0;
        int yellowFinishedSum = 0;
        int greenSum = 0;
        int greenFinishedSum = 0;

        db = new DatabaseHelper(this);
        Task[] tasks = db.readTasks();
        if(tasks != null) {
            for (int i = 0; i < tasks.length; i++){
                switch(tasks[i].getPrioritet()) {
                    case 3 :
                        redSum++;
                        if(tasks[i].isZavrsen()){
                            redFinishedSum++;
                        }
                        break;
                    case 2 :
                        yellowSum++;
                        if(tasks[i].isZavrsen()){
                            yellowFinishedSum++;
                        }
                        break;
                    case 1 :
                        greenSum++;
                        if(tasks[i].isZavrsen()){
                            greenFinishedSum++;
                        }
                        break;
                }
            }
        }

        Log.d("stats", "red sum " + redSum + "");
        Log.d("stats", "green sum"  + greenSum + "");
        Log.d("stats", "yellow_sum " + yellowSum + "");

        Log.d("stats", "red f " + redFinishedSum + "");
        Log.d("stats", "green f "  + greenFinishedSum + "");
        Log.d("stats", "yellow f " + yellowFinishedSum + "");

        double redPercentage;
        double greenPercentage;
        double yellowPercentage;

        if(redSum > 0)
            redPercentage = (double)redFinishedSum / redSum * 100;
        else
            redPercentage = 0.01;

        if(greenSum > 0)
            greenPercentage = (double)greenFinishedSum / greenSum * 100;
        else
            greenPercentage = 0.01;

        if(yellowSum > 0)
            yellowPercentage = (double)yellowFinishedSum / yellowSum * 100;
        else
            yellowPercentage = 0.01;


        Log.d("stats", "red % " + redPercentage + "");
        Log.d("stats", "grn % " + greenPercentage + "");
        Log.d("stats", "ylw % " + yellowPercentage + "");

        statisticsView = new StatisticsView(getBaseContext(), (int)redPercentage, (int)yellowPercentage, (int)greenPercentage);

        layout = (RelativeLayout)findViewById(R.id.layout);
        layout.addView(statisticsView);

        btnBack = (Button)findViewById(R.id.btnNazad);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsView.animation.cancel(true);
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


    }
}
