package com.udemy.resources;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import com.udemy.resources.User;

import javax.ws.rs.core.MediaType;

import static org.junit.jupiter.api.Assertions.*;

public class HelloResourceTest {
    private static final HttpAuthenticationFeature FEATURE = HttpAuthenticationFeature.basic("u", "p");

    private static final Authenticator<BasicCredentials, User> AUTHENTICATOR = new Authenticator<BasicCredentials, User>() {
        @Override
        public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
            return Optional.of(new User());
        }
    };

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule
            .builder()
            .addProvider(
                    AuthFactory.binder(
                            new BasicAuthFactory<>(AUTHENTICATOR, "realm", User.class)
                    )
            )
            .setTestContainerFactory(
                    new GrizzlyWebTestContainerFactory()
            )
            .addResource(new HelloResource())
            .build();

    @BeforeClass
    public static void setUpClass() {
        RULE.getJerseyTest().client().register(FEATURE);
    }

    public HelloResourceTest() {

    }

//    @Test
//    public void testGetGreeting() {
//        String expected = "Hello world";
//        String actual = RULE.getJerseyTest().target("/hello").request(MediaType.TEXT_PLAIN).get(String.class);
//        assertEquals(expected, actual);
////        fail("Not implemented");
//    }
//
//    @Test
//    public void testGetSecuredGreeting() {
//        String expected = "Hello secured world";
//        String actual = RULE.getJerseyTest().target("/hello/secured").request(MediaType.TEXT_PLAIN).get(String.class);
//        assertEquals(expected, actual);
//    }
}