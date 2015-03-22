package project.hackmty.pp_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.views.ButtonFlat;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Create_Event extends ActionBarActivity {


    MaterialEditText mNameEvent,mDateEvent,mDescriptionEvent, mLocAddressEvent;
    ButtonFlat plan_event;
    String name,date;
    private final String TAG = "TEST";

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        plan_event = (ButtonFlat) findViewById(R.id.buttoncreateEvent);
        mNameEvent =(MaterialEditText) findViewById(R.id.editTextnameevent);
        mDateEvent = (MaterialEditText) findViewById(R.id.editTextdate);
        mDescriptionEvent =(MaterialEditText) findViewById(R.id.description_event);
        mLocAddressEvent = (MaterialEditText) findViewById(R.id.loc_address);

        plan_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreateEvent();
            }
        });
    }

    private void attemptCreateEvent(){
            mNameEvent.setError(null);
            mDescriptionEvent.setError(null);
            mLocAddressEvent.setError(null);
            mDateEvent.setError(null);


            final String name = mNameEvent.getText().toString();
            final String dateEvent = mDateEvent.getText().toString();
            final String description = mDescriptionEvent.getText().toString();
            final String locationAddress = mLocAddressEvent.getText().toString();

        mLocAddressEvent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptCreateEvent();
                    return true;
                }
                return false;
            }
        });


            boolean cancel = false;
            View focusView = null;

            if(TextUtils.isEmpty(locationAddress)){
                mLocAddressEvent.setError("This Field is Required");
                focusView = mLocAddressEvent;
                cancel = true;
            }

            if(TextUtils.isEmpty(description)){
                mDescriptionEvent.setError("This Field is Required");
                focusView = mDescriptionEvent;
                cancel = true;
            }

            if(TextUtils.isEmpty(dateEvent)){
                mDateEvent.setError("This Field is Required");
                focusView = mDateEvent;
                cancel = true;
            }

            if(TextUtils.isEmpty(name)){
                mNameEvent.setError("This Field is Required");
                focusView = mNameEvent;
                cancel = true;
            }

            if(cancel){
                focusView.requestFocus();
            }else{
                plan_event.setEnabled(false);
                String tag_json_obj = "json_obj_req";
                String url =getResources().getString(R.string.url_endPoint)+"/api/v1/events/";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                //Post Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("date", dateEvent);
                params.put("location", locationAddress);
                params.put("owner_id","0");
                params.put("description", description);

                String URL = getResources().getString(R.string.url_endPoint)+"/api/v1/events/";

                JsonObjectRequest req = new JsonObjectRequest(
                        Request.Method.POST,
                        URL,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                    //Ir a la actividad de ITEMS y que se borre esta actividad
                                plan_event.setEnabled(true);
                                startActivity(new Intent(getApplicationContext(),ItemList.class));
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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
            }

    }
}

