package org.nimesa.assignment.model;

import com.amazonaws.services.s3.model.Bucket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "s3_bucket")
@Entity
public class S3Bucket {
    @Id
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bucket")
    private List<S3Object> objectList;

    private Date creationDate;

    public S3Bucket(String name, Date creationDate){
        this.name = name;
        this.creationDate = creationDate;
    }
}
