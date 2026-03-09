package com.rensoft.impl;

import com.rensoft.remote.IoTDevice;
import jakarta.ejb.Stateless;
import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
public class IoTDeviceBean implements IoTDevice {
    private String patientId;
    private double heartRate;
    private double bloodPressure;
    private boolean medicationAdherence;
    private String location;

    public IoTDeviceBean(String patientId) {
        this.patientId = patientId;
    }

    public IoTDeviceBean() {
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public double getHeartRate() {
        return heartRate;
    }

    @Override
    public double getBloodPressure() {
        return bloodPressure;
    }

    @Override
    public boolean isMedicationAdherence() {
        return medicationAdherence;
    }

    @Override
    public String getPatientLocation() {
        return location;
    }

    private IoTDevice captureData() {
        this.heartRate = Math.random() * (100 - 60) + 60; // Simulate heart rate
        this.bloodPressure = Math.random() * (140 - 90) + 90; // Simulate blood pressure
        this.medicationAdherence = Math.random() > 0.5; // Simulate medication adherence
        this.location = "Patient's Home"; // Simulate location

        return this;
    }

    @Override
    public void transmitData() {
        try {
            InitialContext context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("java:comp/env/jms/ConnectionFactory");
            Queue queue = (Queue) context.lookup("java:comp/env/jms/Queue");

            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);

            TextMessage message = session.createTextMessage();

            while (true) {
                Thread.sleep(500);
                message.setText(captureData().toString());
                producer.send(message);
            }

//            connection.close();
        } catch (NamingException | JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return heartRate + ", " + bloodPressure + ", " + medicationAdherence + ", " + location;
    }
}
