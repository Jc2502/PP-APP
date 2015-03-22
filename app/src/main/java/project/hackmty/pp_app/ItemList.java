package project.hackmty.pp_app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.melnykov.fab.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ItemList extends ActionBarActivity {

    FloatingActionButton add_element;
    private ListView item_list;
    public ItemAdapter adapter;
    private static final String DATEF = "yyyy-MM-dd HH:mm:ss";
    ArrayList<Item>items_list;
    private Gson gson = new GsonBuilder().setDateFormat(DATEF).create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Log.e("ID_EVENT", getIntent().getStringExtra("event_id"));
        item_list = (ListView) findViewById(R.id.list_item);
        add_element =(FloatingActionButton) findViewById(R.id.fab_item);

        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = getResources().getString(R.string.url_endPoint)+"/api/v1/items/";

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonEvents = null;
                        try {
                            jsonEvents = response.getJSONArray("items");
                            Log.e("JSONARRAY", jsonEvents.toString());
                            ArrayList<Item> items = new ArrayList<Item>();
                            for (int i =0; i<jsonEvents.length();i++){
                                try {
                                    items.add(gson.fromJson(jsonEvents.getJSONObject(i).toString(), Item.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            items_list = items;
                            adapter = new ItemAdapter(getApplicationContext(),items_list);
                            adapter.notifyDataSetChanged();
                            item_list.setAdapter(adapter);

                            item_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                public boolean onItemLongClick(final AdapterView<?> arg0, View arg1,
                                                               final int pos, long id) {
                                    final Event event = (Event) item_list.getAdapter().getItem(pos);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    //String url = getResources().getString(R.string.url_endPoint) + "/api/v1/items/3" + event.getObjectId();
                                    String url = getResources().getString(R.string.url_endPoint) + "/api/v1/items/3";

                                    JsonObjectRequest dr = new JsonObjectRequest(Request.Method.DELETE, url,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    // response
                                                    try {
                                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                                    } catch (JSONException jsonEx) {
                                                        jsonEx.printStackTrace();
                                                    }
                                                    items_list.remove(pos);
                                                    adapter.notifyDataSetChanged();

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error.

                                                }
                                            }
                                    );
                                    queue.add(dr);

                                    return false;
                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d("TAG", response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("TAasdokasdasdasdadaG", "Error: " + new String(error.networkResponse.data));
                Log.e("TASK",new String(error.networkResponse.data));
                if(error.networkResponse.statusCode == 400)
                {
                    Log.e("TOAST-->","Error");
                }
            }
        }) {
            //Configurando Headers para que tome JSON

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        queue.add(req);

        add_element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"HOLA",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
