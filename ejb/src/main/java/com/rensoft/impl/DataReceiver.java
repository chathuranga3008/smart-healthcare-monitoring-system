package com.rensoft.impl;

import com.rensoft.model.PatientData;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.Stateful;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Stateful
@MessageDriven(mappedName = "jms/Queue")
public class DataReceiver implements MessageListener {
    private PatientDataCache cache;

    public DataReceiver() {
    }

    public DataReceiver(PatientDataCache cache) {
        this.cache = cache;
    }

    @Override
    public void onMessage(Message message) {
        String patientId = "";
        try {
            if (message instanceof TextMessage) {
                String messageText = ((TextMessage) message).getText();
                PatientData patientData = processMessage(patientId, messageText);
                cache.addPatientData(patientId, patientData);

                int responseCode = getResponseCode(patientData);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    System.out.println("Request sent successfully");
                } else {
                    System.err.println("Request send failed. Response code: " + responseCode);
                }
            }
        } catch (JMSException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private int getResponseCode(PatientData patientData) throws URISyntaxException, IOException {
        String servletURL = "http://localhost:8080/SHMS/device";
        URL url = new URI(servletURL + "&hr=" + patientData.getHeartRate() + "&bp=" + patientData.getBloodPressure() + "&ma=" + patientData.isMedicationAdherence() + "&pl=" + patientData.getLocation()).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        return connection.getResponseCode();
    }

    private PatientData processMessage(String patientId, String messageText) {
        String[] parts = messageText.split(", ");

        if (parts.length != 5) {
            throw new RuntimeException("Invalid message format");
        }

        patientId = parts[0];
        double heartRate = Double.parseDouble(parts[0]);
        double bloodPressure = Double.parseDouble(parts[1]);
        boolean medicationAdherence = Boolean.parseBoolean(parts[2]);
        String patientLocation = parts[3];

        return new PatientData(heartRate, bloodPressure, medicationAdherence, patientLocation);
    }
}
