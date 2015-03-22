package project.hackmty.pp_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Juan Acosta on 3/21/2015.
 */
public class Login extends Activity {

    public Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        Login = (Button) findViewById(R.id.login_btn);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Eventos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }
}

