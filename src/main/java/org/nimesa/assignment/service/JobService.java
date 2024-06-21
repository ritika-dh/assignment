package org.nimesa.assignment.service;

import org.nimesa.assignment.model.Job;
import org.nimesa.assignment.model.S3Bucket;
import org.nimesa.assignment.repository.JobRepository;
import org.nimesa.assignment.utils.JobState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    JobRepository repository;

    @Autowired
    EC2Service ec2Service;

    @Autowired
    S3Service s3Service;

    @Autowired
    S3ObjectsService s3ObjectsService;

    private static final ExecutorService es;

    static {
        es = Executors.newFixedThreadPool(10);
    }

    public int discoverServices(List<String> services){
        int jobId = createJob();
        List<CompletableFuture<Void>> futures = services.stream().map(service -> discoverServiceAsync(service))
                .collect(Collectors.toList());

        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((res, ex) -> updateJobState(jobId, ex == null));
        return jobId;
    }

    private int createJob(){
        Job job = new Job();
        job.setState(JobState.IN_PROGRESS);
        return repository.save(job).getId();
    }

    private void updateJobState(int jobId, boolean success){
        Job job = repository.findById(jobId).orElseThrow();
        job.setState(success ? JobState.SUCCESS : JobState.FAILED);
        repository.save(job);
    }

    private CompletableFuture<Void> discoverServiceAsync(String service) {
        CompletableFuture<Void> result = null;
        if("EC2".equals(service)){
            result = discoverEC2Async();
        } else if ("S3".equals(service)) {
            result = discoverS3Async();
        }
        return result;
    }

    private CompletableFuture<Void> discoverS3Async() {
        return CompletableFuture.runAsync(() -> {
            s3Service.discoverS3Buckets();
        }, es);
    }

    private CompletableFuture<Void> discoverEC2Async() {
        return CompletableFuture.runAsync(() -> {
                    ec2Service.discoverInstances();
                }, es);
    }

    public String getJobResult(int jobid){
       Optional<Job> job = repository.findById(jobid);
       return job.isPresent() ? String.valueOf(job.get().getState()) : "job not found";
    }

    public List<String> getDiscoveryResult(String service){
        if("EC2".equals(service)){
            return ec2Service.listAllInstanceIds();
        } else if ("S3".equals(service)) {
            return s3Service.listAllBucketNames();
        }
        return new ArrayList<>();
    }

    public int getS3BucketObjects(String bucketName){
        int jobId = createJob();

        CompletableFuture.supplyAsync(() -> s3Service.downloadObjectsInABucket(bucketName), es)
                        .exceptionally((ex) -> {updateJobState(jobId, false); return null;})
                        .thenAccept((result) -> {
                            S3Bucket s3Bucket = s3Service.findBucketByName(bucketName);
                            s3ObjectsService.saveS3ObjectsForBucket(result, s3Bucket);})
                        .whenComplete((res, ex) -> updateJobState(jobId, ex == null));
        return jobId;
    }

}
