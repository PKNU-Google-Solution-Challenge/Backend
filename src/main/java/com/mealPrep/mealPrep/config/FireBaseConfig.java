package com.mealPrep.mealPrep.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
@Configuration
public class FireBaseConfig {
    @PostConstruct
    public void initFirebase() {
        try {
            FileInputStream serviceAccount =
//                    new FileInputStream("/home/ubuntu/app/key/serviceAccountKey.json");
                    new FileInputStream("src/main/resources/mealPrepServiceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://friend-327a9.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
