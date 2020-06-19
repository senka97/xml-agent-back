package com.example.team19.soap;

import com.example.team19.config.UnTrustworthyTrustManager;
import com.example.team19.dto.AdDTO;
import com.example.team19.dto.PriceListRequestDTO;
import com.example.team19.service.impl.CarServiceImpl;
import com.example.team19.service.impl.PriceListServiceImpl;
import com.example.team19.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;


import javax.net.ssl.TrustManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class AdClient extends WebServiceGatewaySupport {

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private PriceListServiceImpl priceListService;

    @Autowired
    private LoginClient loginClient;

    public PostAdResponse postAd(AdDTO adDTO){
        /*
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("agent@gmail.com");
        loginRequest.setPassword("agent");



        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = (LoginResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://localhost:8083/user-service/ws/Ad", loginRequest,
                        new SoapActionCallback(
                                "http://www.rent-a-car.com/ad-service/soap/LoginRequest"));

*/      HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });

        LoginResponse loginResponse = loginClient.login();
        String startDateStr = adDTO.getStartDate().toString();
        String endDateStr = adDTO.getEndDate().toString();
        System.out.println(startDateStr);
/*
        XMLGregorianCalendar startDate = null;
        XMLGregorianCalendar endDate = null;

        try {
            startDate =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(adDTO.getStartDate().toString());
            endDate =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(adDTO.getEndDate().toString());
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }*/

        PostAdRequest request = new PostAdRequest();



        //request.setOwnerId(2);
        request.setStartDate(startDateStr);
        request.setEndDate(endDateStr);
        request.setCdw(adDTO.isCdw());
        request.setLimitKm(adDTO.getLimitKm());
        request.setLocation(adDTO.getLocation());
        request.setLimitKm(adDTO.getLimitKm());
        PriceList priceList = new PriceList();


        com.example.team19.model.PriceList existingPl = priceListService.findById(adDTO.getPriceList().getId());

        if(existingPl.getMainId() != null){
            priceList.setId(existingPl.getMainId());
        }else{
            priceList.setId(0);
            priceList.setAlias(existingPl.getAlias());
            priceList.setDiscount20Days(existingPl.getDiscount20Days());
            priceList.setDiscount30Days(existingPl.getDiscount30Days());
            priceList.setPricePerDay(existingPl.getPricePerDay());
            priceList.setPricePerKm(existingPl.getPricePerKm());
        }


        request.setPriceList(priceList);



        Car car = new Car();
        System.out.println("Car BRAND: "+adDTO.getCar().getCarModel().getCarBrand().getName());
        car.setCarBrand(adDTO.getCar().getCarModel().getCarBrand().getName());
        car.setCarModel(adDTO.getCar().getCarModel().getName());
        car.setCarClass(adDTO.getCar().getCarClass().name());
        car.setChildrenSeats(adDTO.getCar().getChildrenSeats());
        car.setFeulType(adDTO.getCar().getFuelType().name());
        car.setHasAndroidApp(adDTO.getCar().isHasAndroidApp());
        car.setMileage(adDTO.getCar().getMileage());
        car.setTransType(adDTO.getCar().getTransType().name());

        for(String img: adDTO.getCar().getPhotos64()) {
            car.getPhotos64().add(img);
        }

        if(adDTO.getCar().getId() != null){
            com.example.team19.model.Car existingCar = carService.findById(adDTO.getCar().getId());
            if(existingCar.getMainId() != null) {
                car.setId(existingCar.getMainId());
            }else{
                car.setId(0);
            }

        }else{
            car.setId(0);
        }


        request.setCar(car);



        //ovo ovde je da bi se ignorisala provera za sertifikat, zato sto se koristi https
        //radilo bi i da tu stoji http i uri za direktno servis, ali ovo je da ide preko zuul
        sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });

        getWebServiceTemplate().setMessageSender(sender);


        PostAdResponse response = (PostAdResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));

        return response;
    }

    public AddPriceListResponse addPriceList(PriceListRequestDTO priceListRequestDTO){

        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();
        AddPriceListRequest apr = new AddPriceListRequest();
        apr.setAlias(priceListRequestDTO.getAlias());
        apr.setPricePerDay(priceListRequestDTO.getPricePerDay());
        apr.setPricePerKm(priceListRequestDTO.getPricePerKm());
        apr.setPriceForCdw(priceListRequestDTO.getPriceForCdw());
        apr.setDiscount20Days(priceListRequestDTO.getDiscount20Days());
        apr.setDiscount30Days(priceListRequestDTO.getDiscount30Days());

        AddPriceListResponse response = (AddPriceListResponse) getWebServiceTemplate()
                .marshalSendAndReceive(apr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;
    }

    public DeletePriceListResponse deletePriceList(Long mainId){

        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setTrustManagers(new TrustManager[] { new UnTrustworthyTrustManager() });
        getWebServiceTemplate().setMessageSender(sender);

        LoginResponse loginResponse = loginClient.login();
        DeletePriceListRequest dpr = new DeletePriceListRequest();
        dpr.setMainId(mainId);

        DeletePriceListResponse response = (DeletePriceListResponse) getWebServiceTemplate()
                .marshalSendAndReceive(dpr,
                        new SoapRequestHeaderModifier(loginResponse.getToken()));
        return response;
    }


}
