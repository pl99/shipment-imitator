package ru.advantum.shipmentimitator.sourcedb.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@Table(name = "shipment")
public class SrcShipment extends AbstractPersistable<Long> {
    @Column(name = "carrier_id")
    Integer carrierId;
    String comment;
    @Column(name = "cost_in_rubles")
    Double costInRubles;
    @Column(name = "delivery_date")
    LocalDate deliveryDate;

    @Column(name = "created_instant")
    Instant createdInstant;

    @Column(name = "date_receipt_accounting_docs")
    LocalDate dateReceiptAccountingDocs;

    @Column(name = "driver_id")
    Integer driverId;

    @Column(name = "fact_finish_instant")
    Instant factFinishInstant;

    @Column(name = "fact_start_instant")
    Instant factStartInstant;

    @Column(name = "initiator_id")
    Integer initiatorId;

    @Column(name = "is_waybills_delivered", nullable = false)
    Boolean isWaybillsDelivered = false;

    @Column(name = "last_modified_instant")
    Instant lastModifiedInstant;

    @Column(name = "linked_route_id")
    Long linkedRouteId;

    @Column(name = "max_temp_in_celsius")
    Double maxTempInCelsius;

    @Column(name = "min_temp_in_celsius")
    Double minTempInCelsius;

    @Column(name = "number_in_month", nullable = false)
    Integer numberInMonth;

    @Column(name = "passing_by_telematics", nullable = false)
    Boolean passingByTelematics = false;

    @Column(name = "planned_finish_instant", nullable = false)
    Instant plannedFinishInstant;

    @Column(name = "planned_start_instant", nullable = false)
    Instant plannedStartInstant;

    @Column(name = "route_id")
    Long routeId;

    @Column(name = "route_sheet_date")
    LocalDate routeSheetDate;

    @Column(name = "route_sheet_receipt_date")
    LocalDate routeSheetReceiptDate;


    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "tariff_id", nullable = false)
    Integer tariffId;

    @Column(name = "vehicle_id")
    Integer vehicleId;


    @Column(name = "wms_status")
    String wmsStatus;

    @Column(name = "wms_status_update_instant")
    Instant wmsStatusUpdateInstant;

    @Column(name = "wms_vehicle_registration_instant")
    Instant wmsVehicleRegistrationInstant;


    @Column(name = "cost_change_reason")
    String costChangeReason;

    @Column(name = "updated_by_user_id")
    Integer updatedByUserId;

    @Column(name = "fact_distance_in_km")
    Double factDistanceInKm;

    @Column(name = "planned_distance_in_km")
    Integer plannedDistanceInKm;

    @Column(name = "planned_cost_in_rubles", nullable = false, precision = 19, scale = 2)
    BigDecimal plannedCostInRubles;

    @Column(name = "next_shipment_id")
    Long nextShipmentId;

    @Column(name = "previous_shipment_id")
    Long previousShipmentId;


    @Column(name = "cancellation_initiator")
    String cancellationInitiator;

    @Column(name = "cancellation_instant")
    Instant cancellationInstant;

    @Column(name = "cancellation_by_order_deleting")
    Boolean cancellationByOrderDeleting;

    @Column(name = "route_cancellation_reason_id")
    Integer routeCancellationReasonId;

    @Column(name = "paid", nullable = false)
    Boolean paid = false;

    @Column(name = "tolerance_in_degrees_celsius")
    Double toleranceInDegreesCelsius;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SrcShipment shipment = (SrcShipment) o;
        return Objects.equals(getId(), shipment.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
