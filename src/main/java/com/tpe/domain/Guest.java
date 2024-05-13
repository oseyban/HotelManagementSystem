package com.tpe.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_guest")
public class Guest {

    //todo : auto generated
    @Id//pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "guest",orphanRemoval = true)//tablo ile ilişkiyi kurar:gerek yok
    private List<Reservation> reservations=new ArrayList<>();

    @Embedded           //addresi ekledik buraya
    private Address address;

    @PrePersist                 //kalıcı hale getirmeden hemen önce bu işlemi yap
    public void prePersist(){   //createDate işlemimi set ettik...
        this.createDate=LocalDateTime.now();
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

//    public void setCreateDate(LocalDateTime createDate) {
//        this.createDate = createDate;
//    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", address=" + address +
                '}';
    }
}