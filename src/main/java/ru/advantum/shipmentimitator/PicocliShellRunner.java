package ru.advantum.shipmentimitator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ru.advantum.shipmentimitator.components.ShipmentCounter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PicocliShellRunner implements CommandLineRunner, ExitCodeGenerator {

    CommandLine.IFactory factory;
    ShipmentCounter shipmentCounter;
    int exitCode;

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(shipmentCounter, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
