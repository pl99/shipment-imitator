package ru.advantum.shipmentimitator.sourcedb.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.advantum.shipmentimitator.sourcedb.model.SrcShipment;

import java.time.LocalDate;

@Component
public class ShipmentSpecification {
    public Specification<SrcShipment> shipmentByDeliveryDay(final LocalDate from, final LocalDate to) {
        if (null == to) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deliveryDate"), from));
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("deliveryDate"), from, to));
    }
}
