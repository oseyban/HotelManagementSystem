package com.tpe.repository;

import com.tpe.config.HibernateUtils;
import com.tpe.domain.Hotel;
import com.tpe.domain.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoomRepository {

    //ÖDEV save, findByID, findAll yapılacak. Hotel Repository de örneği var...

    private Session session;

    public void save(Room room) {
        //1b repository de hotel bağlantı işlemi
        try {
            session = HibernateUtils.getSessionFactory().openSession();   //bir oturum oluşturduk

            Transaction transaction = session.beginTransaction(); //kaydetme işlemini bir transaction içine alıyoruz ki hata olursa işlemi hiç yapmamış olsun...
            session.save(room);    //insert into hotel
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);   //her durumda oturumu kapatıyoruz...
        }
    }

    //2-b
    public Room findById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_room where id=pId
            session.get(Room.class, id);   //Room room = session.get(Room.class,id); şeklinde kullanacaksak return room; yazmalıydık

            return session.get(Room.class,id);

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    //3-b
    public List<Room> findAll(){
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_hotel
            List<Room> roomList = session.createQuery("FROM Room",Room.class).getResultList();  //parametre olarak HQL alır SQL değil : SELECT yazmaya gerek yok yani ama class ismi yazılmalı
            return roomList;

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }
}
