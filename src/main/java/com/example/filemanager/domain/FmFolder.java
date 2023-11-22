package com.example.filemanager.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;

@Entity
@Table(name = "folder")
public class FmFolder {

    @Id
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Name is required")
    private String path;

    public FmFolder(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public FmFolder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Job{" +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}