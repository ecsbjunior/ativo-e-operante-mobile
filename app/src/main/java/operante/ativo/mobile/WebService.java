package operante.ativo.mobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {
    public static String getService(String _URL) {
        String dados = "";

        try {
            URL url = new URL(_URL);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST)
                is = conexao.getInputStream();
            else
                is = conexao.getErrorStream();
            dados = buildAnswer(is);
            is.close();
            conexao.disconnect();
        }
        catch (Exception e) {
            dados = "Erro: " + e.getMessage();
        }

        return dados;
    }

    public static String postService(String _URL) {
        String dados = "";

        try {
            URL url = new URL(_URL);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST)
                is = conexao.getInputStream();
            else
                is = conexao.getErrorStream();
            dados = buildAnswer(is);
            is.close();
            conexao.disconnect();
        }
        catch (Exception e) {
            dados = "Erro: " + e.getMessage();
        }

        return dados;
    }

    private static String buildAnswer(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try {
            String linha;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null)
                buffer.append(linha);
            br.close();
        }
        catch (Exception e) {
            buffer.append("Erro:" + e.getMessage());
        }
        return buffer.toString();
    }
}
