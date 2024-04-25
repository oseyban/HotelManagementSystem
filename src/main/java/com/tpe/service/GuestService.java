package com.tpe.service;

import com.tpe.domain.Address;
import com.tpe.domain.Guest;
import com.tpe.exceptions.GuestNotFoundException;
import com.tpe.repository.GuestRepository;

import java.util.List;
import java.util.Scanner;

public class GuestService {
    private Scanner scanner=new Scanner(System.in);

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // ödev2
    public Guest findGuestById(Long id) {
        try {
            Guest foundGuest = guestRepository.findById(id);
            if(foundGuest!=null){
                System.out.println("*-----------------------------*");
                System.out.println(foundGuest);
                return foundGuest;
            }else{
                throw new GuestNotFoundException("Guest not found with id : "+id);
            }
        }catch (GuestNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    //ödev3
    public void findAllGuest() {
        List<Guest> guests = guestRepository.findAll();
        if (!guests.isEmpty()) {
            for (Guest guest : guests) {
                System.out.println(guest);
            }
        } else {
            System.out.println("Guest not found");
        }
    }

    //9-b
    public void saveGuest() {
        Guest guest=new Guest();

       System.out.println("Enter guest Id : ");
        guest.setId(scanner.nextLong());
        scanner.nextLine();

        System.out.println("Enter guest name : ");
        guest.setName(scanner.nextLine());

        Address address=new Address();

        System.out.println("Enter street : ");
        address.setStreet(scanner.nextLine());

        System.out.println("Enter city : ");
        address.setCity(scanner.nextLine());

        System.out.println("Enter country : ");
        address.setCountry(scanner.nextLine());

        System.out.println("Enter zipcode : ");
        address.setZipcode(scanner.nextInt());

        guest.setAddress(address);

        guestRepository.save(guest);
        System.out.println("Guest is saved successfully...");

    }

    //10-b
    public void deleteGuestById(Long id) {
        Guest foundGuest=findGuestById(id);
        //guestin rezervasyonları varsa orphanremoval ile bunlar otomatik silinsin
        if (foundGuest!=null){
            guestRepository.delete(foundGuest);
            System.out.println("Guest is removed successfully..Guest id : "+foundGuest.getId());
        }
    }
}