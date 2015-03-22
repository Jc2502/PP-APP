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
        TextView oi =(TextView) convertView.findViewById(R.id.object_id);
        TextView CF = (TextView) convertView.findViewById(R.id.CFText);
        TextView min = (TextView) convertView.findViewById(R.id.minText);
        TextView det = (TextView) convertView.findViewById(R.id.date_adapt);

        CF.setText(events.get(position).getName());
        min.setText(events.get(position).getDate());
        det.setText(events.get(position).getDescription());
        oi.setText(events.get(position).getObjectId());

        return convertView;
    }

}
