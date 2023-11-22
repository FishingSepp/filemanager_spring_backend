package com.example.filemanager.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class FmFile {
    @Id
    @NotBlank
    private String name;

    @NotBlank
    private String path;
    private Long size;
    private LocalDateTime lastConfigured;
    private String fileType;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getLastConfigured() {
        return lastConfigured;
    }

    public void setLastConfigured(LocalDateTime lastConfigured) {
        this.lastConfigured = lastConfigured;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public FmFile(String name, String path, Long size, LocalDateTime lastConfigured, String fileType) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.lastConfigured = lastConfigured;
        this.fileType = fileType;
    }

    public FmFile() {

    }

    public String toString() {
        return "File{" +
                "name=" + name +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", lastConfigured=" + lastConfigured +
                ", fileType=" + fileType +
                '}';
    }
}

