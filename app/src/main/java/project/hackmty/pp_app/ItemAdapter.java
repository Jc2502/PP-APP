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
public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items){
        this.context =context;
        this.items = items;
    }

    public int getCount() {
        return this.items.size();
    }

    public Item getItem(int position) {
        return this.items.get(position);
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
        TextView oi =(TextView) convertView.findViewById(R.id.name_item_text);
        TextView CF = (TextView) convertView.findViewById(R.id.name_item_text);
        TextView min = (TextView) convertView.findViewById(R.id.minText);
        TextView det = (TextView) convertView.findViewById(R.id.price_adapt);

        CF.setText(items.get(position).getName());
        min.setText(items.get(position).getMarket());
        det.setText(items.get(position).getPrice());
        oi.setText(items.get(position).getEvent_id());

        return convertView;
    }

}
