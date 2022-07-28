package sg.edu.np.mad.splanner;

public class journal{
    public String Title;
    public String Description;
    public String date;

    public journal() {
    }

    public journal(String title, String des, String date) {
        this.Title = title;
        this.Description = des;
        this.date = date;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
