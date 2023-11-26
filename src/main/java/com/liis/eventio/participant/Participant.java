package com.liis.eventio.participant;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="participant_id", updatable = false, nullable = false)
    protected Integer participantId;
}
