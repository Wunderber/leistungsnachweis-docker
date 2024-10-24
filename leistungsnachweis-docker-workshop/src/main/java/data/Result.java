package data;

public class Result {
    private String customerId;
    private long consumption;

    public Result(String customerId, long consumption) {
        this.customerId = customerId;
        this.consumption = consumption;
    }

    public String getCustomerId() {
        return customerId;
    }

    public long getConsumption() {
        return consumption;
    }
}
