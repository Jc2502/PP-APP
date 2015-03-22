package project.hackmty.pp_app;

import android.content.Intent;
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


public class Eventos extends ActionBarActivity {

    FloatingActionButton create_event;
    private ListView EventosList;
    public EventAdapter adapter;
    private static final String DATEF = "yyyy-MM-dd HH:mm:ss";
    ArrayList<Event>eventos_list;
    private Gson gson = new GsonBuilder().setDateFormat(DATEF).create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        create_event = (FloatingActionButton) findViewById(R.id.fab);
        EventosList =(ListView) findViewById(R.id.ListViewEvents);


        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = getResources().getString(R.string.url_endPoint)+"/api/v1/events/";

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonEvents = null;
                        try {
                            jsonEvents = response.getJSONArray("events");
                            Log.e("JSONARRAY",jsonEvents.toString());
                            ArrayList<Event> events = new ArrayList<Event>();
                            for (int i =0; i<jsonEvents.length();i++){
                                try {
                                    events.add(gson.fromJson(jsonEvents.getJSONObject(i).toString(), Event.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            eventos_list = events;
                            adapter = new EventAdapter(getApplicationContext(),eventos_list);
                            adapter.notifyDataSetChanged();
                            EventosList.setAdapter(adapter);
                            EventosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                                    final Event event = (Event)  EventosList.getAdapter().getItem(position);
                                    String item = event.getObjectId();
                                    Intent intent = new Intent(getApplicationContext(),ItemList.class);
                                    intent.putExtra("event_id", adapter.getItem(position).getObjectId());
                                    startActivity(intent);
                                    //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                                }
                            });
                            EventosList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                public boolean onItemLongClick(final AdapterView<?> arg0, View arg1,
                                                               final int pos, long id) {
                                    final Event event = (Event)  EventosList.getAdapter().getItem(pos);
                                    RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());
                                    String url = getResources().getString(R.string.url_endPoint)+"/api/v1/events/"+event.getObjectId();

                                    JsonObjectRequest dr = new JsonObjectRequest(Request.Method.DELETE, url,
                                            new Response.Listener<JSONObject>()
                                            {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    // response
                                                    try {
                                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                                    }catch (JSONException jsonEx){
                                                        jsonEx.printStackTrace();
                                                    }
                                                    eventos_list.remove(pos);
                                                    adapter.notifyDataSetChanged();

                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
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
        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Create_Event.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

}


