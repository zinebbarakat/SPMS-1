package com.spms.dashboard;

import java.sql.Timestamp;

public class SensorRecord {
    private String sensorName;
    private double measurementValue;
    private Timestamp timeStamp;

    public SensorRecord(String sensorName, double measurementValue, Timestamp timeStamp) {
        this.sensorName = sensorName;
        this.measurementValue = measurementValue;
        this.timeStamp = timeStamp;
    }

    public String getSensorName() { return sensorName; }
    public double getMeasurementValue() { return measurementValue; }
    public Timestamp getTimeStamp() { return timeStamp; }
}
