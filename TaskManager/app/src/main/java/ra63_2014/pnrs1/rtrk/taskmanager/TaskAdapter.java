package ra63_2014.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 18.4.17..
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<Task> list = new ArrayList<>();
    private Date date;
    private Calendar tempCalendar;
    private Calendar cal1;
    private Calendar cal2;

    private class ViewHolder {
        public TextView ime = null;
        public TextView date = null;
        public Button urg = null;
        public CheckBox checkBox = null;
        public Button urgButton = null;
    }

    public TaskAdapter(Context context) {
        this.context = context;
        list = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        list.add(task);
        notifyDataSetChanged();
    }

    public static ArrayList<Task> getList(){
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = list.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    public void update(Task[] tasks) {
        list.clear();
        if(tasks != null) {
            for(Task task : tasks) {
                list.add(task);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        Log.d("Deleting", "usao je ovde");

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_row, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.ime = (TextView) view.findViewById(R.id.name_text);
            holder.date = (TextView) view.findViewById(R.id.edit);
            holder.urg = (Button) view.findViewById(R.id.colour_button);
            holder.checkBox = (CheckBox) view.findViewById(R.id.finished_check_box);
            holder.urgButton = (Button) view.findViewById(R.id.urgency_button);
            view.setTag(holder);
        }

        date = new Date();
        tempCalendar = Calendar.getInstance();
        cal1 = Calendar.getInstance();
        cal2 = Calendar.getInstance();

        final Task task = (Task) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.ime.setText(task.getIme());
        tempCalendar = task.getCalendar();
        date = task.getCalendar().getTime();
        cal1.add(Calendar.DAY_OF_YEAR, 1);
        cal2.add(Calendar.DAY_OF_YEAR, 7);

        if (tempCalendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            holder.date.setText("Danas u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
        } else if (tempCalendar.get(Calendar.DAY_OF_YEAR) == cal1.get(Calendar.DAY_OF_YEAR)) {
            holder.date.setText("Sutra u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
        } else if (tempCalendar.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
            switch (tempCalendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    holder.date.setText("Nedelja u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.MONDAY:
                    holder.date.setText("Ponedeljak u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.TUESDAY:
                    holder.date.setText("Utorak u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.WEDNESDAY:
                    holder.date.setText("Sreda u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.THURSDAY:
                    holder.date.setText("Cetvrtak u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.FRIDAY:
                    holder.date.setText("Petak u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
                case Calendar.SATURDAY:
                    holder.date.setText("Subota u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + "" + " : " + tempCalendar.get(Calendar.MINUTE) + "");
                    break;
            }
        } else {
            holder.date.setText(tempCalendar.get(Calendar.DAY_OF_MONTH) + "" + "/" + (tempCalendar.get(Calendar.MONTH) + 1) + ""
                    + "/" + tempCalendar.get(Calendar.YEAR) + "" + " u " + tempCalendar.get(Calendar.HOUR_OF_DAY) + ""
                    + ":" + tempCalendar.get(Calendar.MINUTE) + "");
        }

        switch (task.getPrioritet()) {
            case 3:
                holder.urg.setBackgroundColor(context.getResources().getColor(R.color.red));
                break;
            case 2:
                holder.urg.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                break;
            case 1:
                holder.urg.setBackgroundColor(context.getResources().getColor(R.color.green));
                break;
        }

        if (task.isReminder())
            holder.urgButton.setBackgroundColor(context.getResources().getColor(R.color.black));
        else
            holder.urgButton.setBackgroundColor(context.getResources().getColor(R.color.white));


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.ime.setPaintFlags(holder.ime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    task.setZavrsen(true);
                } else {
                    holder.ime.setPaintFlags(holder.ime.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    task.setZavrsen(false);
                }


            }
        });


        holder.checkBox.setChecked(task.isZavrsen());

        return view;
    }
}
