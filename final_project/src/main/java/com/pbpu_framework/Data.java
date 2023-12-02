package com.pbpu_framework;

import java.io.Serializable;

public class Data implements Serializable {
    private String subject;
    private String description;

    public Data(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    // Constructor for parsing from a string
    public Data(String dataString) {
        String[] parts = dataString.split("\\|");
        if (parts.length == 2) {
            this.subject = parts[0].trim();
            this.description = parts[1].trim();
        } else {
            // Handle invalid format or throw an exception
            throw new IllegalArgumentException("Invalid data format: " + dataString);
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s | %s", this.getSubject(), this.getDescription());
    }

    // Method to create an instance from separate subject and description strings
    public static Data createFromStrings(String subject, String description) {
        return new Data(subject, description);
    }

    public String toCsvString() {
        return String.format("%s,%s", this.getSubject(), this.getDescription());
    }
}
