package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Guest;
import com.tpe.domain.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GuestRepository {

    private Session session;

    public void save(Guest guest) {
        //1b repository de guest bağlantı işlemi
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk

            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...
            session.save(guest);    //insert into guest
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }

    //2-b
    public Guest findById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_hotel where id=pId
            session.get(Guest.class, id);   //Hotel hotel = session.get(Hotel.class,id); şeklinde kullanacaksak return hotel; yazmalıydık

            return session.get(Guest.class,id);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    //3-b
    public List<Guest> findAll(){
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_hotel
            List<Guest> guestList = session.createQuery("FROM Guest",Guest.class).getResultList();  //parametre olarak HQL alır SQL değil : SELECT yazmaya gerek yok yani ama class ismi yazılmalı
            return guestList;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }
    public void delete(Guest guest) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk
            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...

            session.delete(guest);    //insert into hotel

            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }
}
