package ra63_2014.pnrs1.rtrk.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by godra on 1.6.17..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "tasks.db";
    public static final String TABLE_NAME = "Tasks";
    public static final String COLUMN_NAME = "TaskName";
    public static final String COLUMN_TIME = "TaskTime";
    public static final String COLUMN_DESCRIPTION = "TaskDescription";
    public static final String COLUMN_REMINDER = "TaskReminder";
    public static final String COLUMN_PRIORITY = "TaskPriority";
    public static final String COLUMN_DONE = "TaskDone";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("qwerty", "database_created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database", "Creating");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+COLUMN_NAME+" TEXT, "
                +COLUMN_TIME+" INT, "
                +COLUMN_DESCRIPTION+" TEXT, "
                +COLUMN_REMINDER+" INT, "
                +COLUMN_PRIORITY+" INT, "
                +COLUMN_DONE+" INT);");
        Log.d("Database", "created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Task mTask)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("prioitet", Integer.toString(mTask.getPrioritet()));
        values.put(COLUMN_NAME, mTask.getIme());
        values.put(COLUMN_PRIORITY, mTask.getPrioritet());
        values.put(COLUMN_TIME, mTask.getCalendar().getTimeInMillis());
        values.put(COLUMN_REMINDER, mTask.isReminder() ? 1 : 0);
        values.put(COLUMN_DESCRIPTION, mTask.getOpis());
        values.put(COLUMN_DONE, mTask.isZavrsen() ? 1 : 0);

        db.insert(TABLE_NAME, null, values);
        Log.d("qwerty", "task inserted");
        close();
    }

    public Task[] readTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0){
            return null;
        }

        Task[] tasks = new Task[cursor.getCount()];
        int i = 0;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            tasks[i++] = createTask(cursor);
        }

        close();
        return tasks;
    }

    public void update(Task task, String ime, String opis, Calendar cal, int prioritet, boolean zavrsen, boolean reminder) {
        SQLiteDatabase db = getReadableDatabase();
        deleteTask(task.getIme());

        Task mTask = new Task(ime, opis, prioritet, cal, reminder, zavrsen);
        insert(mTask);
    }

    public Task readTask(String ime) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_NAME + "=?",
                new String[] {ime}, null, null, null);

        cursor.moveToFirst();
        Task task = createTask(cursor);
        close();
        return task;
    }

    public void deleteTask(String ime) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[] {ime});
        close();
    }

    public Task createTask(Cursor cursor){
        String ime = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String opis = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
        int prioritet = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
        int zavrsen = cursor.getInt(cursor.getColumnIndex(COLUMN_DONE));
        int vreme = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME));
        int reminder = cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER));

//        Log.d("Zadatak - ime :", ime);
//        Log.d("Zadatak - prioritet :", Integer.toString(prioritet) + "\n");

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(vreme);

        Task task = new Task(ime, opis, prioritet, cal,
                reminder == 1 ? true : false,
                zavrsen == 1 ? true : false);

        return task;

    }
}
