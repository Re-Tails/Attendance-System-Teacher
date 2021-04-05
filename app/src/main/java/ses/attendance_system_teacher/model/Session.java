package ses.attendance_system_teacher.model;

public class Session {
    private String session_location;
    private String session_date;
    private String session_start_time;
    private String session_end_time;
    private String session_subject;

    public String getSession_location() {
        return session_location;
    }

    public void setSession_location(String session_location) {
        this.session_location = session_location;
    }

    public String getSession_date() {
        return session_date;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }

    public String getSession_start_time() {
        return session_start_time;
    }

    public void setSession_start_time(String session_start_time) {
        this.session_start_time = session_start_time;
    }

    public String getSession_end_time() {
        return session_end_time;
    }

    public void setSession_end_time(String session_end_time) {
        this.session_end_time = session_end_time;
    }

    public String getSession_subject() {
        return session_subject;
    }

    public void setSession_subject(String session_subject) {
        this.session_subject = session_subject;
    }
}
