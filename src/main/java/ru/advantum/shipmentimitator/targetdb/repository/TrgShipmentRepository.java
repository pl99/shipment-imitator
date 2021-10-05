package ru.advantum.shipmentimitator.targetdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.advantum.shipmentimitator.targetdb.model.TrgShipment;

@Repository()
public interface TrgShipmentRepository extends JpaRepository<TrgShipment, Long> {
}
