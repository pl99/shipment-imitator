package ru.advantum.shipmentimitator.targetdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.advantum.shipmentimitator.targetdb.model.TrgPointPass;

public interface TrgPointPassRepository extends JpaRepository<TrgPointPass, Long> {
}