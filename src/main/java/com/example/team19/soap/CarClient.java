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


    public MostCommentsResponse getCarWithMostComments(){
        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        System.out.println("Most Comments");
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse  loginResponse = loginClient.login();

        MostCommentsRequest request = new MostCommentsRequest();
        request.setId(1);
        MostCommentsResponse response = (MostCommentsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapRequestHeaderModifier(loginResponse.getToken()));

        System.out.println(response.getCarSOAP().getCarModel());
        return response;

    }
}
