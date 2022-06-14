package sg.edu.np.mad.splanner;

public class schedule {
    private String module;
    private String classname;
    private String timing;
    private String username;

    public schedule(String module, String classname, String timing, String username) {
        this.module = module;
        this.classname = classname;
        this.timing = timing;
        this.username = username;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
