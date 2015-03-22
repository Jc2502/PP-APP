package project.hackmty.pp_app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Create_Event extends ActionBarActivity {


    EditText nameevent,dateevent;
    Button plan_event;
    String name,date;
    double latitude, longitude;
    private final String TAG = "TEST";

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        plan_event = (Button) findViewById(R.id.buttoncreateEvent);
        nameevent =(EditText) findViewById(R.id.editTextnameevent);
        dateevent = (EditText) findViewById(R.id.editTextdate);

        plan_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameevent.getText().toString();
                date = dateevent.getText().toString();

                gps = new GPSTracker(Create_Event.this);
                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    // \n is for new line
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    Log.d("Location", "Latitude:" + latitude + ", Longitude:" + longitude);
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                if (nameevent.getText().toString().trim().length() > 0 && dateevent.getText().toString().trim().length() > 0) {
                    Log.d("DATOS", name + " || " + date);
                    String location = String.valueOf(latitude) +","+ String.valueOf(longitude);
                    //new GetEventTask(getApplicationContext(), name, date,location,"0"," ").execute();
                    String tag_json_obj = "json_obj_req";
                    String url ="http://10.20.55.105:8000/api/v1/events/";

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    //Post Params.
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("date", date);
                    params.put("location", location);
                    params.put("owner_id","0");
                    params.put("description", "asd");

                    String URL = "http://10.12.172.234:8000/api/v1/events/";

                    JsonObjectRequest req = new JsonObjectRequest(
                            Request.Method.POST,
                            URL,
                            new JSONObject(params),
                            new Response.Listener<JSONObject>(){

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", response.toString());

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //VolleyLog.d("TAasdokasdasdasdadaG", "Error: " + new String(error.networkResponse.data));
                            Log.e("TASK",new String(error.networkResponse.data));
                             if(error.networkResponse.statusCode == 400)
                             {
                                 Toast.makeText(getApplicationContext(),R.string.campos_requeridos,Toast.LENGTH_SHORT).show();
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

                } else {
                    Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

