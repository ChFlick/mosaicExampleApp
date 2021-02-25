import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.lib.objects.v2x.MessageRouting;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmissionOutputApp extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {
    @Override
    public void onStartup() {
        getOs().getCellModule().enable();
        getLog().info("Starting fuel counter");
    }

    @Override
    public void onShutdown() {
        getLog().info("Shutting down");
    }

    @Override
    public void onVehicleUpdated(@Nullable VehicleData previousVehicleData,
                                 @Nonnull VehicleData updatedVehicleData) {
        var emissions =
                updatedVehicleData.getVehicleEmissions().getCurrentEmissions();
        var position = updatedVehicleData.getProjectedPosition();
        getLog().info("Current emission output: {}", emissions);

        MessageRouting server_0 =
                getOs().getCellModule().createMessageRouting().topoCast("server_0");
        getOs().getCellModule().sendV2xMessage(new EmissionMessage(server_0, emissions, position));
    }

    @Override
    public void processEvent(Event event) throws Exception {

    }
}
