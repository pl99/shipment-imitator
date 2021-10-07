package ru.advantum.shipmentimitator.sourcedb.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.advantum.shipmentimitator.config.ReadOnlyRepository;
import ru.advantum.shipmentimitator.sourcedb.model.SrcPointPass;
import ru.advantum.shipmentimitator.sourcedb.model.SrcShipment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SrcShipmentRepository
        extends ReadOnlyRepository<SrcShipment, Long>, JpaSpecificationExecutor<SrcShipment> {
    Long countShipmentByDeliveryDateBetween(LocalDate from,LocalDate to);

    List<SrcShipment> findAllByDeliveryDate(LocalDate deliveryDate);
}
