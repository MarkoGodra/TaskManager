package ra63_2014.pnrs1.rtrk.taskmanager;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by root on 18.4.17..
 */

public class Task implements Serializable {

    private String ime;
    private String opis;
    private int prioritet;
    private Calendar calendar;
    private boolean reminder;
    private boolean zavrsen;

    public Task(){
        ime = "";
        opis = "";
        prioritet = -1;
        calendar = Calendar.getInstance();
        reminder = false;
        zavrsen = false;
    }

    public Task(String ime, String opis, int prioritet, Calendar calendar, boolean reminder, boolean zavrsen){
        this.ime = ime;
        this.opis = opis;
        this.prioritet = prioritet;
        this.reminder = reminder;
        this.zavrsen = zavrsen;
        this.calendar = calendar;
    }

    /*

        Setters and getters

     */

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getPrioritet() {
        return prioritet;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public boolean isZavrsen() {
        return zavrsen;
    }

    public void setZavrsen(boolean zavrsen) {
        this.zavrsen = zavrsen;
    }
}
