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
import java.time.LocalDateTime;
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

    public void saveReservation() {
        Scanner scanner = new Scanner(System.in);

        // Yeni bir rezervasyon nesnesi oluştur
        Reservation reservation = new Reservation();

        // Rezervasyon ID'sini giriş al
        System.out.println("Enter reservation ID");
        reservation.setId(scanner.nextLong());
        scanner.nextLine(); // Scanner'ı temizle

        // Check-in tarihini al
        System.out.println("Enter CheckIn Date (yyyy-MM-dd)");
        String checkInDateString = scanner.nextLine();
        LocalDate checkInDate = LocalDate.parse(checkInDateString);
        reservation.setCheckInDate(checkInDate);

        // Check-out tarihini al
        System.out.println("Enter CheckOut Date (yyyy-MM-dd)");
        String checkOutDateString = scanner.nextLine();
        LocalDate checkOutDate = LocalDate.parse(checkOutDateString);
        reservation.setCheckOutDate(checkOutDate);

        // Misafir bilgilerini al
        System.out.println("Reservasyon yapılacak  kişinin id bilgisini girin");
        guestService.findAllGuest();
        Long id=scanner.nextLong();
        Guest guest = guestService.findGuestById(id);
        reservation.setGuest(guest);

        // Oda bilgilerini al
        System.out.println("Reservasyon yapılacak  odanın id bilgisini girin");
        roomService.getAllRooms();
        Long roomid=scanner.nextLong();
        Room room = roomService.findRoomById(roomid);
        reservation.setRoom(room);

        // Rezervasyonu kaydet
        reservasyonRepository.save(reservation);


        System.out.println("Reservation ID = " + reservation.getId());
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
