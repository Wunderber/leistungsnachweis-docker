package data;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("customerId")
    private String customerId;

    @SerializedName("workloadId")
    private String workloadId;

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("eventType")
    private String eventType;

    // Getter and Setter

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(String workloadId) {
        this.workloadId = workloadId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "customerId='" + customerId + '\'' +
                ", workloadId='" + workloadId + '\'' +
                ", timestamp=" + timestamp +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
