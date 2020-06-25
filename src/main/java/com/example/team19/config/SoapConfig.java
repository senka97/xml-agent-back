package com.example.team19.config;

import com.example.team19.soap.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

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
    public TestClient testClient(Jaxb2Marshaller marshaller) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        TestClient client = new TestClient();
        client.setDefaultUri("https://localhost:8083/rent-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(messageSender());
        return client;
    }

    @Bean
    public AdClient adClient(Jaxb2Marshaller marshaller) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        AdClient client = new AdClient();
        client.setDefaultUri("https://localhost:8083/ad-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(messageSender());
        return client;
    }

    @Bean
    public LoginClient loginClient(Jaxb2Marshaller marshaller) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        LoginClient client = new LoginClient();
        client.setDefaultUri("https://localhost:8083/user-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(messageSender());
        return client;
    }

    @Bean
    public CarClient carClient(Jaxb2Marshaller marshaller) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        CarClient client = new CarClient();
        client.setDefaultUri("https://localhost:8083/car-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(messageSender());
        return client;
    }

    @Bean
    public RentClient rentClient(Jaxb2Marshaller marshaller) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        RentClient client = new RentClient();
        client.setDefaultUri("https://localhost:8083/rent-service/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(messageSender());
        return client;
    }


    @Bean
    public HttpsUrlConnectionMessageSender messageSender() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, IOException, CertificateException {

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(new File("store/agent.p12")), "password".toCharArray());
        if(ks.containsAlias("agent")){
            System.out.println("Ima agenta");
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(ks, "password".toCharArray());

        KeyStore ts = KeyStore.getInstance("PKCS12");
        ts.load(new FileInputStream(new File("store/truststore.p12")), "password".toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(ts);

        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

        // otherwise: java.security.cert.CertificateException: No name matching localhost found
        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if (hostname.equals("localhost") || hostname.equals("zuul")) {
                return true;
            }
            return false;
        });

        return messageSender;

    }
}
