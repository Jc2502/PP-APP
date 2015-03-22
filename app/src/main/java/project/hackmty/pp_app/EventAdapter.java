package project.hackmty.pp_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juan Acosta on 3/22/2015.
 */
public class EventAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Event> events;

    public EventAdapter(Context context, ArrayList<Event> events){
        this.context =context;
        this.events = events;
    }

    public int getCount() {
        return this.events.size();
    }

    public Event getItem(int position) {
        return this.events.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.event, null);
        }
        TextView mEventName = (TextView) convertView.findViewById(R.id.event_name);
        TextView mLocationAdd = (TextView) convertView.findViewById(R.id.locadd);
        TextView mDateAdd = (TextView) convertView.findViewById(R.id.date_tv);
        TextView mDescription = (TextView) convertView.findViewById(R.id.description_tv);


        mEventName.setText(events.get(position).getName());
        mDateAdd.setText(events.get(position).getDate());
       mLocationAdd.setText(events.get(position).getLocation());
        mDescription.setText(events.get(position).getDescription());

        return convertView;
    }

}
