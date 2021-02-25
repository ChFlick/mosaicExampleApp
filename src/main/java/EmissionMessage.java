import org.eclipse.mosaic.lib.geo.CartesianPoint;
import org.eclipse.mosaic.lib.objects.v2x.EncodedPayload;
import org.eclipse.mosaic.lib.objects.v2x.MessageRouting;
import org.eclipse.mosaic.lib.objects.v2x.V2xMessage;
import org.eclipse.mosaic.lib.objects.vehicle.Emissions;

import javax.annotation.Nonnull;

public class EmissionMessage extends V2xMessage {
    private final Emissions emissions;
    private final CartesianPoint position;

    public EmissionMessage(MessageRouting routing, Emissions emissions, CartesianPoint position) {
        super(routing);
        this.emissions = emissions;
        this.position = position;
    }

    public Emissions getEmissions() {
        return emissions;
    }

    public CartesianPoint getPosition() {
        return position;
    }

    @Nonnull
    @Override
    public EncodedPayload getPayLoad() {
        return new EncodedPayload(Double.BYTES);
    }

    @Override
    public String toString() {
        return "EmissionMessage{" +
                "emissions=" + emissions +
                ", position=" + position +
                '}';
    }
}
