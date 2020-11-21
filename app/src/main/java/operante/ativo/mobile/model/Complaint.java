package operante.ativo.mobile.model;

import java.util.ArrayList;

public class Complaint {
    private int id, urgency;
    private String title, description;
    private User user;
    private CompetentOrgan competentOrgan;
    private ProblemType problemType;
    private ArrayList<Image> images;

    public Complaint(int id, String title, String description, int urgency, User user, CompetentOrgan competentOrgan, ProblemType problemType, ArrayList<Image> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.user = user;
        this.competentOrgan = competentOrgan;
        this.problemType = problemType;
        this.images = images;
    }

    public Complaint(String title, String description, int urgency, User user, CompetentOrgan competentOrgan, ProblemType problemType, ArrayList<Image> images) {
        this(0, title, description, urgency, user, competentOrgan, problemType, images);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CompetentOrgan getCompetentOrgan() {
        return competentOrgan;
    }

    public void setCompetentOrgan(CompetentOrgan competentOrgan) {
        this.competentOrgan = competentOrgan;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
