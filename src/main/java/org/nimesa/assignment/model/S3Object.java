package org.nimesa.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "s3_object")
@Entity
public class S3Object {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "name")
    private S3Bucket bucket;

    public S3Object(String fileName, S3Bucket bucket) {
        this.fileName = fileName;
        this.bucket = bucket;
    }
}
