import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.impl.StubberHttpServer;
import com.pact.stubber.impl.StubberServerBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Trigger {

    public static void main(String[] args) throws InterruptedException {
        StubberHttpServer server =
                (StubberHttpServer) StubberServerBuilder.getInstance().setServerConfig(new ServerConfig(9088,"/home/kaushik/github/pactstubber/stubber-server/src/test/resources/pact",null)).build();
        server.startServer();
        Thread.sleep(10000000);



    }


}
@Getter
@AllArgsConstructor
class MyClass{
    private List<String> list;
}
