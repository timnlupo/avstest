package com.amazon.alexa.avs.unity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnityInterface {

    private static final Logger log = LoggerFactory.getLogger(UnityInterface.class);

    public UnityInterface() {
      super();
    }

    public static void updateUnityAlexa(String input) {
      log.error(input);
    }

    public static void updateOnProcessing() {
      log.error("processing");
    }

    public static void updateOnListening() {
      log.error("listening");
    }

    public static void updateOnProcessingFinished() {
      log.error("processing finished");
    }

}
