package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;
import com.example.team19.wsdl.LoginRequest;
import com.example.team19.wsdl.LoginResponse;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManager;
import javax.xml.soap.MimeHeaders;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class SoapRequestHeaderModifier implements WebServiceMessageCallback  {
    private final String email = "agent@gmail.com";
    private final String password = "agent";
    private String token= "";
    SoapRequestHeaderModifier(String token){
        this.token="Agent "+token;
    }

    @Override
    public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {

        SaajSoapMessage soapMessage = (SaajSoapMessage) webServiceMessage;
        MimeHeaders mimeHeader = soapMessage.getSaajMessage().getMimeHeaders();
        mimeHeader.setHeader("Authorization",token);
    }

}
