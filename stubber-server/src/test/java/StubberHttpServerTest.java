import com.pact.parse.dto.InteractionDTO;
import com.pact.parse.dto.RequestData;
import com.pact.parse.dto.ResponseData;
import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.impl.StubberHttpServer;
import com.pact.stubber.impl.StubberServerBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class StubberHttpServerTest {

    private static StubberHttpServer server = null;

    @BeforeClass
    public static void setUp(){
        server = (StubberHttpServer) StubberServerBuilder.getInstance().setServerConfig(new ServerConfig(9088,"",null)).build();
    }

    @Test
    public void testLoadInteractions(){
        Map<RequestData, ResponseData> data = server.loadPacts("/home/kaushik/github/pactstubber/stubber-server/src/test/resources/pact");
        System.out.println(data);
        Assert.assertNotNull(data);
    }

    @Test
    public void testLoadInteractionsNoAccess(){
        Map<RequestData, ResponseData> data = server.loadPacts("/home/kaushik/github/pactstubber/stubber-server/src/test/resources/test_noaccess");
        System.out.println(data);
        Assert.assertNotNull(data);
    }



}
