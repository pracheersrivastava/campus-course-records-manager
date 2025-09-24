












package edu.ccrm.domain;

import java.time.LocalDateTime;

public abstract class Person {
    private static long idCounter = 1;

    protected long id;
    protected String fullName;
    protected String email;
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateModified;

    public Person(String fullName, String email) {
        this.id = idCounter++;
        this.fullName = fullName;
        this.email = email;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
    }

    public abstract String getDetails();

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        this.dateModified = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.dateModified = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName + ", Email: " + email;
    }
}
