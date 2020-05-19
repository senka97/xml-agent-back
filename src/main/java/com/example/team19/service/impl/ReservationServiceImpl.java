package com.example.team19.service.impl;

import com.example.team19.dto.ReservationDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.model.Reservation;
import com.example.team19.repository.ReservationRepository;
import com.example.team19.service.ReservationService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdServiceImpl adService;


    @Override
    public Reservation createNewReservation(ReservationDTO reservation) {

        Advertisement ad = adService.findById(reservation.getAdId());

        if (ad != null)
        {
            // TODO proveriti isto i za zahteve sa glavne aplikacije

            Set<Reservation> reservations = findReservationsForThisAd(ad.getId());
            for( Reservation r : reservations)
            {
                if(reservation.getStartDate().equals(r.getStartDate()) || reservation.getStartDate().equals(r.getEndDate())
                        || reservation.getEndDate().equals(r.getStartDate()) || reservation.getEndDate().equals(r.getEndDate()))
                {
                    return  null;
                }

                if(reservation.getStartDate().isAfter(r.getStartDate()))
                {
                    if(reservation.getStartDate().isBefore(r.getEndDate()) )
                    {
                        return null;
                    }
                }
                else if(reservation.getEndDate().isBefore(r.getEndDate()))
                {
                    if(reservation.getEndDate().isAfter(r.getStartDate()))
                    {
                        return null;
                    }
                }
                else if(reservation.getStartDate().isBefore(r.getStartDate()) && reservation.getEndDate().isAfter(r.getEndDate()))
                {
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
        return null;
    }

    @Override
    public Set<Reservation> findReservationsForThisAd(Long adId) {
        return reservationRepository.findReservationsForThisAd(adId);
    }


}
