package ru.advantum.shipmentimitator.targetdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.advantum.shipmentimitator.targetdb.model.TrgShipmentPoint;

public interface TrgShipmentPointRepository extends JpaRepository<TrgShipmentPoint, Long> {
}