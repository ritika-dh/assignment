package org.nimesa.assignment.repository;

import org.nimesa.assignment.model.S3Object;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface S3ObjectRepository extends CrudRepository<S3Object, Integer> {
    @Query("SELECT o.fileName from S3Object as o where o.bucket.name = :bucketName")
    public List<String> findByBucketName(String bucketName);

    @Query("SELECT count(*) from S3Object as o where o.bucket.name = :bucketName")
    public Integer countObjectsInBucket(String bucketName);

}
