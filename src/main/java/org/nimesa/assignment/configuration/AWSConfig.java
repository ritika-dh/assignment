package org.nimesa.assignment.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
   @Value("${region}")
    private String region;

   public AWSCredentials awsCredentials(){
       return new BasicAWSCredentials(accessKey, secretKey);
   }

   @Bean
   public AmazonS3 s3Client(){
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
               .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
               .withRegion(region)
               .build();
       return s3Client;
   }

   @Bean
   public AmazonEC2 amazonEC2(){
       AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
               .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
               .withRegion(region)
               .build();
       return ec2Client;
   }

}
