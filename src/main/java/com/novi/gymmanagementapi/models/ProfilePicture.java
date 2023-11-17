package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    @Lob
    private byte[] docFile;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFileName() {
        return fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}