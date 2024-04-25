package com.tpe.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_hotel")
public class Hotel {

    @Id//primary key
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    //orphanRemoval
    //A otelinin odaları: 11,12,13
    //A otelinin oda listesinden 11 i çıkarırsam:room tabledan 11 i siler

    //cascade:
    //A otelinin odaları: 11,12,13
    //A otelinin oda listesinden 11 i çıkarırsam:room tableda 11 hala var


    @OneToMany(mappedBy = "hotel",cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)//hotel ile room arasında ilişki kurulmasını sağlar: ilişki tablosu ekler. Fetch Type OneToMany lerde Lazy dir.
    private List<Room> rooms=new ArrayList<>();

    //hibernate data çekerken(fetch) default constructorı kullanır.
    public Hotel() {
    }

    //param const

    public Hotel(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }


    //getter-setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}