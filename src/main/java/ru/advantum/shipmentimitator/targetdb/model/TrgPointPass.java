package ru.advantum.shipmentimitator.targetdb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.Instant;

@Table(name = "point_pass")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class TrgPointPass {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_in_instant", nullable = false)
    private Instant firstInInstant;

    @Column(name = "first_out_instant_after")
    private Instant firstOutInstantAfter;

    @Column(name = "last_in_instant", nullable = false)
    private Instant lastInInstant;

    @Column(name = "point_id")
    private Long pointId;

}