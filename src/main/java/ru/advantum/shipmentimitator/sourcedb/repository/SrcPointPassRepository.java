package ru.advantum.shipmentimitator.sourcedb.repository;

import org.springframework.data.jpa.repository.Query;
import ru.advantum.shipmentimitator.config.ReadOnlyRepository;
import ru.advantum.shipmentimitator.sourcedb.model.SrcPointPass;

import java.util.List;
import java.util.stream.Stream;

public interface SrcPointPassRepository extends ReadOnlyRepository<SrcPointPass, Long>{
    @Query("select pp from SrcPointPass pp " +
            "join SrcShipmentPoint sp on sp.id=pp.pointId " +
            "where sp.shipmentId in ?1")
    Stream<SrcPointPass> findAllByShipmentIds(List<Long> shipmentIds);

}