package operante.ativo.mobile.singletons;

public class URLSingleton {
    private static String URL = "https://ativo-e-oprante-backend.herokuapp.com";
    private static String api_key = "";

    public static String getURL() {
        return URL;
    }

    public static String getApiKey() { return api_key; }

    public static void setApiKey(String apiKey) { api_key = apiKey; }
}
