package sg.edu.np.mad.splanner;

public class Record {
    String recordName;
    String recordComment;
    String timeSet;
    String timeTaken;

    public Record(){
    }

    public Record(String recordName, String recordComment, String timeSet, String timeTaken){
        this.recordName = recordName;
        this.recordComment = recordComment;
        this.timeSet = timeSet;
        this.timeTaken = timeTaken;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordComment() {
        return recordComment;
    }

    public void setRecordComment(String recordComment) {
        this.recordComment = recordComment;
    }

    public String getTimeValue() {
        return timeSet;
    }

    public void setTimeValue(String timeSet) {
        this.timeSet = timeSet;
    }

    public String getTimeTaken() { return timeTaken; }

    public void setTimeTaken(String timeTaken) { this.timeTaken = timeTaken; }
}
