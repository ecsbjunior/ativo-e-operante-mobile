package operante.ativo.mobile;

import android.os.AsyncTask;

public class WebServiceAsync extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... strings) {
        String ans = WebService.getService(strings[0]);
        return ans;
    }
}
