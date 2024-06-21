package org.nimesa.assignment.model;

import com.amazonaws.services.ec2.model.Instance;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ec2_instance")
@Entity
public class EC2Instance {
    @Id
    private String instanceId;
    private String imageId;
    private String instanceType;
    private String state;

    public EC2Instance(Instance instance) {
        this.instanceId = instance.getInstanceId();
        this.imageId = instance.getImageId();
        this.instanceType = instance.getInstanceType();
        this.state = instance.getState().getName();
    }
}
