import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.impl.StubberHttpServer;
import com.pact.stubber.impl.StubberServerBuilder;

public class Trigger {

    public static void main(String[] args) throws InterruptedException {
        StubberHttpServer server = (StubberHttpServer) StubberServerBuilder.getInstance().setServerConfig(new ServerConfig(9088,"",null)).build();
        server.startServer();
        Thread.sleep(10000000);

    }
}
