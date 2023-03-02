package com.example.cloud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.Serial;
import java.io.Serializable;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "storage")
public class Storage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @NonNull
    @Size(max = 100)
    private String fileName;
    @NonNull
    private String type;
    @NonNull
    private Long size;
    @NonNull
    @Lob
    private byte[] data;
}
