package data;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Sender {
    private static final String POST_URL = "http://localhost:8080/v1/result";  // The URL to send POST requests to
    private static final Gson gson = new Gson();  // Gson instance for JSON serialization

    /**
     * Sends the calculated results via a POST request to the server.
     *
     * @param results List of Result objects to be sent
     * @throws Exception if any error occurs during the request
     */
    public void sendResults(List<Result> results) throws Exception {
        // Convert the list of Result objects to a JSON string
        String jsonPayload = gson.toJson(new ResultPayload(results));

        // Prepare the POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(POST_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Create an HTTP client and send the request
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Display the response status code and body
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    // Helper class to structure the JSON payload format
    static class ResultPayload {
        private List<Result> result;

        // Constructor that accepts a list of Result objects
        public ResultPayload(List<Result> result) {
            this.result = result;
        }
    }
}
