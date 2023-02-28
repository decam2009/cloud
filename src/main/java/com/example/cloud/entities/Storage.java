package com.example.cloud.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "storage")
public class Storage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @NonNull
    private String fileName;
    @NonNull
    private String type;
    @NonNull
    private String fileHash;
    @Lob
    private byte[] data;
}
