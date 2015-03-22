package project.hackmty.pp_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.gc.materialdesign.views.ButtonFlat;

/**
 * Created by Juan Acosta on 3/21/2015.
 */
public class Login extends ActionBarActivity {

    public ButtonFlat Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
       // setToolbar();
        Login = (ButtonFlat) findViewById(R.id.login_btn);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Eventos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }



    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);
    }
}

