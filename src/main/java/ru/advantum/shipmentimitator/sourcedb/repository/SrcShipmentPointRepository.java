package ru.advantum.shipmentimitator.sourcedb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.advantum.shipmentimitator.sourcedb.model.SrcShipmentPoint;

import java.util.List;
import java.util.stream.Stream;

public interface SrcShipmentPointRepository extends JpaRepository<SrcShipmentPoint, Long> {
    Stream<SrcShipmentPoint> findAllByShipmentId(Long shipmentId);
    Stream<SrcShipmentPoint> findAllByShipmentIdIn(List<Long> shipmentIds);
}