package org.nimesa.assignment.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.nimesa.assignment.model.S3Bucket;
import org.nimesa.assignment.repository.S3BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    @Autowired
    AmazonS3 s3Client;

    @Autowired
    S3BucketRepository repository;

    public void discoverS3Buckets(){
        List<Bucket> buckets = s3Client.listBuckets();
        List<S3Bucket> s3BucketList = new ArrayList<>();
        for (Bucket b : buckets) {
            s3BucketList.add(new S3Bucket(b.getName(), b.getCreationDate()));
        }
        repository.saveAll(s3BucketList);
    }

    public List<String> listAllBucketNames(){
        return repository.findAllIds();
    }

    public S3Bucket findBucketByName(String bucketName) {
       return repository.findById(bucketName).orElse(null);
    }

    public List<S3ObjectSummary> downloadObjectsInABucket(String bucketName){
        ListObjectsV2Result result = s3Client.listObjectsV2(bucketName);
        System.out.println("size " + result.getObjectSummaries().size());
        return result.getObjectSummaries();
    }

}
