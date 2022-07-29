package sg.edu.np.mad.splanner;

public class Music {
    String name;
    String url;

    public Music() {

    }

    public Music(String n, String u) {
        this.name = n;
        this.url = u;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
