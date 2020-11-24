package operante.ativo.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import operante.ativo.mobile.singletons.URLSingleton;

public class signup extends AppCompatActivity {
    private Button btnSUConfirm;
    private TextView txtEmail, txtSUCPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_signup);

        txtEmail = findViewById(R.id.txtEmail);
        txtSUCPF = findViewById(R.id.txtSUCPF);
        btnSUConfirm = findViewById(R.id.btnSUConfirm);

        btnSUConfirm.setOnClickListener(e -> handleSignup());
    }

    private void handleSignup() {
        if(txtEmail.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "O email não pode ficar vazio!", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(txtSUCPF.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "O CPF não pode ficar vazio!", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            try {
                WebServicePostAsync WSAsync = new WebServicePostAsync();
                String URL = String.format(URLSingleton.getURL() + "/users?cpf=%s&email=%s&apikey=%s", txtSUCPF.getText(), txtEmail.getText(), URLSingleton.getApiKey());
                String ans = WSAsync.execute(URL).get();
                JSONObject json = new JSONObject(ans);

                if (json.getBoolean("status")) {
                    SharedPreferences spref = getSharedPreferences("AEO::user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = spref.edit();

                    editor.putString("id", json.getString("id"));
                    editor.putString("api_key", json.getString("apikey"));

                    editor.commit();

                    URLSingleton.setApiKey(spref.getString("api_key", ""));

                    Intent intent = new Intent(this, complaints.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(this, "Não foi possivel criar o usuario!", Toast.LENGTH_LONG);
                    toast.show();
                }
            } catch (Exception e) {
                Log.d("AEO Exception: ", e.getMessage());
            }
        }
    }


}