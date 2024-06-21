package org.nimesa.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nimesa.assignment.utils.JobState;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job")
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private JobState state;
}
