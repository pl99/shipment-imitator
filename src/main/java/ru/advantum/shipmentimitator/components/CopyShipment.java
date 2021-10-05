package ru.advantum.shipmentimitator.components;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.advantum.shipmentimitator.sourcedb.model.SrcShipment;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentPointRepository;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentRepository;
import ru.advantum.shipmentimitator.targetdb.model.TrgShipment;
import ru.advantum.shipmentimitator.targetdb.model.ShipmentPoint;
import ru.advantum.shipmentimitator.targetdb.repository.TrgShipmentPointRepository;
import ru.advantum.shipmentimitator.targetdb.repository.TrgShipmentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CopyShipment {

    SrcShipmentRepository sourceRepository;
    TrgShipmentRepository targetRepository;
    SrcShipmentPointRepository srcShipmentPointRepository;
    TrgShipmentPointRepository trgShipmentPointRepository;

    @ShellMethod(key = {"shc"}, value = "Подсчитать общее количество рейсов")
    public String counts() {
        long sourceCount = sourceRepository.count();
        long targetCount = targetRepository.count();
        return "source cnt = " + sourceCount + " target cnt = " + targetCount;
    }

    @Transactional
    @ShellMethod(key = {"sco"}, value = "скопировать рейс по id")
    public String copyOnce(@ShellOption({"shipmentId, id"}) Long shipmentID) {
        SrcShipment shipment = sourceRepository.findById(shipmentID)
                .orElseThrow(EntityNotFoundException::new);
        TrgShipment trg = targetRepository.findById(shipmentID)
                .orElseGet(TrgShipment::new);

        copyShipment(shipment, trg);
        List<ShipmentPoint> shipmentPoints = createShipmentPoints(shipment);
        targetRepository.saveAndFlush(trg);
        trgShipmentPointRepository.saveAllAndFlush(shipmentPoints);
        return "copied";
    }

    @Transactional
    @ShellMethod(key = {"sco-date"}, value = "скопировать рейс по дате доставки")
    public String copyForDeliveryDate(@ShellOption({"deliveryDate"}) String deliveryDate) {
        LocalDate deliveryDateDt = LocalDate.parse(deliveryDate);

        List<SrcShipment> shipments = sourceRepository.findAllByDeliveryDate(deliveryDateDt);
        List<TrgShipment> targets = shipments.stream().map(this::copyShipment).collect(Collectors.toList());
        List<TrgShipment> trgShipments = targetRepository.saveAllAndFlush(targets);
        List<ShipmentPoint> targetPoints = shipments.stream().flatMap(s -> createShipmentPoints(s).stream()).collect(Collectors.toList());
        trgShipmentPointRepository.saveAllAndFlush(targetPoints);
        return "copied from " + shipments.size() + " to " + trgShipments.size();
    }

    private List<ShipmentPoint> createShipmentPoints(SrcShipment shipment) {
        @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
        @RequiredArgsConstructor
        @Getter
        class TwoPoints {
            ru.advantum.shipmentimitator.sourcedb.model.ShipmentPoint srcPoint;
            ru.advantum.shipmentimitator.targetdb.model.ShipmentPoint trgPoint;
        }
        return srcShipmentPointRepository.findAllByShipmentId(shipment.getId())
                .map(it -> new TwoPoints(it, new ShipmentPoint()))
                .map(it -> {
                    BeanUtils.copyProperties(it.getSrcPoint(), it.getTrgPoint());
                    it.getTrgPoint().setId(it.getSrcPoint().getId());
                    return it.getTrgPoint();
                })
                .collect(Collectors.toList());
    }

    private void copyShipment(SrcShipment shipment, TrgShipment target) {
        BeanUtils.copyProperties(shipment, target);
        target.setId(shipment.getId());
    }

    private TrgShipment copyShipment(SrcShipment shipment){
        TrgShipment target = new TrgShipment();
        BeanUtils.copyProperties(shipment, target);
        target.setId(shipment.getId());
        return target;
    }
}



