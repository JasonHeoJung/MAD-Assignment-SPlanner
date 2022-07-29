package sg.edu.np.mad.splanner;

public class journal{
    public String title;
    public String description;
    public String date;

    public journal() {
    }

    public journal(String title, String des, String date) {
        this.title = title;
        this.description = des;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
