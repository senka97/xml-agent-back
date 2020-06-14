package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;

import com.example.team19.wsdl.CommentsRequest;
import com.example.team19.wsdl.CommentsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManager;

public class CarClient extends WebServiceGatewaySupport {

   public CommentsResponse getComments(Long adId) {

        CommentsRequest request = new CommentsRequest();
        request.setId(adId);

        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        CommentsResponse response = (CommentsResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://localhost:8083/car-service/ws/soap", request,
                        new SoapActionCallback(
                                "http://www.rent-a-car.com/car-service/soap/CommentsRequest"));

        return response;
    }
}
