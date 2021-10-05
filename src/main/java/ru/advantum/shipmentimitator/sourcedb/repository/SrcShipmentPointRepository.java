package ru.advantum.shipmentimitator.sourcedb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.advantum.shipmentimitator.sourcedb.model.ShipmentPoint;

import java.util.List;
import java.util.stream.Stream;

public interface SrcShipmentPointRepository extends JpaRepository<ShipmentPoint, Long> {
    Stream<ShipmentPoint> findAllByShipmentId(Long shipmentId);
}