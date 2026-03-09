package com.rensoft.model;

public class PatientData {
    private double heartRate;
    private double bloodPressure;
    private boolean medicationAdherence;
    private String location;

    public PatientData() {
    }

    public PatientData(double heartRate, double bloodPressure, boolean medicationAdherence, String location) {
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.medicationAdherence = medicationAdherence;
        this.location = location;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public double getBloodPressure() {
        return bloodPressure;
    }

    public boolean isMedicationAdherence() {
        return medicationAdherence;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return heartRate + ", " + bloodPressure + ", " + medicationAdherence + ", " + location;
    }
}