package com.calorietracker.model;

import java.time.LocalDateTime;

public class HealthTip {
    private String id;
    private String message;
    private TipSeverity severity;
    private LocalDateTime generatedAt;
    private String category;

    public enum TipSeverity {
        LOW,
        MEDIUM,
        HIGH
    }

    public HealthTip(String id, String message, TipSeverity severity, String category) {
        this.id = id;
        this.message = message;
        this.severity = severity;
        this.category = category;
        this.generatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public TipSeverity getSeverity() {
        return severity;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("%s (Severity: %s)", message, severity);
    }
}