package ses.attendance_system_teacher.model;

public class Teacher {
    private String teacher_id;
    private String UID;
    private String teacher_name;
    private String teacher_email;

    public Teacher() {
    }

    public String getId() {
        return teacher_id;
    }

    public void setId(String id) {
        this.teacher_id = id;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return teacher_name;
    }

    public void setName(String name) {
        this.teacher_name = name;
    }

    public String getEmail() {
        return teacher_email;
    }

    public void setEmail(String email) {
        this.teacher_email = email;
    }

    public Teacher(String teacher_id, String UID, String teacher_name, String teacher_email) {
        this.teacher_id = teacher_id;
        this.UID = UID;
        this.teacher_name = teacher_name;
        this.teacher_email = teacher_email;
    }
}
