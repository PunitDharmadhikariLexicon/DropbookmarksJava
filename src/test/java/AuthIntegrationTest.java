import com.udemy.DropBookmarksApplication;
import com.udemy.DropBookmarksConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class AuthIntegrationTest {
    private static final String CONFIG_PATH
            = "config.yml";

    @ClassRule public static final DropwizardAppRule<DropBookmarksConfiguration> RULE
            = new DropwizardAppRule<>(DropBookmarksApplication.class, CONFIG_PATH);

    private static final HttpAuthenticationFeature FEATURE
            = HttpAuthenticationFeature.basic("username", "p@ssw0rd");


    private static final String TARGET =
            "https://localhost:8080";

    private static final String PATH = "/hello/secured";

    private Client client;

    @Before
    public void setUp() {
        client= ClientBuilder.newClient();
    }

    @After
    public void tearDown() {
        client.close();
    }

//    @Test
//    public void testSadPath() {
//        Response response = client.target(TARGET).path(PATH).request().get();
//
//        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    public void testHappyPath() {
//        String expected = "Hello secured world";
//        client.register(FEATURE);
//        String actual = client.target(TARGET).path(PATH).request(MediaType.TEXT_PLAIN).get(String.class);
//
//        assertEquals(expected, actual);
//    }
}