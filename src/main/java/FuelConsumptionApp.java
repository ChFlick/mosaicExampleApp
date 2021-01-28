import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.interactions.vehicle.VehicleStop;
import org.eclipse.mosaic.lib.objects.v2x.MessageRouting;
import org.eclipse.mosaic.lib.objects.v2x.V2xMessage;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.api.Interaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FuelConsumptionApp extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {
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
        var consumedFuel =
                updatedVehicleData.getVehicleConsumptions().getCurrentConsumptions().getFuel();
        getLog().info("Currently consuming fuel: {}", consumedFuel);

        if (consumedFuel % 10 > 2) {
            this.getOs().stopNow(VehicleStop.VehicleStopMode.STOP, 1000);
            getLog().info("Stopping now");
        }

        MessageRouting server_0 =
                getOs().getCellModule().createMessageRouting().topoCast("server_0");
        getOs().getCellModule().sendV2xMessage(new FuelMessage(server_0, consumedFuel));
    }

    @Override
    public void processEvent(Event event) throws Exception {

    }
}
