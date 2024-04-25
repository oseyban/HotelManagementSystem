package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

//Room-Guest Reservation için service ve repo claslarını oluşturalım : ödev
public class HotelRepository {

    private Session session;

    public void save(Hotel hotel) {
        //1b repository de hotel bağlantı işlemi
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk

            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...
            session.save(hotel);    //insert into hotel
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }

    //2-b
    public Hotel findById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_hotel where id=pId
            session.get(Hotel.class, id);   //Hotel hotel = session.get(Hotel.class,id); şeklinde kullanacaksak return hotel; yazmalıydık

            return session.get(Hotel.class,id);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    //3-b
    public List<Hotel> findAll(){
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_hotel
            List<Hotel> hotelList = session.createQuery("FROM Hotel",Hotel.class).getResultList();  //parametre olarak HQL alır SQL değil : SELECT yazmaya gerek yok yani ama class ismi yazılmalı
            return hotelList;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    //7-c
    public void delete(Hotel hotel) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk
            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...

            session.delete(hotel);    //insert into hotel
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }

    //8-c todo
    public void updateHotel(Hotel existingHotel) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk
            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...

            session.update(existingHotel);

            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }
}
