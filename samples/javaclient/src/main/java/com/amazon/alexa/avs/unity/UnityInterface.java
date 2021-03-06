package com.amazon.alexa.avs.unity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UnityInterface {

    private static final Logger log = LoggerFactory.getLogger(UnityInterface.class);

    private final static UnityInterface instance = new UnityInterface();
    private final String deviceID  = "bravo_alexa_001";

    private boolean firebaseReady = false;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private UnityInterface() {
      try {
            File fbauthfile = new File("src/main/java/com/amazon/alexa/avs/unity/alexa-unity-firebase-adminsdk-e32w2-65c478ce9f.json");
            FileInputStream serviceAccount = new FileInputStream(fbauthfile);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://alexa-unity.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
            this.database = FirebaseDatabase.getInstance();
            this.ref = database.getReference().child(deviceID);
            this.firebaseReady = true;
        } catch (FileNotFoundException e) {
            log.error("Invalid Firebase Initialization FileNotFoundException: " + e.getMessage());
            //System.exit(1);
        } catch (IOException e) {
          log.error("Invalid Firebase Initialization IOException: " + e.getMessage());
        }
    }

    public static UnityInterface getInstance() {
      return instance;
    }

    public void updateUnityAlexa(String input) {
      DatabaseReference dataRef = this.ref.child("data");
      Map<String, Object> jsonMap = new Gson().fromJson(input, new TypeToken<HashMap<String, Object>>() {}.getType());
      postFirebase(jsonMap, dataRef, true);
    }

    public void updateOnListening() {
      DatabaseReference dataRef = this.ref.child("isListening");
      postFirebase(true, dataRef, false);
    }

    public void updateOnProcessing() {
      DatabaseReference dataRef1 = this.ref.child("isListening");
      DatabaseReference dataRef2 = this.ref.child("isProcessing");
      postFirebase(false, dataRef1, false);
      postFirebase(true, dataRef2, false);
    }

    public void updateOnProcessingFinished() {
      DatabaseReference dataRef = this.ref.child("isProcessing");
      postFirebase(false, dataRef, false);
    }

    public void updateOnTalking() {
      DatabaseReference dataRef1 = this.ref.child("isProcessing");
      DatabaseReference dataRef2 = this.ref.child("isTalking");
      postFirebase(false, dataRef1, false);
      postFirebase(true, dataRef2, false);
    }

    public void updateOnTalkingFinished() {
      DatabaseReference dataRef = this.ref.child("isTalking");
      postFirebase(false, dataRef, false);
    }

    private void postFirebase(Object value, DatabaseReference dataRef, boolean isPush) {
      if (this.firebaseReady) {
        try {
          if (isPush) {
            dataRef.push().setValue(value, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        log.debug("FB: Data could not be saved - " + databaseError.getMessage());
                    } else {
                        log.debug("FB: Data saved successfully");
                    }
                }
            });
          } else {
            dataRef.setValue(value, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        log.debug("FB: Data could not be saved - " + databaseError.getMessage());
                    } else {
                        log.debug("FB: Data saved successfully");
                    }
                }
            });
          }
        } catch(Exception e) {
          log.error("FB: Exception found - " + e);
        }
      } else {
        log.error("FB: Not ready");
      }
    }

}
