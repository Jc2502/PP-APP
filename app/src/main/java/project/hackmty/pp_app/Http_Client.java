package project.hackmty.pp_app;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Juan Acosta on 3/21/2015.
 */
public class Http_Client {

    private static final String DATEF = "yyyy-MM-dd HH:mm:ss";
    private Gson gson = new GsonBuilder().setDateFormat(DATEF).create();

    public static String SendEvent(String name,String date,String location,String owner_id,String description) throws JSONException {
        BufferedReader bufferedReader = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://10.20.55.105:8000/api/v1/events/");
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("name", name));
        postParameters.add(new BasicNameValuePair("date",date));
        postParameters.add(new BasicNameValuePair("location",location));
        postParameters.add(new BasicNameValuePair("owner_id",owner_id));
        postParameters.add(new BasicNameValuePair("description",description));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            String value = stringBuffer.toString();
            if (value.equals("null")) {
                return null;
            } else {
                JSONObject jsonObject = new JSONObject(value);
                Log.d("JARRAY",String.valueOf(jsonObject));
                return jsonObject.getString("error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
