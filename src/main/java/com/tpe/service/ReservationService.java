package com.tpe.service;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Guest;
import com.tpe.domain.Hotel;
import com.tpe.domain.Reservation;
import com.tpe.domain.Room;
import com.tpe.exceptions.HotelNotFoundException;
import com.tpe.exceptions.ReservationNotFoundException;
import com.tpe.repository.HotelRepository;
import com.tpe.repository.ReservationRepository;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static java.time.LocalDate.parse;

public class ReservationService {
    private Scanner scanner=new Scanner(System.in);

    private ReservationRepository reservasyonRepository;

    private final GuestService guestService;

    private final RoomService roomService;

    public ReservationService(ReservationRepository reservationRepository, GuestService guestService, RoomService roomService){
        this.reservasyonRepository=reservationRepository;
        this.guestService = guestService;
        this.roomService = roomService;
    }

    public void saveReservation(){

        Reservation reservation=new Reservation();
        //Hotel hotel=new Hotel();

        System.out.println("Enter reservation ID");
        reservation.setId( scanner.nextLong());
        scanner.nextLine();         // int gibi alt satıra geçmez elle alt satıra geçirmesini sağladık

        System.out.println("Enter reservation name");
        //reservation.setName(scanner.nextLine());

        System.out.println("Enter reservation contact");
        //reservation.setContact(scanner.nextLine());

        System.out.println("Enter reservation information");
        //reservation.setInformation(scanner.nextLine());

        reservasyonRepository.save(reservation);

        System.out.println("reservation ID = " + reservation.getId());;
    }

    public Reservation findReservationById(Long id){

        Reservation foundReservation=ReservationRepository.findById(id);
        try {
            if(foundReservation!=null) {
                System.out.println("----------------------------------------------");
                System.out.println(foundReservation);
                System.out.println("----------------------------------------------");
                return foundReservation;
            }
            else {
                throw new ReservationNotFoundException("Reservation Nor Found By Id : "+id);
            }
        } catch (ReservationNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //3-c : tüm otelleri listeleme
    public void findAllReservations() {
        List<Reservation> allReservations=reservasyonRepository.findAll();
        if (!allReservations.isEmpty()){
            System.out.println("----------------------ALL RESERVATıONS-------------------------");
            for(Reservation r:allReservations){
                System.out.println(r);
            }
            System.out.println("----------------------------------------------------------");
        } else{
            System.out.println("Reservation list is empty");
        }
    }


    public void deleteReservationById(Long id) {

        Reservation foundReservation=findReservationById(id);

        if(foundReservation!=null){
            System.out.println(foundReservation);
            System.out.println("Are you sure to delete? ");
            System.out.println("Please answer Y or N");
            String select=scanner.nextLine();
            if(select.equalsIgnoreCase("Y")){
                reservasyonRepository.delete(foundReservation);
                System.out.println("Hotel is deleted SUCCESSFULLY");
            } else {
                System.out.println("Delete operation is CANCELLED!");
            }
        }
    }

    public void createReservation() {
        Reservation reservation=new Reservation();

        //girilen tarihlerin uygunluğu kontrol edilmiş kabul ediyoruz...
        System.out.println("Enter check-in date(yyyy-MM-dd)");
        reservation.setCheckInDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));


        System.out.println("Enter check-out date(yyyy-MM-dd)");
        reservation.setCheckOutDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));


        System.out.println("Enter room id : ");
        Long roomId=scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter guest id : ");
        Long guestId=scanner.nextLong();
        scanner.nextLine();

        Room room=roomService.findRoomById(roomId);
        Guest guest=guestService.findGuestById(guestId);

        reservasyonRepository.save(reservation);
        System.out.println("Rezervation is created successfully");
    }
}
