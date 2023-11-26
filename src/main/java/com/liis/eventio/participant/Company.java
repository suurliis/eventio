package com.liis.eventio.participant;

import com.liis.eventio.participant.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "company")
@PrimaryKeyJoinColumn(name="participant_id")
public class Company extends Participant {
    @Column(nullable = false, length = 45)
    private String name;
    @Column(nullable = false, length = 20, name="registry_code")
    private String registryCode;
    @Column(nullable = false, name="number_of_participants")
    private int numberOfParticipants;
    @Column(nullable = false, name="payment_method")
    private String paymentMethod;
    @Column(nullable = false, name="extra_info", length = 5000)
    private String extraInfo;
}
