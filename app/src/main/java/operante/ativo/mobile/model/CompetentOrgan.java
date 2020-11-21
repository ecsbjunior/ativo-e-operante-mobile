package operante.ativo.mobile.model;

import androidx.annotation.NonNull;

public class CompetentOrgan {
    private int id;
    private String name;

    public CompetentOrgan(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CompetentOrgan(String name) {
        this(0, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
