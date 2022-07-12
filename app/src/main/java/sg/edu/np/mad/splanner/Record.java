package sg.edu.np.mad.splanner;

public class Record {
    String recordName;
    String recordComment;
    String timeValue;

    public Record(){
    }

    public Record(String recordName, String recordComment, String timeValue){
        this.recordName = recordName;
        this.recordComment = recordComment;
        this.timeValue = timeValue;
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
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }
}
