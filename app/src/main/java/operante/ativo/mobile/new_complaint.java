package operante.ativo.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import operante.ativo.mobile.model.CompetentOrgan;
import operante.ativo.mobile.model.Complaint;
import operante.ativo.mobile.model.ProblemType;
import operante.ativo.mobile.singletons.URLSingleton;

public class new_complaint extends AppCompatActivity {
    private Spinner spnCompetentOrgans, spnProblemTypes;
    private SeekBar sbUrgency;
    private TextView txtTitle, txtDescription;
    private Button btnNewSubmit;
    private ArrayList<ProblemType> problemTypes = new ArrayList();
    private ArrayList<CompetentOrgan> competentOrgans = new ArrayList();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_new_complaint);

        sbUrgency = findViewById(R.id.sbUrgency);
        txtTitle = findViewById(R.id.txtNewTitle);
        txtDescription = findViewById(R.id.txtNewDescription);
        btnNewSubmit = findViewById(R.id.btnNewSubmit);
        spnCompetentOrgans = findViewById(R.id.spnNewCompetentOrgans);
        spnProblemTypes = findViewById(R.id.spnProblemTypes);

        loadProblemTypes();
        loadCompetentOrgan();

        ArrayAdapter<CompetentOrgan> competentOrganAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, competentOrgans
        );
        ArrayAdapter<ProblemType> problemTypeAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, problemTypes
        );

        competentOrganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problemTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCompetentOrgans.setAdapter(competentOrganAdapter);
        spnProblemTypes.setAdapter(problemTypeAdapter);

        btnNewSubmit.setOnClickListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        SharedPreferences spref = getSharedPreferences("AEO::user", MODE_PRIVATE);
        String title = txtTitle.getText().toString();
        String description = txtDescription.getText().toString();
        int urgency = sbUrgency.getProgress() + 1;
        int user_id = Integer.parseInt(spref.getString("id", "0"));
        int competentorgan_id = ((CompetentOrgan)spnCompetentOrgans.getSelectedItem()).getId();
        int problemtype_id = ((ProblemType)spnProblemTypes.getSelectedItem()).getId();

        if (title.isEmpty()) {
            Toast.makeText(this, "O titulo não pode ficar vazio!", Toast.LENGTH_LONG).show();
        }
        else if(description.isEmpty()) {
            Toast.makeText(this, "A descrição não pode ficar vazia!", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                WebServicePostAsync WSAsync = new WebServicePostAsync();
                String URL = String.format(
                    "%s/%s?title=%s&description=%s&urgency=%s&user_id=%s&competentorgan_id=%s&problemtype_id=%s&apikey=%s",
                    URLSingleton.getURL(),
                    "complaints",
                    title,
                    description,
                    urgency+"",
                    user_id+"",
                    competentorgan_id+"",
                    problemtype_id+"",
                    URLSingleton.getApiKey()
                );
                String ans = WSAsync.execute(URL).get();
                JSONObject json = new JSONObject(ans);

                if (json.getBoolean("status")) {
                    Intent intent = new Intent(this, complaints.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Não foi possivel enviar a sua denuncia...", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e) {
                Log.d("AEO Exception: ", e.getMessage());
            }
        }

    }

    private void loadProblemTypes() {
        try {
            WebServiceAsync WSAsync = new WebServiceAsync();
            String URL = URLSingleton.getURL() + "/problem-types?apikey=" + URLSingleton.getApiKey();
            String ans = WSAsync.execute(URL).get();
            JSONArray jsonArray = new JSONArray(ans);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                problemTypes.add(gson.fromJson(json.toString(), ProblemType.class));
            }
        }
        catch(Exception e) {
            Log.d("AEO Exception: ", e.getMessage());
        }
    }

    private void loadCompetentOrgan() {
        try {
            WebServiceAsync WSAsync = new WebServiceAsync();
            String URL = URLSingleton.getURL() + "/competent-organs?apikey=" + URLSingleton.getApiKey();
            String ans = WSAsync.execute(URL).get();
            JSONArray jsonArray = new JSONArray(ans);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                competentOrgans.add(gson.fromJson(json.toString(), CompetentOrgan.class));
            }
        }
        catch(Exception e) {
            Log.d("AEO Exception: ", e.getMessage());
        }
    }
}