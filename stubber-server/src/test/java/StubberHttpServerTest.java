import com.pact.parse.dto.InteractionDTO;
import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.impl.StubberHttpServer;
import com.pact.stubber.impl.StubberServerBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class StubberHttpServerTest {

    private static StubberHttpServer server = null;

    @BeforeClass
    public static void setUp(){
        server = (StubberHttpServer) StubberServerBuilder.getInstance().setServerConfig(new ServerConfig(9088,"",null)).build();
    }

    @Test
    public void testLoadInteractions(){
        List<InteractionDTO> data = server.loadPacts("/home/kaushik/github/pactstubber/stubber-server/src/test/resources/pact");
        System.out.println(data);
    }

}
