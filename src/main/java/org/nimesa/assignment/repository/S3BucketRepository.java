package org.nimesa.assignment.repository;

import org.nimesa.assignment.model.S3Bucket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface S3BucketRepository extends CrudRepository<S3Bucket, String> {
    @Query("select t.id from S3Bucket t")
    List<String> findAllIds();
}
