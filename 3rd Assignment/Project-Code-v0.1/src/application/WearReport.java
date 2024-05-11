package application;
public class WearReport {
    private int reportId;
    private String copyId;
    private String description;
    private String photoUrl;

    // Constructor, getters and setters
    public WearReport(String copyId, String description, String photoUrl) {
        this.copyId = copyId;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    // getters and setters
}
