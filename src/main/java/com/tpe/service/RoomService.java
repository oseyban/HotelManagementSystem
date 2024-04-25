package com.tpe.service;


import com.tpe.domain.Hotel;
import com.tpe.domain.Room;
import com.tpe.exceptions.RoomNotFoundException;
import com.tpe.repository.RoomRepository;

import java.util.List;
import java.util.Scanner;

//serviceler servicelerle veya kendi repoları ile iletişime geçer
public class RoomService {

    private Scanner scanner=new Scanner(System.in);

    private final HotelService hotelService;

    private final RoomRepository roomRepository;

    //param const
    public RoomService(HotelService hotelService, RoomRepository roomRepository) {
        this.hotelService = hotelService;
        this.roomRepository = roomRepository;
    }

    //4-b
    public void saveRoom() {

        Room room=new Room();

        System.out.println("Enter room ID : ");
        room.setId(scanner.nextLong());
        scanner.nextLine();

        System.out.println("Enter room number : ");
        room.setNumber(scanner.nextLine());

        System.out.println("Enter room capacity : ");
        room.setCapacity(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Enter hotel ID : ");
        Long hotelId=scanner.nextLong();
        scanner.nextLine();

        //girilen id hangi otele ait
        Hotel foundHotel =hotelService.findHotelById(hotelId);

        if (foundHotel!=null) {

            room.setHotel(foundHotel);//oda hangi otele aitse set edildi.
            //t_room tablosunda hotel_id sütununa bulunan otelin sadece idsini ekler

//        //bu odayı otelin oda listesine ekleyelim
//        foundHotel.getRooms().add(room);  ---> mappedBy bu işlemi bizim yerimize yapıyor.

            roomRepository.save(room);//tabloya eklendi

            System.out.println("Room is saved successfully. Room id : " + room.getId());
        }else {
            System.out.println("Room could not saved!!!");
        }
    }

    //5-b : Id si verilen odayı tablodan bulup yazdırma ve geri döndürme:ÖDEV
    public Room findRoomById(Long roomId) {
        try {
            Room foundRoom = roomRepository.findById(roomId);
            if (foundRoom != null) {
                System.out.println("---------------------------------");
                System.out.println(foundRoom);
                System.out.println("---------------------------------");
                return foundRoom;
            } else {
                throw new RoomNotFoundException(" Room  not found with ID: " + roomId);
            }
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //6-b: eğer tablo boş değilse tüm odaları listeleme:ÖDEV
    public void getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            for (Room room : rooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("No rooms found!");
        }
    }

    //ödev1
    public void deleteRoomById(Long id) {
        Room existingRoom =findRoomById(id);
        if (existingRoom != null) {
            roomRepository.delete(existingRoom);
            System.out.println("Room  deleted successfully. ID: " + id);
        }


    }


}