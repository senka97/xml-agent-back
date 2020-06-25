package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;
import com.example.team19.dto.ReservationDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.service.impl.AdServiceImpl;
import com.example.team19.service.impl.ReservationServiceImpl;
import com.example.team19.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.TrustManager;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentClient extends WebServiceGatewaySupport {

    @Autowired
    private LoginClient loginClient;


    public GetPendingRResponse getPendingRequests(List<Long> ids){

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();

        GetPendingRRequest gpr = new GetPendingRRequest();
        gpr.getIds().addAll(ids);

        GetPendingRResponse response = (GetPendingRResponse) getWebServiceTemplate()
                .marshalSendAndReceive(gpr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;
    }

    public AddReservationResponse addReservation(ReservationDTO reservationDTO,Long adMainId, double pricePerKm, double payment){

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        AddReservationRequest arr = new AddReservationRequest();
        arr.setAdMainId(adMainId);
        arr.setCurrentPricePerKm(pricePerKm);
        arr.setClientFirstName(reservationDTO.getClientFirstName());
        arr.setClientLastName(reservationDTO.getClientLastName());
        arr.setClientPhoneNumber(reservationDTO.getClientLastName());
        arr.setClientPhoneNumber(reservationDTO.getClientPhoneNumber());
        arr.setClientEmail(reservationDTO.getClientEmail());
        arr.setStartDate(reservationDTO.getStartDate().toString());
        arr.setEndDate(reservationDTO.getEndDate().toString());
        arr.setPayment(payment);

        LoginResponse loginResponse = loginClient.login();

        AddReservationResponse response = (AddReservationResponse) getWebServiceTemplate()
                .marshalSendAndReceive(arr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;
    }

    public RejectPendingRResponse rejectPendingRequest(Long id){

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();
        RejectPendingRRequest rpr = new RejectPendingRRequest();
        rpr.setIdMain(id);

        RejectPendingRResponse response = (RejectPendingRResponse) getWebServiceTemplate()
                .marshalSendAndReceive(rpr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;

    }

    public AcceptPendingRResponse acceptPendingRequest(Long id){

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();
        AcceptPendingRRequest apr = new AcceptPendingRRequest();
        apr.setIdMain(id);

        AcceptPendingRResponse response = (AcceptPendingRResponse) getWebServiceTemplate()
                .marshalSendAndReceive(apr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;

    }

    public GetMessagesResponse getMessagesFromMainApp(Long mainIdRequest, List<Long> existingMessages){

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();
        GetMessagesRequest gmr = new GetMessagesRequest();
        gmr.setMainIdRequest(mainIdRequest);
        gmr.getExistingMessages().addAll(existingMessages);

        GetMessagesResponse response = (GetMessagesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(gmr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;
    }

    public AddMessageResponse addMessage(Long mainIdRequest, String content){

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();
        AddMessageRequest amr = new AddMessageRequest();
        amr.setMainIdRequest(mainIdRequest);
        amr.setContent(content);

        AddMessageResponse response = (AddMessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(amr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;
    }

    public CreateRequestReportResponse createRequestReport(Long requestAdId, String content, double km)
    {
        CreateRequestReportRequest request = new CreateRequestReportRequest();
        ReportSOAP report = new ReportSOAP();
        report.setContent(content);
        report.setKm(km);
        report.setRequestAdId(requestAdId);
        request.setReportSOAP(report);

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();

        CreateRequestReportResponse response = (CreateRequestReportResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapRequestHeaderModifier(loginResponse.getToken()));

        return response;
    }

    public CreateReservationReportResponse createReservationReport(Long reservationId, String content, double km)
    {
        CreateReservationReportRequest request = new CreateReservationReportRequest();
        ReportSOAP report = new ReportSOAP();
        report.setContent(content);
        report.setKm(km);
        report.setReservationId(reservationId);
        request.setReportSOAP(report);

        //HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        //sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        //getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();

        CreateReservationReportResponse response = (CreateReservationReportResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapRequestHeaderModifier(loginResponse.getToken()));

        return response;
    }
}
