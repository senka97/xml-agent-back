package com.example.team19.service.impl;

import com.example.team19.dto.ReservationDTO;
import com.example.team19.dto.ReservationResponseDTO;
import com.example.team19.enums.RequestStatus;
import com.example.team19.model.Advertisement;
import com.example.team19.model.Request;
import com.example.team19.model.Reservation;
import com.example.team19.repository.ReservationRepository;
import com.example.team19.service.ReservationService;
import com.example.team19.soap.RentClient;
import com.example.team19.wsdl.AddReservationResponse;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdServiceImpl adService;

    @Autowired
    private RequestServiceImpl requestService;

    @Autowired
    private RentClient rentClient;


    @Override
    public ReservationResponseDTO createNewReservation(ReservationDTO reservationDTO) {

        // TODO Proverim da li postoji ovaj oglas u trazenom periodu u rezervacijama
        // TODO Proverim da li postoji u zahtevima
        // TODO Ako postoji odmah odbijam, ako ne tek onda saljem upit na glavnu
        // TODO Ako je na glavnoj slobodno, tamo napravim rezervaciju i sacuvam je, zatim je vratim ovde i isto sacuvam
        // TODO Ako nije slobodno uzmem sve zahteve i rezervacije koji se poklapaju sa trazenim terminom, vratim ih ovde i sacuvam


        /*Advertisement ad = adService.findById(reservation.getAdId());
        if (ad != null) {
            Set<Reservation> reservations = findReservationsForThisAd(ad.getId());
            for( Reservation r : reservations) {
                if(reservation.getStartDate().equals(r.getStartDate()) || reservation.getStartDate().equals(r.getEndDate())
                        || reservation.getEndDate().equals(r.getStartDate()) || reservation.getEndDate().equals(r.getEndDate())) {
                    return  null;
                }
                if(reservation.getStartDate().isAfter(r.getStartDate())) {
                    if(reservation.getStartDate().isBefore(r.getEndDate()) ) {
                        return null;
                    }
                }
                else if(reservation.getEndDate().isBefore(r.getEndDate())) {
                    if(reservation.getEndDate().isAfter(r.getStartDate())) {
                        return null;
                    }
                }
                else if(reservation.getStartDate().isBefore(r.getStartDate()) && reservation.getEndDate().isAfter(r.getEndDate())) {
                    return null;
                }
            }

            Reservation newReservation = new Reservation();

            newReservation.setClientFirstName(reservation.getClientFirstName());
            newReservation.setClientLastName(reservation.getClientLastName());
            newReservation.setClientEmail(reservation.getClientEmail());
            newReservation.setClientPhoneNumber(reservation.getClientPhoneNumber());
            newReservation.setStartDate(reservation.getStartDate());
            newReservation.setEndDate(reservation.getEndDate());
            newReservation.setAdvertisement(ad);

            return reservationRepository.save(newReservation);

        }
        return null;*/

        //Prvo proverim da li u lokalnog bazi agenta postoje rezervacije za ovaj oglas u tom periodu
        List<Reservation> reservations = this.reservationRepository.findReservationsForThisAdForThisPeriod(reservationDTO.getAdId(),reservationDTO.getStartDate(),reservationDTO.getEndDate());
        if(reservations.size() > 0){
            return null; //vrati null, zauzeto je
        }
        //Ako ne postoji u rezervacijama proverim u zahtevima
        List<Request> requests = this.requestService.findPaidRequestsForAdForThisPeriod(reservationDTO.getAdId(),reservationDTO.getStartDate(),reservationDTO.getEndDate());
        if(requests.size() > 0){
            return null;
        }

        //sada posaljem tu rezervaciju na glavnu da se napravi i da se vrati id sa glavne
        //na glavnoj su mozda neki pending requests odbijeni zbog ove rezervacije
        //pa cu vratiti id od tih odbijenih da se i ovde odbiju

        Advertisement ad = adService.findById(reservationDTO.getAdId());

        double payment = 0;
        long days = ChronoUnit.DAYS.between(reservationDTO.getStartDate(),reservationDTO.getEndDate()) + 1; //plus 1 da bi kad rezervise na jedan dan, cena bila za 1 dan
        if(days<20){
            payment = days*ad.getPriceList().getPricePerDay();
        }else if(days>=20 && days<30){
            payment = days*ad.getPriceList().getPricePerDay() - (ad.getPriceList().getDiscount20Days()/100)*(days*ad.getPriceList().getPricePerDay());
        }else{
            payment = days*ad.getPriceList().getPricePerDay() - (ad.getPriceList().getDiscount30Days()/100)*(days*ad.getPriceList().getPricePerDay());
        }

        AddReservationResponse arr = this.rentClient.addReservation(reservationDTO, ad.getMainId(),
                ad.getPriceList().getPricePerDay(), payment);
        if(arr.isSuccess()){
             Reservation r = new Reservation(reservationDTO);
             r.setAdvertisement(ad);
             r.setPayment(payment);
             r.setCurrentPricePerKm(ad.getPriceList().getPricePerKm());
             r.setMainId(arr.getMainId());
             Reservation res = this.reservationRepository.save(r);
             ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO(res);
             //sada se odbiju svi ti zahtevi koji su odbijeni i na glavnoj app
            for(Long idCanceled: arr.getCanceledRequests()){
                Request requestCanceled = this.requestService.findByMainId(idCanceled);
                if(requestCanceled != null) { // u slucaju da ti neki pending requests nisu povuceni na agenta jos uvek pa da ne pukne
                    requestCanceled.setStatus(RequestStatus.Canceled);
                    this.requestService.save(requestCanceled);
                }
            }

             return reservationResponseDTO;
        }else {
            return new ReservationResponseDTO(); //vrati prazan ako se desila greska prilikon cuvanja na glavnoj
        }
    }

    @Override
    public Set<Reservation> findReservationsForThisAd(Long adId) {
        return reservationRepository.findReservationsForThisAd(adId);
    }


}
