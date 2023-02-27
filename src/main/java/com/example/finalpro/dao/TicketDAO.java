package com.example.finalpro.dao;

import com.example.finalpro.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TicketDAO extends JpaRepository<Ticket, Integer> {


//    @Transactional
//    @Modifying
//    @Query(value = "insert into ticket t(ticketid, cateid, placeid, ticket_name, price, ticket_date, content, img_fname, img_fname_main, vid_url, min_age, runtime, cast, loc, lat, lng)" +
//            " values(:#{#t.ticketid}, :#{#t.cateid}, :#{#t.ticket_name}, :#{#t.price}, TO_DATE(:#{#t.ticket_date},'YYYY-MM-DD HH:mm:ss'), :#{#t.content}, :#{#t.img_fname}, :#{#t.img_fname_main}, :#{#t.vid_url}, :#{#t.min_age}, :#{#t.runtime}, :#{#t.cast}, :#{#t.loc}, :#{#t.lat}, :#{#t.lng})", nativeQuery = true)
//    public void insertTicket(Ticket ticket);

}
