package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Hotel;
import com.tpe.domain.Reservation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReservationRepository {

    private static Session session;

    public void save(Reservation reservation) {
        //1b repository de reservation bağlantı işlemi
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk
            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...

            session.save(reservation);    //insert into reservation

            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }

    //2-b
    public static Reservation findById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_reservation where id=pId
            session.get(Reservation.class, id);   //Reservation reservation = session.get(Reservation.class,id); şeklinde kullanacaksak return hotel; yazmalıydık

            return session.get(Reservation.class,id);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    //3-b
    public List<Reservation> findAll(){
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_reservation
            List<Reservation> reservationList = session.createQuery("FROM Reservation",Reservation.class).getResultList();  //parametre olarak HQL alır SQL değil : SELECT yazmaya gerek yok yani ama class ismi yazılmalı
            return reservationList;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    public void delete(Reservation reservation) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk
            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...

            session.delete(reservation);    //insert into t_reservation values
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }
}
