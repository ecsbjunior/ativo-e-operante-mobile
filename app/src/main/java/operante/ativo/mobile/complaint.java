package operante.ativo.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import operante.ativo.mobile.singletons.URLSingleton;

public class complaint extends AppCompatActivity {
    private TextView txtTitle, txtDescription, txtUrgency, txtProblemType, txtCompetentOrgan, txtCFeedbackMsg;
    private String complaint_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_complaint);

        txtTitle = findViewById(R.id.txtCTitle);
        txtDescription = findViewById(R.id.txtCDescription);
        txtUrgency = findViewById(R.id.txtCUrgency);
        txtProblemType = findViewById(R.id.txtCProblemType);
        txtCompetentOrgan = findViewById(R.id.txtCCompetentOrgan);
        txtCFeedbackMsg = findViewById(R.id.txtCFeedbackMsg);

        Intent intent = getIntent();

        complaint_id = intent.getStringExtra("id");
        txtTitle.setText(intent.getStringExtra("title"));
        txtDescription.setText(intent.getStringExtra("description"));
        txtUrgency.setText(intent.getStringExtra("urgency"));
        txtProblemType.setText(intent.getStringExtra("problemType"));
        txtCompetentOrgan.setText(intent.getStringExtra("competentOrgan"));

        if(txtUrgency.getText().toString().equals("1")) {
            txtUrgency.setBackgroundColor(getColor(R.color.colorPriority1));
        }
        else if(txtUrgency.getText().toString().equals("2")) {
            txtUrgency.setBackgroundColor(getColor(R.color.colorPriority2));
        }
        else if(txtUrgency.getText().toString().equals("3")) {
            txtUrgency.setBackgroundColor(getColor(R.color.colorPriority3));
        }
        else if(txtUrgency.getText().toString().equals("4")) {
            txtUrgency.setBackgroundColor(getColor(R.color.colorPriority4));
        }
        else {
            txtUrgency.setBackgroundColor(getColor(R.color.colorPriority5));
        }

        loadFeedbackMsg();
    }

    private void loadFeedbackMsg() {
        try {
            WebServiceAsync WSAsync = new WebServiceAsync();
            String URL = URLSingleton.getURL() + "/feedbacks/" + complaint_id;
            String ans = WSAsync.execute(URL).get();
            JSONArray jsonArray = new JSONArray(ans);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                txtCFeedbackMsg.setText(txtCFeedbackMsg.getText().toString() + "\n" + json.getString("description"));
            }
        }
        catch(Exception e) {
            Log.d("AEO Exception: ", e.getMessage());
        }
    }
}