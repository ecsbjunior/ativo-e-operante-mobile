package operante.ativo.mobile.model;

public class User {
    private int id;
    private String CPF, email, APIKEY;

    public User(int id, String CPF, String email, String APIKEY) {
        this.id = id;
        this.CPF = CPF;
        this.email = email;
        this.APIKEY = APIKEY;
    }

    public User(String CPF, String email, String APIKEY) {
        this(0, CPF, email, APIKEY);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String aPIKEY) {
        APIKEY = aPIKEY;
    }
}