package ra63_2014.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Stats extends AppCompatActivity {

    private RelativeLayout layout;
    private Button btnBack;
    StatisticsView statisticsView;

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

        statisticsView = new StatisticsView(getBaseContext());

        layout = (RelativeLayout)findViewById(R.id.layout);
        layout.addView(statisticsView);

        btnBack = (Button)findViewById(R.id.btnNazad);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsView.animation.cancel(true);
                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
