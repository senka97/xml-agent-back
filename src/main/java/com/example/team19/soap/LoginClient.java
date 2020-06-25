package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;
import com.example.team19.dto.AdDTO;
import com.example.team19.model.TokenInfo;
import com.example.team19.service.impl.TokenInfoServiceImpl;
import com.example.team19.wsdl.LoginRequest;
import com.example.team19.wsdl.LoginResponse;
import com.example.team19.wsdl.PostAdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManager;
import java.util.Date;

public class LoginClient extends WebServiceGatewaySupport {

    @Autowired
    private TokenInfoServiceImpl tokenInfoService;

    public LoginResponse login(){
        LoginResponse loginResponse = new LoginResponse();
        if(tokenInfoService.checkIfExpired() == true) {
            System.out.println("Login");
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail("agent@gmail.com");
            loginRequest.setPassword("agent");

//            HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
//            sender.setTrustManagers(new TrustManager[]{new UnTrustworthyTrustManager()});
//
//            getWebServiceTemplate().setMessageSender(sender);

            System.out.println("Dodje dovde");
            loginResponse = (LoginResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(loginRequest);
            System.out.println("Prodje");

            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setToken(loginResponse.getToken());
            Date date = new Date(System.currentTimeMillis()+loginResponse.getExpiration());
            tokenInfo.setExpires(date);
            tokenInfoService.save(tokenInfo);
        }else{
            System.out.println("Exists");
            loginResponse.setToken(tokenInfoService.getToken().getToken());
        }

        return loginResponse;

    }
}
