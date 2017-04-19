package ra63_2014.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 18.4.17..
 */

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Task> list;
    private Date date;
    private Calendar tempCalendar;

    private class ViewHolder{
        public TextView ime = null;
        public TextView date = null;
        public Button urg = null;
        public CheckBox checkBox = null;
        public Button urgButton = null;
    }

    public TaskAdapter(Context context){
        this.context = context;
        list = new ArrayList<Task>();
    }

    public void addTask(Task task){
        list.add(task);
        notifyDataSetChanged();
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_row, null);

            ViewHolder holder = new ViewHolder();
            holder.ime = (TextView)view.findViewById(R.id.name_text);
            holder.date = (TextView)view.findViewById(R.id.edit);
            holder.urg = (Button) view.findViewById(R.id.colour_button);
            holder.checkBox = (CheckBox) view.findViewById(R.id.finished_check_box);
            holder.urgButton = (Button) view.findViewById(R.id.urgency_button);
            view.setTag(holder);
        }

        date = new Date();
        tempCalendar = Calendar.getInstance();

        Task task = (Task)getItem(position);
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.ime.setText(task.getIme());
        tempCalendar = task.getCalendar();
        date = task.getCalendar().getTime();
        holder.date.setText(Integer.toString(tempCalendar.get(Calendar.HOUR_OF_DAY)));

        return view;
    }
}
