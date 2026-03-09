package com.rensoft.impl;

import com.rensoft.model.PatientData;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Startup
public class PatientDataCache {
    private final Map<String, PatientData> cache = new ConcurrentHashMap<>();

    public void addPatientData(String patientId, PatientData data) {
        cache.put(patientId, data);
    }

    public String getPatientData(String patientId) {
        return cache.get(patientId).toString();
    }
}
