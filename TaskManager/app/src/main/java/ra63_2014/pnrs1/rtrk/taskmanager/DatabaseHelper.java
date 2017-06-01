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
    public static final String COLUMN_DESCRIPTION = "TaskDescription";
    public static final String COLUMN_REMINDER = "TaskReminder";
    public static final String COLUMN_PRIORITY = "TaskPriority";
    public static final String COLUMN_DONE = "TaskDone";
    public static final String COLUMN_DAY = "TaskDay";
    public static final String COLUMN_MONTH = "TaskMonth";
    public static final String COLUMN_YEAR = "TaskYear";
    public static final String COLUMN_HOUR = "TaskHour";
    public static final String COLUMN_MINUTE = "TaskMinute";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("qwerty", "database_created");
        //onCreate(getReadableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DEBUG", "creating DB");
        Log.d("database", "Creating");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+COLUMN_NAME+" TEXT, "
                +COLUMN_DESCRIPTION+" TEXT, "
                +COLUMN_REMINDER+" INT, "
                +COLUMN_DAY+" INT, "
                +COLUMN_MONTH+" INT, "
                +COLUMN_YEAR+" INT, "
                +COLUMN_HOUR+" INT, "
                +COLUMN_MINUTE+" INT, "
                +COLUMN_PRIORITY+" INT, "
                +COLUMN_DONE+" INT);");
        Log.d("Database", "created");
        Log.d("DEBUG", "DB created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Task mTask) {
        /*

            storageCalendar.set(Calendar.MINUTE, minute);
                storageCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                 storageCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                storageCalendar.set(Calendar.MONTH, month);
                storageCalendar.set(Calendar.YEAR, year);


         */

        Calendar storageCalendar = mTask.getCalendar();
        int minutes = storageCalendar.get(Calendar.MINUTE);
        int hours = storageCalendar.get(Calendar.HOUR_OF_DAY);
        int day = storageCalendar.get(Calendar.DAY_OF_MONTH);
        int month = storageCalendar.get(Calendar.MONTH);
        int year = storageCalendar.get(Calendar.YEAR);


        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("prioitet", Integer.toString(mTask.getPrioritet()));
        values.put(COLUMN_NAME, mTask.getIme());
        values.put(COLUMN_PRIORITY, mTask.getPrioritet());
        values.put(COLUMN_DAY, day);
        values.put(COLUMN_MONTH, month);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_HOUR, hours);
        values.put(COLUMN_MINUTE, minutes);
        values.put(COLUMN_REMINDER, mTask.isReminder() ? 1 : 0);
        values.put(COLUMN_DESCRIPTION, mTask.getOpis());
        values.put(COLUMN_DONE, mTask.isZavrsen() ? 1 : 0);

        db.insert(TABLE_NAME, null, values);
        Log.d("qwerty", "task inserted");
        close();
    }

    public Task[] readTasks() {
        Log.d("DEBUG", "usao u read tasks");

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
        int reminder = cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER));
        int minute = cursor.getInt(cursor.getColumnIndex(COLUMN_MINUTE));
        int dayOfMonth = cursor.getInt(cursor.getColumnIndex(COLUMN_DAY));
        int hourOfDay = cursor.getInt(cursor.getColumnIndex(COLUMN_HOUR));

        int month = cursor.getInt(cursor.getColumnIndex(COLUMN_MONTH));
        int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));


        Calendar storageCalendar = Calendar.getInstance();
        storageCalendar.set(Calendar.MINUTE, minute);
        storageCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        storageCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        storageCalendar.set(Calendar.MONTH, month);
        storageCalendar.set(Calendar.YEAR, year);

        Task task = new Task(ime, opis, prioritet, storageCalendar,
                reminder == 1 ? true : false,
                zavrsen == 1 ? true : false);

        return task;

    }
}
