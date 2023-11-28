package com.liis.eventio.participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@PrimaryKeyJoinColumn(name="participant_id")
@Table(name = "person")
public class Person extends Participant {
    @Column(nullable = false, length = 45, name="first_name")
    private String firstName;
    @Column(nullable = false, length = 45, name="last_name")
    private String lastName;
    @Column(nullable = false, length = 11, name="personal_code")
    private String personalCode;
    @Column(nullable = false, name="payment_method")
    private String paymentMethod;
    @Column(length = 1500, name="extra_info")
    private String extraInfo;
}
