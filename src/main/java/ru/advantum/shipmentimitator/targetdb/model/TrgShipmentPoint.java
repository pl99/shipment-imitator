package ru.advantum.shipmentimitator.targetdb.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Table(name = "shipment_point")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class TrgShipmentPoint {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "delayed", nullable = false)
    private Boolean delayed = false;

    @Column(name = "delivery_acceptance_finish_instant")
    private Instant deliveryAcceptanceFinishInstant;

    @Column(name = "delivery_acceptance_start_instant")
    private Instant deliveryAcceptanceStartInstant;

    @Column(name = "dispatcher_finish_instant")
    private Instant dispatcherFinishInstant;

    @Column(name = "dispatcher_start_instant")
    private Instant dispatcherStartInstant;

    @Column(name = "early", nullable = false)
    private Boolean early = false;

    @Column(name = "fact_finish_instant")
    private Instant factFinishInstant;

    @Column(name = "fact_start_instant")
    private Instant factStartInstant;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "lon", nullable = false)
    private Double lon;

    @Column(name = "max_temp_in_celsius")
    private Double maxTempInCelsius;

    @Column(name = "min_temp_in_celsius")
    private Double minTempInCelsius;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "parking_forbidden", nullable = false)
    private Boolean parkingForbidden = false;

    @Column(name = "planned_finish_instant", nullable = false)
    private Instant plannedFinishInstant;

    @Column(name = "planned_start_instant", nullable = false)
    private Instant plannedStartInstant;

    @Column(name = "predictable_late", nullable = false)
    private Boolean predictableLate = false;

    @Column(name = "retail_point_id", nullable = false)
    private Integer retailPointId;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(name = "unloading_by_driver", nullable = false)
    private Boolean unloadingByDriver = false;

    @Column(name = "shipment_id", nullable = false)
    private Long shipmentId;

    @Column(name = "required_arrival_time_before_in_minutes")
    private Integer requiredArrivalTimeBeforeInMinutes;

    @Column(name = "registration_duration_in_minutes")
    private Integer registrationDurationInMinutes;

    @Column(name = "fact_amount_of_places", nullable = false)
    private Double factAmountOfPlaces;

    @Column(name = "min_passing_duration_in_minutes")
    private Integer minPassingDurationInMinutes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TrgShipmentPoint that = (TrgShipmentPoint) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}