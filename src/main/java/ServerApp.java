import org.eclipse.mosaic.fed.application.ambassador.simulation.communication.CamBuilder;
import org.eclipse.mosaic.fed.application.ambassador.simulation.communication.ReceivedAcknowledgement;
import org.eclipse.mosaic.fed.application.ambassador.simulation.communication.ReceivedV2xMessage;
import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.CommunicationApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.OperatingSystem;
import org.eclipse.mosaic.fed.application.app.api.os.TrafficManagementCenterOperatingSystem;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.interactions.communication.V2xMessageTransmission;
import org.eclipse.mosaic.interactions.vehicle.VehicleStop;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ServerApp extends AbstractApplication<OperatingSystem> implements CommunicationApplication {
    @Override
    public void onStartup() {
        getOs().getCellModule().enable();
        getLog().info("Starting the server! {}", getOs().getId());
    }

    @Override
    public void onShutdown() {
        getLog().info("Shutting down");
    }

    @Override
    public void processEvent(Event event) throws Exception {
    }

    @Override
    public void onMessageReceived(ReceivedV2xMessage receivedV2xMessage) {
        getLog().info("Received Message {}", receivedV2xMessage.getMessage());
    }

    @Override
    public void onAcknowledgementReceived(ReceivedAcknowledgement receivedAcknowledgement) {

    }

    @Override
    public void onCamBuilding(CamBuilder camBuilder) {

    }

    @Override
    public void onMessageTransmitted(V2xMessageTransmission v2xMessageTransmission) {

    }
}
