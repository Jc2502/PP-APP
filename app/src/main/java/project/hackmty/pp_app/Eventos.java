package project.hackmty.pp_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
    private static final String DATEF = "yyyy-MM-dd HH:mm:ss";
    ArrayList<Event>eventos_list;
    private Gson gson = new GsonBuilder().setDateFormat(DATEF).create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        new EventTask(getApplicationContext(),gson,EventosList).execute();
        create_event = (FloatingActionButton) findViewById(R.id.fab);
        EventosList =(ListView) findViewById(R.id.ListViewEvents);
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = "http://10.12.172.234:8000/api/v1/events/";

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
                            EventAdapter adapter = new EventAdapter(getApplicationContext(),eventos_list);
                            EventosList.setAdapter(adapter);
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
class EventTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private Gson gson;
    private ListView lv;


    public EventTask(Context context, Gson gson,ListView lv) {
        this.context = context;
        this.gson = gson;
        this.lv = lv;
    }

    @Override
    protected Boolean doInBackground(String... params) {


        return false;

    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("ContactosTask", "Entro onPostExecute");

        //this.context.startActivity(new Intent(this.context, AudioConferencia.class));
        //og.d("CITAS == ", String.valueOf(this.eventos_ist.size()));

    }

}

