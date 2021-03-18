package ses.attendance_system_teacher.models;

public class Teacher {
    private String id;
    private String UID;
    private String username;
    private String email;

    public Teacher() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Teacher(String id, String UID, String username, String email) {
        this.id = id;
        this.UID = UID;
        this.username = username;
        this.email = email;
    }
}
