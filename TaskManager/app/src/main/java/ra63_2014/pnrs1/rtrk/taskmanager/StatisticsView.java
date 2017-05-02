package ra63_2014.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;

/**
 * Created by godra on 2.5.17..
 */

public class StatisticsView extends View {

    private Paint paint;

    /*
        Will set those percentages later from
        database or whatever

     */
    private int redCirclePercentage = 88;
    private int greenCirclePercentage = 93;
    private int yellowCirclePercentage = 81;

    /*
        Percentage of circle already drawn.
        Default values are 0. (I don't want them to be drawn straight away)
     */
    private int redDrawnPercentage = 0;
    private int greenDrawnPercentage = 0;
    private int yellowDrawnPercentage = 0;

    private RectF redCircle = new RectF();
    private RectF yellowCircle = new RectF();
    private RectF greenCircle = new RectF();

    private Animation animation = new Animation();

    private String redPercentage;
    private String yellowPercentage;
    private String greenPercentage;

    private final String hpTask = "Zadaci visokog prioriteta";
    private final String mpTask = "Zadaci srednjeg prioriteta";
    private final String lpTask = "Zadaci niskog prioriteta";

    public StatisticsView(Context context) {
        super(context);
        paint = new Paint();
        animation.execute();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
            Rectangles on which circles will be placed
         */
        redCircle.set(canvas.getWidth() * 4 / 12,
                canvas.getHeight() / 10,
                canvas.getWidth() * 8 / 12,
                canvas.getHeight() * 3 / 10);
        yellowCircle.set(canvas.getWidth() / 12,
                canvas.getHeight() * 5 / 10,
                canvas.getWidth() * 5 / 12,
                canvas.getHeight() * 7 / 10);
        greenCircle.set(canvas.getWidth() * 7 / 12,
                canvas.getHeight() * 5 / 10,
                canvas.getWidth() * 11 / 12,
                canvas.getHeight() * 7 / 10);


        /*
            Draw red circle animation
         */
        paint.setColor(Color.BLUE);
        canvas.drawOval(redCircle, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(redCircle, -90, (float)(redDrawnPercentage * 3.6), true, paint);

        /*
            Draw yellow circle animation
         */
        paint.setColor(Color.BLUE);
        canvas.drawOval(yellowCircle, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawArc(yellowCircle, -90, (float)(yellowDrawnPercentage * 3.6), true, paint);

        /*
            Draw green circle animation
         */
        paint.setColor(Color.BLUE);
        canvas.drawOval(greenCircle, paint);
        paint.setColor(Color.GREEN);
        canvas.drawArc(greenCircle, -90, (float)(greenDrawnPercentage * 3.6), true, paint);

        /*
            Draw text
         */
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);

        /*
            Text under circles
         */
        canvas.drawText(hpTask, 0, hpTask.length(), redCircle.centerX() - 220, redCircle.centerY() + 200, paint);
        canvas.drawText(mpTask, 0, mpTask.length(), yellowCircle.centerX() - 240, yellowCircle.centerY() + 200, paint);
        canvas.drawText(lpTask, 0, lpTask.length(), greenCircle.centerX() - 220, greenCircle.centerY() + 200, paint);

        paint.setTextSize(80);

        /*
            Percentage string
         */
        canvas.drawText(redPercentage, 0, redPercentage.length(), redCircle.centerX() - 70, redCircle.centerY() + 30, paint);
        canvas.drawText(yellowPercentage, 0, yellowPercentage.length(), yellowCircle.centerX() - 70, yellowCircle.centerY() + 30, paint);
        canvas.drawText(greenPercentage, 0, greenPercentage.length(), greenCircle.centerX() - 70, greenCircle.centerY() + 30, paint);

    }

    private class Animation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while(redDrawnPercentage < redCirclePercentage ||
                    yellowDrawnPercentage < yellowCirclePercentage ||
                    greenDrawnPercentage < greenCirclePercentage) {

                if(redDrawnPercentage < redCirclePercentage)
                    redDrawnPercentage++;

                if(yellowDrawnPercentage < yellowCirclePercentage)
                    yellowDrawnPercentage++;

                if(greenDrawnPercentage < greenCirclePercentage)
                    greenDrawnPercentage++;

                redPercentage = Integer.toString(redDrawnPercentage) + "%";
                yellowPercentage = Integer.toString(yellowDrawnPercentage) + "%";
                greenPercentage = Integer.toString(greenDrawnPercentage) + "%";

                postInvalidate();
                SystemClock.sleep(25);

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO : Probably will load data from database here
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
