import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.lib.objects.v2x.MessageRouting;
import org.eclipse.mosaic.lib.objects.vehicle.Emissions;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmissionOutputApp extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {
    private final List<Emissions> producedEmissions = new ArrayList<>();

    @Override
    public void onStartup() {
        getOs().getCellModule().enable();
        getLog().info("Starting emission counter");
    }

    @Override
    public void onShutdown() {
        getLog().info("Shutting down");
        getLog().info("Sent Messages: {}", producedEmissions.size());
        getLog().info("Average emission values:");
        getLog().info("CO: {} mg/s", producedEmissions.stream().map(Emissions::getCo)
                                                      .collect(Collectors.averagingDouble(it -> it)));
        getLog().info("CO2: {} mg/s", producedEmissions.stream().map(Emissions::getCo2)
                                                       .collect(Collectors.averagingDouble(it -> it)));
        getLog().info("PMx: {} mg/s", producedEmissions.stream().map(Emissions::getPmx)
                                                       .collect(Collectors.averagingDouble(it -> it)));
        getLog().info("NMx: {} mg/s", producedEmissions.stream().map(Emissions::getNox)
                                                       .collect(Collectors.averagingDouble(it -> it)));
        getLog().info("HC: {} mg/s", producedEmissions.stream().map(Emissions::getHc)
                                                      .collect(Collectors.averagingDouble(it -> it)));
    }

    @Override
    public void onVehicleUpdated(@Nullable VehicleData previousVehicleData,
                                 @Nonnull VehicleData updatedVehicleData) {
        var emissions =
                updatedVehicleData.getVehicleEmissions().getCurrentEmissions();
        var position = updatedVehicleData.getProjectedPosition();

        if (!(emissions.getCo() == 0.0 &&
                emissions.getCo2() == 0.0 &&
                emissions.getHc() == 0.0 &&
                emissions.getNox() == 0.0 &&
                emissions.getPmx() == 0.0)) {
            getLog().info("Current emission output: {}", emissions);
            producedEmissions.add(emissions);
            MessageRouting server_0 = getOs().getCellModule().createMessageRouting().topoCast("server_0");
            getOs().getCellModule().sendV2xMessage(new EmissionMessage(server_0, emissions, position));
        }
    }

    @Override
    public void processEvent(Event event) throws Exception {

    }
}
