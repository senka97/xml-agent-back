package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;

import com.example.team19.dto.NewReplyDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.service.AdService;
import com.example.team19.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManager;
import javax.transaction.Transactional;

public class CarClient extends WebServiceGatewaySupport {

    @Autowired
    private AdService adService;

    @Autowired
    private LoginClient loginClient;

   public CommentsResponse getComments(Long adId) {

        CommentsRequest request = new CommentsRequest();

       Advertisement ad = this.adService.findById(adId);
       if(ad == null)
           return null;

       request.setId(ad.getMainId());

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

    public SendReplyResponse replyComment(Long commentId, NewReplyDTO reply) {

        SendReplyRequest request = new SendReplyRequest();
        request.setCommentId(commentId);
        request.setReplyContent(reply.getReplyContent());

        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        SendReplyResponse response = (SendReplyResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://localhost:8083/car-service/ws/soap", request,
                        new SoapActionCallback(
                                "http://www.rent-a-car.com/car-service/soap/SendReplyRequest"));

        return response;
    }


    public GetCarWithMostCommentsResponse getCarWithMostComments(){
        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        System.out.println("Most Comments");
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse  loginResponse = loginClient.login();

        GetCarWithMostCommentsRequest request = new GetCarWithMostCommentsRequest();
        request.setId(1);
        GetCarWithMostCommentsResponse response = (GetCarWithMostCommentsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapRequestHeaderModifier(loginResponse.getToken()));

        System.out.println(response.getCarSOAP().getCarModel());
        return response;

    }

    public GetCarWithBestRateResponse getCarWithBestRate(){
        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        System.out.println("Best Rate");
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse  loginResponse = loginClient.login();

        GetCarWithBestRateRequest request = new GetCarWithBestRateRequest();
        request.setId(1);
        GetCarWithBestRateResponse response = (GetCarWithBestRateResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapRequestHeaderModifier(loginResponse.getToken()));

        System.out.println(response.getCarSOAP().getCarModel());
        return response;

    }

    public GetCarWithMostKilometersResponse getCarWithMostKilometers(){
        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        System.out.println("Most Comments");
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse  loginResponse = loginClient.login();

        GetCarWithMostKilometersRequest request = new GetCarWithMostKilometersRequest();
        request.setId(1);
        GetCarWithMostKilometersResponse response = (GetCarWithMostKilometersResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapRequestHeaderModifier(loginResponse.getToken()));

        System.out.println(response.getCarSOAP().getCarModel());
        return response;

    }
}
