package com.tpe.service;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Hotel;
import com.tpe.exceptions.HotelNotFoundException;
import com.tpe.repository.HotelRepository;
import org.hibernate.HibernateException;

import javax.lang.model.SourceVersion;
import java.util.List;
import java.util.Scanner;

public class HotelService {

    private Scanner scanner=new Scanner(System.in);

    private HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository){
        this.hotelRepository=hotelRepository;
    }

    public void saveHotel(){

        Hotel hotel=new Hotel();

        System.out.println("Enter hotel ID");
        hotel.setId(scanner.nextLong());
        scanner.nextLine();         // int gibi alt satıra geçmez elle alt satıra geçirmesini sağladık

        System.out.println("Enter hotel name");
        hotel.setName(scanner.nextLine());

        System.out.println("Enter hotel location");
        hotel.setLocation(scanner.nextLine());

        hotelRepository.save(hotel);

        System.out.println("hotel ID = " + hotel.getId());;
    }

    public Hotel findHotelById(Long id){

        Hotel foundHotel=hotelRepository.findById(id);
        try {
            if(foundHotel!=null) {
                System.out.println("----------------------------------------------");
                System.out.println(foundHotel);
                System.out.println("----------------------------------------------");
                return foundHotel;
            }
            else {
                throw new HotelNotFoundException("Hotel Nor Found By Id : "+id);
            }
        } catch (HotelNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //3-c : tüm otelleri listeleme
    public void getAllHotels(){
        List<Hotel> allHotels=hotelRepository.findAll();
        if (!allHotels.isEmpty()){
            System.out.println("----------------------ALL HOTELS-------------------------");
            for(Hotel h:allHotels){
                System.out.println(h);
            }
            System.out.println("----------------------------------------------------------");
        } else{
            System.out.println("Hotel list is empty");
        }
    }

    //7-b
    public void deleteHotel(Long hotelId) {
        Hotel foundHotel=findHotelById(hotelId);

        if(foundHotel!=null){
            System.out.println(foundHotel);
            System.out.println("Are you sure to delete? ");
            System.out.println("Please answer Y or N");
            String select=scanner.nextLine();
            if(select.equalsIgnoreCase("Y")){
                hotelRepository.delete(foundHotel);
                System.out.println("Hotel is deleted SUCCESSFULLY");
            } else {
                System.out.println("Delete operation is CANCELLED!");
            }
        }
    }

    public void updateHotelById(Long updatedHotelId) {
        Hotel existingHotel=findHotelById(updatedHotelId);

        if (existingHotel!=null){
            System.out.println("Enter the new hotel name");
            existingHotel.setName(scanner.nextLine());
            System.out.println("Enter the new location");
            existingHotel.setLocation(scanner.nextLine());

            hotelRepository.updateHotel(existingHotel);
            System.out.println("Hotel is updated");
        }
    }
}
