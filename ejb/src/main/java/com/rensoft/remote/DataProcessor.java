package com.rensoft.remote;

import com.rensoft.model.PatientData;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface DataProcessor {
    void analyzeData(PatientData patientData);

    void newDevice(String patientId);

    double calculateAverageHeartRate(List<PatientData> patientDataList);

    double calculateAverageBloodPressure(List<PatientData> patientDataList);

    double calculateMedicationAdherenceRate(List<PatientData> patientDataList);

    List<IoTDevice> getAll();
}
