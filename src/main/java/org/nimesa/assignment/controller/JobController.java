package org.nimesa.assignment.controller;

import org.nimesa.assignment.dto.MatchInput;
import org.nimesa.assignment.service.JobService;
import org.nimesa.assignment.service.S3ObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    S3ObjectsService s3ObjectsService;

    @GetMapping("discoverServices")
    public Integer discoverServices(@RequestBody List<String> services){
        return jobService.discoverServices(services);
    }

    @GetMapping("job/{id}")
    public String getJobResult(@PathVariable("id") int id){
        return jobService.getJobResult(id);
    }

    @GetMapping("discoveryResult/{service}")
    public List<String> getDiscoveryResult(@PathVariable("service") String service){
        return jobService.getDiscoveryResult(service);
    }

    @GetMapping("s3Bucket/{bucketName}")
    public Integer getS3BucketObjects(@PathVariable("bucketName") String bucketName){
        return jobService.getS3BucketObjects(bucketName);
    }

    @GetMapping("s3Bucket/{bucketName}/count")
    public Integer getS3BucketObjectCount(@PathVariable("bucketName") String bucketName) {
        return s3ObjectsService.getS3BucketObjectsCount(bucketName);
    }

    @GetMapping("s3Bucket/match")
    public List<String> getS3BucketObjectslike(@RequestBody MatchInput input){
        return s3ObjectsService.getS3BucketObjectslike(input.getBucketName(), input.getPatttern());
    }

}
