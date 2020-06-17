package com.example.team19.service.impl;

import com.example.team19.dto.AdFrontDTO;
import com.example.team19.dto.CarFrontDTO;
import com.example.team19.dto.RequestAdFrontDTO;
import com.example.team19.dto.RequestFrontDTO;
import com.example.team19.enums.RequestStatus;
import com.example.team19.model.Advertisement;
import com.example.team19.model.Car;
import com.example.team19.model.Request;
import com.example.team19.model.RequestAd;
import com.example.team19.repository.RequestRepository;
import com.example.team19.service.CarService;
import com.example.team19.service.RequestService;
import com.example.team19.soap.RentClient;

import com.example.team19.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RentClient rentClient;
    @Autowired
    private AdServiceImpl adService;

    @Autowired
    private CarService carService;


    @Override
    public Request findById(Long id) {
        return this.requestRepository.findById(id).orElse(null);
    }

    @Override
    public List<RequestFrontDTO> getPendingRequestsFront() {

        //ovde posaljem id od svih pending zahteva koji postoje na agentu
        //ti id su oni sa glavne aplikacije
        //tamo prvo proverim da li su svi oni i dalje pending (mozda ih je neko otkazao)
        //za one koji nisu vise pending vratim ih u listi canceled i to promenim ovde
        //zatim proverim da li postoje jos neki pending zahtevi pored tih koje sa poslala

        List<Long> pendingRequestsMainId = this.requestRepository.pendingRequestsMainId();
        GetPendingRResponse gpr = this.rentClient.getPendingRequests(pendingRequestsMainId);
        System.out.println(gpr.getRequestSOAP().size());

        //sada prodjem kroz sve te koji su u medjuvremenu otkazani na glavnoj
        for(Long id: gpr.getCanceledRequests()){
            Request r = this.requestRepository.findByMainId(id);
            r.setStatus(RequestStatus.Canceled);
            this.requestRepository.save(r);
        }

        //sada prodjem kroz nove pending sa glavne i njih ovde sacuvam

        for(RequestSOAP rs: gpr.getRequestSOAP()){
            Request request = new Request();
            request.setStatus(RequestStatus.Pending);
            request.setMainId(rs.getId());
            request.setClientFirstName(rs.getClientFirstName());
            request.setClientLastName(rs.getClientLastName());
            request.setClientEmail(rs.getClientEmail());
            request.setClientPhoneNumber(rs.getClientPhoneNumber());
            for(RequestAdSOAP ras: rs.getRequestAdSOAP()){
                System.out.println("Postoji request ad");
                RequestAd requestAd = new RequestAd();
                requestAd.setMainId(ras.getId());
                requestAd.setStartDate(LocalDate.parse(ras.getStartDate()));
                requestAd.setEndDate(LocalDate.parse(ras.getEndDate()));
                requestAd.setPayment(ras.getPayment());
                requestAd.setCurrentPricePerKm(ras.getCurrentPricePerKm());
                requestAd.setRequest(request);
                request.getRequestAds().add(requestAd);
                Advertisement ad = this.adService.findAdByMainId(ras.getAdId());
                requestAd.setAdvertisement(ad);
            }
            this.requestRepository.save(request);
        }

        //ovde se naprave dto za front i vrate
        List<RequestFrontDTO> requestFrontDTOs = new ArrayList<>();
        List<Request> pendingRequests = this.requestRepository.findPendingRequestsFront();

        if(pendingRequests.size() == 0){
            return requestFrontDTOs;
        }
        requestFrontDTOs = makeRequestFrontDTOs(pendingRequests);

        return requestFrontDTOs;
    }

    @Override
    public List<RequestFrontDTO> getPaidRequestsFront() {

        List<Request> paidRequests = this.requestRepository.findPaidRequestsFront();
        List<RequestFrontDTO> requestFrontDTOs = new ArrayList<>();

        if(paidRequests.size() == 0){
            return requestFrontDTOs;
        }
        requestFrontDTOs = makeRequestFrontDTOs(paidRequests);

        return requestFrontDTOs;

    }

    public List<RequestFrontDTO> makeRequestFrontDTOs(List<Request> requests) {

        List<RequestFrontDTO> requestFrontDTOs = new ArrayList<>();

        for (Request r : requests)
        {
            RequestFrontDTO requestFrontDTO = new RequestFrontDTO();

            requestFrontDTO.setId(r.getId());
            requestFrontDTO.setMainId(r.getMainId());
            requestFrontDTO.setStatus(r.getStatus().toString());
            requestFrontDTO.setClientName(r.getClientFirstName());
            requestFrontDTO.setClientLastName(r.getClientLastName());
            requestFrontDTO.setClientPhoneNumber(r.getClientPhoneNumber());
            requestFrontDTO.setClientEmail(r.getClientEmail());

            List<RequestAdFrontDTO> requestAdFrontDTOs = new ArrayList<>();

            for(RequestAd ra : r.getRequestAds() )
            {
                RequestAdFrontDTO requestAdFrontDTO = new RequestAdFrontDTO();

                requestAdFrontDTO.setId(ra.getId());
                requestAdFrontDTO.setMainId(ra.getMainId());
                requestAdFrontDTO.setStartDate(ra.getStartDate());
                requestAdFrontDTO.setEndDate(ra.getEndDate());
                requestAdFrontDTO.setCurrentPricePerKm(ra.getCurrentPricePerKm());
                requestAdFrontDTO.setPayment(ra.getPayment());

                Advertisement ad = this.adService.findById(ra.getAdvertisement().getId());
                AdFrontDTO a = new AdFrontDTO();

                a.setId(ad.getId());
                a.setMainId(ad.getMainId());
                a.setStartDate(ad.getStartDate());
                a.setEndDate(ad.getEndDate());
                a.setLimitKm(ad.getLimitKm());
                a.setLocation(ad.getLocation());
                a.setCdw(ad.getCdw());

                Car car = this.carService.findById(ad.getCar().getId());
                CarFrontDTO c = new CarFrontDTO();

                c.setId(car.getId());
                c.setMainId(car.getMainId());
                ArrayList<String> photos = this.carService.getCarPhotos(c.getId());
                c.setPhoto64(photos.get(0));
                c.setCarModel(car.getCarModel().getName());
                c.setCarBrand(car.getCarModel().getCarBrand().getName());
                c.setMileage(car.getMileage());
                c.setChildrenSeats(car.getChildrenSeats());
                c.setRate(car.getRate());

                a.setCar(c);
                requestAdFrontDTO.setAd(a);

                requestAdFrontDTOs.add(requestAdFrontDTO);

            }

            requestFrontDTO.setRequestAds(requestAdFrontDTOs);
            requestFrontDTOs.add(requestFrontDTO);
        }

        return requestFrontDTOs;
    }

    @Override
    public List<Request> findPaidRequestsForAdForThisPeriod(Long id, LocalDate startDate, LocalDate endDate) {
        return this.requestRepository.findPaidRequestsForAdForThisPeriod(id, startDate, endDate);
    }

    @Override
    public String rejectPendingRequest(Long id) {
        //prvo odbijem na glavnoj pa onda ovde sacuvam to
        Request r = this.requestRepository.findById(id).orElse(null);
        RejectPendingRResponse rpResponse = this.rentClient.rejectPendingRequest(r.getMainId());
        if(rpResponse.isSuccess()){
            r.setStatus(RequestStatus.Canceled);
            this.requestRepository.save(r);
            return null;
        }else{
            return rpResponse.getMessage();
        }
    }

    @Override
    public String acceptPendingRequest(Long id) {
        //prvo prihvatim na glavnoj pa onda ovde sacuvam to
        Request r = this.requestRepository.findById(id).orElse(null);
        //Na glavnoj ce se mozda neki zahtevi automatski odbiti pa ovde vratim id od tih odbijenih
        AcceptPendingRResponse apResponse = this.rentClient.acceptPendingRequest(r.getMainId());
        System.out.println(apResponse.getMessage());
        System.out.println(apResponse.isSuccess());
        System.out.println(apResponse.getCanceledRequests().size());
        if(apResponse.isSuccess()){
            r.setStatus(RequestStatus.Paid);
            this.requestRepository.save(r);
            //odbijem ovde sve te koji su odbijeni na glavnoj
            for(Long idCanceled: apResponse.getCanceledRequests()){
                Request requestCanceled = this.requestRepository.findByMainId(idCanceled);
                if(requestCanceled != null) { //u slucaju da ti pending requests nisu povuceni bili jos uvek na agenta pa da ne pukne
                    requestCanceled.setStatus(RequestStatus.Canceled);
                    this.requestRepository.save(requestCanceled);
                }
            }
            return null;
        }else{
            r.setStatus(RequestStatus.Canceled);
            this.requestRepository.save(r);
            return apResponse.getMessage();
        }
    }

    @Override
    public int getPendingRequestsNumber() {

        List<Request> pendingRequests = this.requestRepository.findPendingRequestsFront();
        return pendingRequests.size();
    }

    @Override
    public Request findByMainId(Long id) {
        return this.requestRepository.findByMainId(id);
    }

    @Override
    public Request save(Request request) {
        return this.requestRepository.save(request);
    }
}
