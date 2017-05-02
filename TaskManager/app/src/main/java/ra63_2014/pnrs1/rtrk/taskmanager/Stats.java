package ra63_2014.pnrs1.rtrk.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        StatisticsView statisticsView = new StatisticsView(getBaseContext());
        setContentView(statisticsView);
    }
}
