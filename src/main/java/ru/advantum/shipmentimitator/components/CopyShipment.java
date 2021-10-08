package ru.advantum.shipmentimitator.components;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.advantum.shipmentimitator.sourcedb.model.SrcShipment;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcPointPassRepository;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentPointRepository;
import ru.advantum.shipmentimitator.sourcedb.repository.SrcShipmentRepository;
import ru.advantum.shipmentimitator.sourcedb.repository.specification.ShipmentSpecification;
import ru.advantum.shipmentimitator.targetdb.model.TrgPointPass;
import ru.advantum.shipmentimitator.targetdb.model.TrgShipment;
import ru.advantum.shipmentimitator.targetdb.model.TrgShipmentPoint;
import ru.advantum.shipmentimitator.targetdb.repository.TrgPointPassRepository;
import ru.advantum.shipmentimitator.targetdb.repository.TrgShipmentPointRepository;
import ru.advantum.shipmentimitator.targetdb.repository.TrgShipmentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@ShellComponent
public class CopyShipment {

    SrcShipmentRepository sourceRepository;
    TrgShipmentRepository targetRepository;
    SrcShipmentPointRepository srcShipmentPointRepository;
    TrgShipmentPointRepository trgShipmentPointRepository;
    SrcPointPassRepository srcPointPassRepository;
    TrgPointPassRepository trgPointPassRepository;
    ShipmentSpecification shipmentSpecification;

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
        List<TrgShipmentPoint> trgShipmentPoints = createTargetShipmentPoints(Collections.singletonList(shipment.getId()));
        List<TrgPointPass> trgPointPasses = createTargetPointPasss(Collections.singletonList(shipment.getId()));

        targetRepository.saveAndFlush(trg);
        trgShipmentPointRepository.saveAllAndFlush(trgShipmentPoints);
        trgPointPassRepository.saveAllAndFlush(trgPointPasses);
        return "copied";
    }

    @Transactional
    @ShellMethod(key = {"sco-date"}, value = "скопировать рейсы по дате доставки")
    public String copyForDeliveryDate(@ShellOption({"from"}) String from, @ShellOption(value = {"to"}) String to) {
        LocalDate fromDt = LocalDate.parse(from);
        LocalDate toDt = ShellOption.NONE.equals(to) ? fromDt : LocalDate.parse(to);

        List<SrcShipment> shipments = sourceRepository.findAll(shipmentSpecification.shipmentByDeliveryDay(fromDt, toDt));
        List<TrgShipment> targets = shipments.stream().map(this::copyShipment).collect(Collectors.toList());
        List<Long> shipmentIds = shipments.stream().map(SrcShipment::getId).collect(Collectors.toList());
        List<TrgShipmentPoint> targetPoints = createTargetShipmentPoints(shipmentIds);
        List<TrgPointPass> targetPointPasss = createTargetPointPasss(shipmentIds);


        List<TrgShipment> trgShipments = targetRepository.saveAllAndFlush(targets);
        trgShipmentPointRepository.saveAllAndFlush(targetPoints);
        trgPointPassRepository.saveAllAndFlush(targetPointPasss);
        return "copied from " + shipments.size() + " to " + trgShipments.size();
    }

    private List<TrgShipmentPoint> createTargetShipmentPoints(List<Long> shipmentIds) {
        return srcShipmentPointRepository.findAllByShipmentIdIn(shipmentIds)
                .map(it -> {
                    TrgShipmentPoint trgShipmentPoint = new TrgShipmentPoint();
                    BeanUtils.copyProperties(it, trgShipmentPoint);
                    trgShipmentPoint.setId(it.getId());
                    return trgShipmentPoint;
                })
                .collect(Collectors.toList());
    }

    private List<TrgPointPass> createTargetPointPasss(List<Long> shipmentIds) {
        return srcPointPassRepository.findAllByShipmentIds(shipmentIds)
                .map(src -> {
                    TrgPointPass trgPointPass = new TrgPointPass();
                    BeanUtils.copyProperties(src, trgPointPass);
                    trgPointPass.setId(src.getId());
                    return trgPointPass;
                })
                .collect(Collectors.toList());
    }

    private void copyShipment(SrcShipment shipment, TrgShipment target) {
        BeanUtils.copyProperties(shipment, target);
        target.setId(shipment.getId());
    }

    private TrgShipment copyShipment(SrcShipment shipment) {
        TrgShipment target = new TrgShipment();
        BeanUtils.copyProperties(shipment, target);
        target.setId(shipment.getId());
        return target;
    }
}



