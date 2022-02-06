package banana.duo.Server;

import banana.duo.common.Message;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServerBluetooth extends Server {

    StreamConnectionNotifier server = null;
    byte[] data = new byte[256];
    int length;
    InputStream in = null;

    ServerBluetooth() throws IOException {

    }

    @Override
    public void startServer() throws IOException, InterruptedException {
        LocalDevice local = LocalDevice.getLocalDevice();
        local.setDiscoverable(DiscoveryAgent.GIAC);
        server = (StreamConnectionNotifier) Connector.open(
                "btspp://localhost:314443353731324B3531B0227AE1D02F");
        StreamConnection conn = server.acceptAndOpen();
        System.out.println("accepted");
        in = conn.openInputStream();
        super.startServer();
    }


    @Override
    protected boolean hasConnection() {
        return false;
    }

    @Override
    protected void cleanIn() throws IOException {
        in.reset();
    }

    @Override
    protected String getLineIn() throws IOException {
        length = in.read(data);
        String message = new String(Arrays.copyOfRange(data, 0, length), StandardCharsets.UTF_8);
        return message.split("\n")[0];
    }

    @Override
    protected void sendMessage(Message message) throws IOException {

    }

    @Override
    protected void offServer() {

    }
}
