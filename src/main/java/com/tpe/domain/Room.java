package com.tpe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="t_room")
public class Room {

    @Id
    private Long id;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private int capasity;

    //todo : many-to-one
    private Hotel hotel;        //oda hangi otele ait

    //todo : one-to-many
    private List<Reservation> reservations=new ArrayList<>();

    public Room(Long id, String number, int capasity, Hotel hotel) {
        this.id = id;
        this.number = number;
        this.capasity = capasity;
        this.hotel = hotel;
    }

    public Room() { //parametreli consructer oluşturduysak parametresiz de oluşturulmalı
    }

    //getter-setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapasity() {
        return capasity;
    }

    public void setCapasity(int capasity) {
        this.capasity = capasity;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    //toString override


    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", capasity=" + capasity +
                //", hotel=" + hotel +  otelde de room yazdırıldığı için sonsuz döngüye girer bu sebeple burada hotel yazdırmıyoruz
                ", reservations=" + reservations +
                '}';
    }
}
