package ru.advantum.shipmentimitator.components;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ShellComponent
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ShipmentCounter {
    SrcShipmentRepository repository;

//    @ShellMethod(value = "src-sh-cnt")
    public String sourceShipmentCount(String from, String to) {
        LocalDate fromDt = LocalDate.parse(from);
        LocalDate toDt = LocalDate.parse(to);
        Long countShipmentByDeliveryDateBetween = repository.countShipmentByDeliveryDateBetween(fromDt, toDt);
        return String.format("From %s to %s shipments count %d", from, to, countShipmentByDeliveryDateBetween);

    }
}
