package operante.ativo.mobile;

import android.os.AsyncTask;

public class WebServicePostAsync extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... strings) {
        String ans = WebService.postService(strings[0]);
        return ans;
    }
}