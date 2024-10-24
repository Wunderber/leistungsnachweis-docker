package data;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

public class Fetcher {

    // HttpClient for sending HTTP requests
    private final HttpClient client;

    // Gson instance for parsing JSON responses
    private final Gson gson;

    // Constructor to initialize HttpClient and Gson
    public Fetcher() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Fetches a dataset from the given URL.
     *
     * @param url The URL from which to fetch the dataset
     * @return The fetched Dataset object
     * @throws Exception If any error occurs during the request
     */
    public Dataset fetchDataset(String url) throws Exception {
        // Create an HTTP GET request to the specified URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and get the response as a string
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the JSON response body into a Dataset object using Gson
        return gson.fromJson(response.body(), Dataset.class);
    }
}
