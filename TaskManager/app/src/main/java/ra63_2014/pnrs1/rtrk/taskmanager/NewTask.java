package ra63_2014.pnrs1.rtrk.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class NewTask extends AppCompatActivity {

    private EditText editTextIme;
    private EditText editTextOpis;
    private Button btnDodaj;
    private Button btnOtkazi;
    private Button btnRed;
    private Button btnYlw;
    private Button btnGrn;
    private CheckBox checkBoxPodseti;
    private Button btnVreme;
    private Button btnDatum;
    private TextView textViewVreme;
    private TextView textViewDatum;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private boolean timeSet = false;
    private boolean dateSet = false;
    private boolean prioritySet = false;
    private Calendar storageCalendar;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        editTextIme = (EditText) findViewById(R.id.editTxtIme);
        editTextOpis = (EditText) findViewById(R.id.editTxtOpis);
        btnDodaj = (Button) findViewById(R.id.btnDodaj);
        btnOtkazi = (Button) findViewById(R.id.btnOtkazi);
        btnRed = (Button) findViewById(R.id.redBtn);
        btnYlw = (Button) findViewById(R.id.ylwBtn);
        btnGrn = (Button) findViewById(R.id.grnBtn);
        btnVreme = (Button) findViewById(R.id.btnVreme);
        btnDatum = (Button) findViewById(R.id.btnDatum);
        checkBoxPodseti = (CheckBox) findViewById(R.id.checkboxPodseti);
        textViewDatum = (TextView)findViewById(R.id.textViewDatum);
        textViewVreme = (TextView)findViewById(R.id.textViewVreme);

        calendar = Calendar.getInstance();
        storageCalendar = Calendar.getInstance();

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            //Called on time change
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textViewVreme.setText(Integer.toString(hourOfDay) + "h: " + Integer.toString(minute) + "m");
                setTimeSet(true);
                showToastTime(hourOfDay, minute);

                storageCalendar.set(Calendar.MINUTE, minute);
                storageCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            }
        };

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            //Called on date change
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textViewDatum.setText(Integer.toString(dayOfMonth) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year));
                setDateSet(true);
                showToastDate(year, month, dayOfMonth);

                storageCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                storageCalendar.set(Calendar.MONTH, month);
                storageCalendar.set(Calendar.YEAR, year);
            }
        };

        btnVreme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        btnDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrioritySet(true);
                btnYlw.setEnabled(false);
                btnGrn.setEnabled(false);
            }
        });

        btnYlw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrioritySet(true);
                btnGrn.setEnabled(false);
                btnRed.setEnabled(false);
            }
        });

        btnGrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrioritySet(true);
                btnRed.setEnabled(false);
                btnYlw.setEnabled(false);
            }
        });

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTimeSet() && isDateSet() && isPrioritySet()
                        && !editTextIme.getText().toString().isEmpty()
                        && !editTextOpis.getText().toString().isEmpty()){

                    //TODO: some operations will come here, adding items to list etc
                    //newTaskPresenter.addItemToList(editTextIme.getText().toString(), textViewDatum.getText().toString());

                    setPrioritySet(false);
                    setTimeSet(false);
                    setDateSet(false);

                    showToastCreated();
                    proceedOnStartActivity();
                    finish();

                } else {
                    showToastInvalid();
                }

            }
        });

        btnOtkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrioritySet(false);
                setTimeSet(false);
                setDateSet(false);
                showToastCanceled();
                proceedOnStartActivity();
                finish();
            }
        });
    }

    /*

        Side functions

     */

    public void showTimeDialog() {

        new TimePickerDialog(NewTask.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                .show();

    }

    public void showDateDialog() {

        new DatePickerDialog(NewTask.this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show();

    }


    public void showToastTime(int hour, int minute) {
        String msg = "Vreme isteka : " + Integer.toString(hour) + "h : " + Integer.toString(minute) + "m";
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
                .show();
    }


    public void showToastDate(int year, int month, int day) {
        String msg = "Datum isteka : " + Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
                .show();
    }


    public void proceedOnStartActivity() {
        Intent intent = new Intent(NewTask.this, StartActivity.class);
        startActivity(intent);
        finish();
    }


    public void showToastCreated() {
        Toast.makeText(this, "Zadatak dodat", Toast.LENGTH_SHORT)
                .show();
    }


    public void showToastCanceled() {
        Toast.makeText(this, "Otkazano", Toast.LENGTH_SHORT)
                .show();
    }


    public void showToastInvalid() {
        Toast.makeText(this, "Popuniti sva polja", Toast.LENGTH_SHORT)
                .show();
    }

    /*

        Setters and getters

     */


    public boolean isTimeSet() {
        return timeSet;
    }

    public void setTimeSet(boolean timeSet) {
        this.timeSet = timeSet;
    }

    public boolean isDateSet() {
        return dateSet;
    }

    public void setDateSet(boolean dateSet) {
        this.dateSet = dateSet;
    }

    public boolean isPrioritySet() {
        return prioritySet;
    }

    public void setPrioritySet(boolean prioritySet) {
        this.prioritySet = prioritySet;
    }

}
