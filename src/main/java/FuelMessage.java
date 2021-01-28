import org.eclipse.mosaic.lib.objects.v2x.EncodedPayload;
import org.eclipse.mosaic.lib.objects.v2x.MessageRouting;
import org.eclipse.mosaic.lib.objects.v2x.V2xMessage;

import javax.annotation.Nonnull;

public class FuelMessage extends V2xMessage {
    private final double fuelValue;

    public FuelMessage(MessageRouting routing, double fuelValue) {
        super(routing);
        this.fuelValue = fuelValue;
    }

    public double getFuelValue() {
        return fuelValue;
    }

    @Nonnull
    @Override
    public EncodedPayload getPayLoad() {
        return new EncodedPayload(Double.BYTES);
    }

    @Override
    public String toString() {
        return "FuelMessage{" +
                "fuelValue=" + fuelValue +
                '}';
    }
}
