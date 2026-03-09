package com.rensoft.remote;

import jakarta.ejb.Remote;

@Remote
public interface IoTDevice {

    String getPatientId();

    double getHeartRate();

    double getBloodPressure();

    boolean isMedicationAdherence();

    String getPatientLocation();

    void transmitData();
}
