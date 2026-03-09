package com.rensoft.impl;

import com.rensoft.model.PatientData;
import com.rensoft.remote.DataProcessor;
import com.rensoft.remote.IoTDevice;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class DataProcessorBean implements DataProcessor {
    List<IoTDevice> devices = new ArrayList<>();

    @Override
    public void analyzeData(PatientData data) {
        // Analysis logic
        System.out.println("Analyzing data: " + data);
    }

    @Override
    public void newDevice(String patientId) {
        devices.add(new IoTDeviceBean(patientId));
    }

    @Override
    public double calculateAverageHeartRate(List<PatientData> patientDataList) {
        return patientDataList.stream().mapToDouble(PatientData::getHeartRate).average().orElse(0);
    }

    @Override
    public double calculateAverageBloodPressure(List<PatientData> patientDataList) {
        return patientDataList.stream().mapToDouble(PatientData::getBloodPressure).average().orElse(0);
    }

    @Override
    public double calculateMedicationAdherenceRate(List<PatientData> patientDataList) {
        long adherenceCount = patientDataList.stream().filter(PatientData::isMedicationAdherence).count();
        return (double) adherenceCount / patientDataList.size();
    }

    public List<IoTDevice> getAll() {
        return devices;
    }
}
