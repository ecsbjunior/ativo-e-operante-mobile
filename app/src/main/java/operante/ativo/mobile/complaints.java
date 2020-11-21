package operante.ativo.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import operante.ativo.mobile.adapters.ComplaintAdapter;
import operante.ativo.mobile.model.Complaint;
import operante.ativo.mobile.singletons.URLSingleton;

public class complaints extends AppCompatActivity {
    private String api_key, user_id;
    private Button btnNewComplaint;
    private ListView viewComplaints;
    private ArrayList<Complaint> complaints = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_complaints);

        viewComplaints = findViewById(R.id.viewComplaints);
        btnNewComplaint = findViewById(R.id.btnNewComplaint);

        loadAPIKEY();
        loadComplaints();

        ComplaintAdapter adapter = new ComplaintAdapter(this, R.layout.complaint_item, complaints);
        viewComplaints.setAdapter(adapter);

        viewComplaints.setOnItemClickListener((adapterView, view, i, l) -> handleClickListView(i));
        btnNewComplaint.setOnClickListener(e -> goNewComplaint());
    }

    private void handleClickListView(int i) {
        Complaint complaint = complaints.get(i);

        Intent intent = new Intent(this, complaint.class);

        intent.putExtra("id", complaint.getId()+"");
        intent.putExtra("title", complaint.getTitle());
        intent.putExtra("description", complaint.getDescription());
        intent.putExtra("urgency", complaint.getUrgency()+"");
        intent.putExtra("problemType", complaint.getProblemType().getName());
        intent.putExtra("competentOrgan", complaint.getCompetentOrgan().getName());

        startActivity(intent);
    }

    private  void goNewComplaint() {
        Intent intent = new Intent(this, new_complaint.class);
        startActivity(intent);
    }

    private void loadComplaints() {
        try {
            WebServiceAsync WSAsync = new WebServiceAsync();
            String URL = String.format(URLSingleton.getURL() + "/complaints?user_id=%s", user_id);
            String ans = WSAsync.execute(URL).get();
            JSONArray jsonArray = new JSONArray(ans);
            Gson gson = new Gson();

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                complaints.add(gson.fromJson(json.toString(), Complaint.class));
            }
        }
        catch (Exception e) {
            Log.d("AEO Exception: ", e.getMessage());
        }
    }

    private void loadAPIKEY() {
        SharedPreferences spref = getSharedPreferences("AEO::user", MODE_PRIVATE);

        user_id = spref.getString("id", "");
        api_key = spref.getString("api_key", "");

        if(api_key.isEmpty()) {
            Toast toast = Toast.makeText(this, "Não foi possível carregar a API KEY", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}