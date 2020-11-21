package operante.ativo.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import operante.ativo.mobile.singletons.URLSingleton;

public class MainActivity extends AppCompatActivity {
    private String api_key;
    private Button btnSignup, btnSignin;
    private TextView txtCPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.btnSignup);
        btnSignin = findViewById(R.id.btnSignin);
        txtCPF = findViewById(R.id.txtCPF);

        btnSignin.setOnClickListener(e -> handleLogin());
        btnSignup.setOnClickListener(e -> goSignup());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            deleteSharedPreferences("AEO::user");
//        }

        handleSharedPreference();
    }

    private void handleLogin() {
        try {
            WebServiceAsync WSAsync = new WebServiceAsync();
            String URL = String.format(URLSingleton.getURL() + "/users/%s", txtCPF.getText().toString());
            String ans = WSAsync.execute(URL).get();
            JSONArray jsonArray = new JSONArray(ans);
            if (jsonArray.length() == 1) {
                JSONObject json = jsonArray.getJSONObject(0);

                SharedPreferences spref = getSharedPreferences("AEO::user", MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();

                editor.putString("id", json.getString("id"));
                editor.putString("api_key", json.getString("apikey"));

                editor.commit();

                handleSharedPreference();
            }
            else {
                Toast toast = Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        catch(Exception e) {
            Log.d("AEO Exception: ", e.getMessage());
        }
    }

    private void handleSharedPreference() {
        SharedPreferences spref = getSharedPreferences("AEO::user", MODE_PRIVATE);
        api_key = spref.getString("api_key", "");

        if (!api_key.isEmpty()) {
            URLSingleton.setApiKey(api_key);

            Intent intent = new Intent(this, complaints.class);
            startActivity(intent);
        }
    }

    private void goSignup() {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
}