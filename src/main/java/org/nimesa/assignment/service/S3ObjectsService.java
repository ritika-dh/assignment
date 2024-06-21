package org.nimesa.assignment.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.nimesa.assignment.model.S3Bucket;
import org.nimesa.assignment.model.S3Object;
import org.nimesa.assignment.repository.S3ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class S3ObjectsService {
    @Autowired
    S3ObjectRepository repository;

    public void saveS3ObjectsForBucket(List<S3ObjectSummary> objects, S3Bucket s3Bucket){
        if(objects == null)
            return;
        List<String> filesPresent = repository.findByBucketName(s3Bucket.getName());
        List<S3Object> newObjects = new ArrayList<>();
        for(S3ObjectSummary objectSummary: objects){
            if(!filesPresent.contains(objectSummary.getKey())){
                newObjects.add(new S3Object(objectSummary.getKey(), s3Bucket));
            }

        }
        if(!newObjects.isEmpty())
            repository.saveAll(newObjects);
    }

    public Integer getS3BucketObjectsCount(String bucketName) {
        return repository.countObjectsInBucket(bucketName);
    }


    public List<String> getS3BucketObjectslike(String bucketName, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        List<String> filesPresent = repository.findByBucketName(bucketName);
        return filesPresent.parallelStream()
                .filter((file) -> pattern.matcher(file).matches())
                .toList();
    }
}
