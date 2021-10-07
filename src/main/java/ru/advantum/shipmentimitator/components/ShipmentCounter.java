package ru.advantum.shipmentimitator.components;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentRepository;

import java.time.LocalDate;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipmentCounter {

    private final SrcShipmentRepository repository;

    @ShellMethod
    private String sourceShipmentCount(@ShellOption( value = "--from", help = "Начало периода") String from,
                                       @ShellOption( value = "--to", help = "Окончание периода")String to) {
        LocalDate fromDt = LocalDate.parse(from);
        LocalDate toDt = LocalDate.parse(to);
        Long countShipmentByDeliveryDateBetween = repository.countShipmentByDeliveryDateBetween(fromDt, toDt);
        return String.format("From %s to %s shipments count %d", from, to, countShipmentByDeliveryDateBetween);

    }

}
