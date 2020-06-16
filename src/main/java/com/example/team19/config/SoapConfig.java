package com.example.team19.config;

import com.example.team19.soap.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.example.team19.wsdl");
        return marshaller;
    }

    @Bean
    public TestClient testClient(Jaxb2Marshaller marshaller) {
        TestClient client = new TestClient();
        client.setDefaultUri("https://localhost:8083/rent-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public AdClient adClient(Jaxb2Marshaller marshaller) {
        AdClient client = new AdClient();
        client.setDefaultUri("https://localhost:8083/ad-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public LoginClient loginClient(Jaxb2Marshaller marshaller) {
        LoginClient client = new LoginClient();
        client.setDefaultUri("https://localhost:8083/user-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public CarClient carClient(Jaxb2Marshaller marshaller) {
        CarClient client = new CarClient();
        client.setDefaultUri("https://localhost:8083/car-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public RentClient rentClient(Jaxb2Marshaller marshaller) {
        RentClient client = new RentClient();
        client.setDefaultUri("https://localhost:8083/rent-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
