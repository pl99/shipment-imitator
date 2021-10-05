package ru.advantum.shipmentimitator.targetdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.advantum.shipmentimitator.targetdb.model.ShipmentPoint;

public interface TrgShipmentPointRepository extends JpaRepository<ShipmentPoint, Long> {
}