package org.nimesa.assignment.repository;

import org.nimesa.assignment.model.EC2Instance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EC2InstanceRepository extends CrudRepository<EC2Instance, String> {
    @Query("select t.id from EC2Instance t")
    List<String> findAllIds();
}
