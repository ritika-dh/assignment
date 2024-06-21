package org.nimesa.assignment.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import org.nimesa.assignment.model.EC2Instance;
import org.nimesa.assignment.repository.EC2InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EC2Service {
    @Autowired
    AmazonEC2 ec2Client;

    @Autowired
    EC2InstanceRepository repository;

    public void discoverInstances(){
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        DescribeInstancesResult response = ec2Client.describeInstances(request);

        List<EC2Instance> instances = new ArrayList<>();
        for(Reservation reservation : response.getReservations()) {
            for(Instance instance : reservation.getInstances()) {
                instances.add(new EC2Instance(instance));
            }
        }
        repository.saveAll(instances);
    }

    public List<String> listAllInstanceIds(){
        return repository.findAllIds();
    }
}
