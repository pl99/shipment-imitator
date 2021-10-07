package ru.advantum.shipmentimitator.components;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentRepository;

import java.time.LocalDate;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
@Slf4j
@Command(name = "sco")
public class ShipmentCounter implements Callable<Integer> {

    @Option(names = "--from", required = true, description = "Начало периода")
    String from;

    @Option(names = "--to", required = true, description = "Окончание периода")
    String to;

    private final SrcShipmentRepository repository;

    private String sourceShipmentCount(String from, String to) {
        LocalDate fromDt = LocalDate.parse(from);
        LocalDate toDt = LocalDate.parse(to);
        Long countShipmentByDeliveryDateBetween = repository.countShipmentByDeliveryDateBetween(fromDt, toDt);
        return String.format("From %s to %s shipments count %d", from, to, countShipmentByDeliveryDateBetween);

    }

    @Override
    public Integer call() throws Exception {
        sourceShipmentCount(this.from, this.to);
        return 0;
    }
}
