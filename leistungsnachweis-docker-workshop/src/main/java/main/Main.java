package main;

import data.*;
import data.Sender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            // URL to fetch the dataset
            String url = "http://localhost:8080/v1/dataset";
            Fetcher dataFetcher = new Fetcher();

            // Fetch the dataset from the provided URL
            Dataset dataset = dataFetcher.fetchDataset(url);

            // Calculate total usage time per customer based on events
            Map<String, Long> customerUsageTime = calculateUsageTime(dataset.getEvents());

            // Print the calculated usage times
            displayUsageTimes(customerUsageTime);

            // Convert usage times to Result objects for sending
            List<Result> results = convertToResults(customerUsageTime);

            // Send the results to the target system
            Sender dataSender = new Sender();
            dataSender.sendResults(results);

        } catch (Exception e) {
            // Handle any errors that occur during fetching or sending data
            System.err.println("Error while fetching or sending data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Calculates the usage time for each customer based on start and stop events.
     *
     * @param events List of events containing start and stop actions
     * @return A map of customer IDs to their total usage time
     */
    public static Map<String, Long> calculateUsageTime(List<Event> events) {
        Map<String, Long> startTimeMap = new HashMap<>();
        Map<String, Long> customerUsageTime = new HashMap<>();

        // First phase: Process all start events and record the start time
        for (Event event : events) {
            if (event.getEventType().equals("start")) {
                String customerId = event.getCustomerId();
                String workloadId = event.getWorkloadId();
                String key = customerId + "-" + workloadId;

                // Store the start timestamp for each customer-workload pair
                startTimeMap.put(key, event.getTimestamp());
            }
        }

        // Second phase: Process stop events and calculate usage time
        for (Event event : events) {
            if (event.getEventType().equals("stop")) {
                String customerId = event.getCustomerId();
                String workloadId = event.getWorkloadId();
                String key = customerId + "-" + workloadId; // Unique key for customer-workload pair

                // If there's a corresponding start event, calculate the time difference
                if (startTimeMap.containsKey(key)) {
                    long startTime = startTimeMap.get(key);
                    long endTime = event.getTimestamp();
                    long usageTime = endTime - startTime;

                    // Add the usage time to the customer's total
                    customerUsageTime.put(customerId, customerUsageTime.getOrDefault(customerId, 0L) + usageTime);

                    // Remove the start entry as the process is complete
                    startTimeMap.remove(key);
                }
            }
        }

        return customerUsageTime;
    }

    /**
     * Displays the calculated total usage time for each customer on the console.
     *
     * @param customerUsageTime Map of customer IDs to their usage time
     */
    public static void displayUsageTimes(Map<String, Long> customerUsageTime) {
        System.out.println("Calculated usage time per customer:");
        for (Map.Entry<String, Long> entry : customerUsageTime.entrySet()) {
            System.out.println("Customer ID: " + entry.getKey() + " - Usage time: " + entry.getValue() + " ms");
        }
        System.out.println();
    }

    /**
     * Converts the calculated usage time into a list of Result objects.
     *
     * @param customerUsageTime Map of customer IDs to their usage time
     * @return A list of Result objects for POST requests
     */
    public static List<Result> convertToResults(Map<String, Long> customerUsageTime) {
        List<Result> results = new ArrayList<>();
        for (Map.Entry<String, Long> entry : customerUsageTime.entrySet()) {
            results.add(new Result(entry.getKey(), entry.getValue()));
        }
        return results;
    }
}
