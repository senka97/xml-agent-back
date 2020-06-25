package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;
import com.example.team19.wsdl.GetTestRequest;
import com.example.team19.wsdl.GetTestResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManager;

public class TestClient extends WebServiceGatewaySupport {

    public GetTestResponse getTest(String test) {

        GetTestRequest request = new GetTestRequest();
        request.setName(test);

        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        /*GetTestResponse response = (GetTestResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://localhost:8083/rent-service/ws/test", request,
                        new SoapActionCallback(
                                "http://www.rent-a-car.com/rent-service/soap/GetTestRequest"));*/

        GetTestResponse response = (GetTestResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }
}
