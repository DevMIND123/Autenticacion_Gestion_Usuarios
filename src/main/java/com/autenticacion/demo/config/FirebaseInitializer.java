package com.autenticacion.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FirebaseInitializer {

    @PostConstruct
    public void initialize() throws IOException {
	
	Dotenv dotenv = Dotenv.configure()
            .filename("firebase.env") 
            .load();


        String privateKey = dotenv.get("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

        String json = "{"
                + "\"type\": \"" + dotenv.get("FIREBASE_TYPE") + "\","
                + "\"project_id\": \"" + dotenv.get("FIREBASE_PROJECT_ID") + "\","
                + "\"private_key_id\": \"" + dotenv.get("FIREBASE_PRIVATE_KEY_ID") + "\","
                + "\"private_key\": \"" + privateKey + "\","
                + "\"client_email\": \"" + dotenv.get("FIREBASE_CLIENT_EMAIL") + "\","
                + "\"client_id\": \"" + dotenv.get("FIREBASE_CLIENT_ID") + "\","
                + "\"auth_uri\": \"" + dotenv.get("FIREBASE_AUTH_URI") + "\","
                + "\"token_uri\": \"" + dotenv.get("FIREBASE_TOKEN_URI") + "\","
                + "\"auth_provider_x509_cert_url\": \"" + dotenv.get("FIREBASE_AUTH_PROVIDER_CERT_URL") + "\","
                + "\"client_x509_cert_url\": \"" + dotenv.get("FIREBASE_CLIENT_CERT_URL") + "\""
                + "}";

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(json.getBytes())))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        System.out.println("✅ Firebase inicializado con variables de entorno.");
    }
}
