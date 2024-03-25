package com.example.chatgptui;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIRequestTask extends AsyncTask<String, Void, String> {
    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";
    private TextView responseTextView;

    public OpenAIRequestTask(TextView responseTextView) {
        this.responseTextView = responseTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String inputText = params[0];
        String response = "";

        try {
            // Create URL for the OpenAI API endpoint
            URL url = new URL("https://api.openai.com/v1/engines/davinci/completions");

            // Create HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + "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");
            connection.setDoOutput(true);

            // Construct request body
            String requestBody = "{\"prompt\": \"" + inputText + "\", \"max_tokens\": 150}";
            connection.getOutputStream().write(requestBody.getBytes());

            // Get response from OpenAI
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                System.out.println(line);
            }
            reader.close();
            response = stringBuilder.toString();

            // Close connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        // Update UI with the response
        responseTextView.setText(result);
    }
}