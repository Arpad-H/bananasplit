package com.example.bananasplit.util.text;

import android.content.Context;
import android.util.Log;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provides text analysis functionality using the Google Cloud Natural Language API.
 * The API is used to extract entities from text.
 * @author Arpad Horvath
 */
public class TextAnalysisProvider {

    private final Context context;
    private final ExecutorService executorService;

    /**
     * Creates a new TextAnalysisProvider instance.
     * @param context The application context.
     */
    public TextAnalysisProvider(Context context) {
        this.context = context;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Callback interface for text analysis results.
     */
    public interface TextAnalysisCallback {
        void onAnalysisSuccess(List<Entity> entities);
        void onAnalysisFailure(Exception e);
    }

    /**
     * Analyzes the given text and returns a list of entities found in the text.
     * based on the Google Cloud Natural Language API documentation.
     * <a href="https://cloud.google.com/natural-language/docs/reference/libraries#client-libraries-install-java">...</a>
     * <a href="https://cloud.google.com/docs/authentication/application-default-credentials#GAC">...</a>
     * <a href="https://stackoverflow.com/questions/6343166/how-can-i-fix-android-os-networkonmainthreadexception">...</a>
     * @param text The text to analyze.
     * @param callback The callback to receive the analysis results.
     */
    public void analyzeText(String text, TextAnalysisCallback callback) {
        executorService.execute(() -> {
            try (InputStream credentialsStream = context.getAssets().open("civil-oarlock-430219-t8-1dd96ae6ae58.json")) { //ChatGP prompt: where to get the download version of the credentials file and where ist it used for authentication
                GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
                LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();

                try (LanguageServiceClient languageServiceClient = LanguageServiceClient.create(settings)) {
                    Document doc = Document.newBuilder()
                            .setContent(text)
                            .setType(Document.Type.PLAIN_TEXT)
                            .setLanguage("de")
                            .build();

                    AnalyzeEntitiesResponse response = languageServiceClient.analyzeEntities(doc);
                    List<Entity> entities = response.getEntitiesList();
                    callback.onAnalysisSuccess(entities);
                }
            } catch (IOException e) {
                Log.e("TextAnalysisManager", "Failed to analyze text", e);
                callback.onAnalysisFailure(e);
            }
        });
    }

    /**
     * Shuts down the executor service.
     */
    public void shutdown() {
        executorService.shutdown();
    }
}
