package com.liis.eventio.event;

import com.liis.eventio.participant.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String location;
    @Column(length = 1000, name="extra_info")
    private String extraInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "event_participant", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns =
    @JoinColumn(name = "participant_id"))
    private List<Participant> participants = new ArrayList<>();
}
