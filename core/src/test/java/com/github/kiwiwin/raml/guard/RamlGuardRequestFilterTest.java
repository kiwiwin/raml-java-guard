package com.github.kiwiwin.raml.guard;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

public class RamlGuardRequestFilterTest extends JerseyTest {

    @Path("persons")
    public static class TestResource {
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        public String create(String request) {
            return "created";
        }
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(TestResource.class).register(RamlGuardRequestFilter.class);
    }

    @Test
    public void test() throws Exception {
        String test = target("/persons").request()
                .post(person("name", "15", "john@gmail.com"), String.class);
        System.out.println(test);
    }

    private Entity<Person> person(String name, String age, String email) {
        return Entity.entity(new Person(name, age, email), MediaType.APPLICATION_JSON);
    }

    class Person {
        private String age;
        private String name;
        private String email;

        Person(String name, String age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
