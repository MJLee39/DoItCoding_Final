package com.example.finalpro;

import com.example.finalpro.controller.DrawController;
import com.example.finalpro.dao.TicketDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Ticket;
import com.example.finalpro.vo.TicketVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@Setter
public class ScheduledDraw {

    static int day;
    static Date date = new Date();
    @Autowired
    private DrawController drawController;

    @Autowired
    private TicketDAO ticketDAO;

    //매일 오후 12시에 다음날이 상영일인 상품에 대해 드로우 진행
    @Scheduled(cron = "0 27 13 * * ?")
    public void draw(){
        List<TicketVO> tickets = DBManager.selectTicketWhichDraw();
        int toYear = date.getYear()+1900;
        int toMonth = date.getMonth()+1;
        int toDay = date.getDate();
        for(TicketVO t : tickets){

            String date = t.getTicket_date().substring(0,10);       //년 월 일 까지만 잘라준다.
            String arr[] = date.split("-");                 // 년 월 일 기준으로 잘라 배열에 저장.

            // 현재 년 월이 일치하고 상영일이 today의 다음 날일때 draw 실행.
            if(toYear == Integer.parseInt(arr[0]) && toMonth == Integer.parseInt(arr[1]) && toDay == Integer.parseInt(arr[2])-1){
                System.out.println("draw실행:"+ t.getTicketid());
                drawController.drawExec(t.getTicketid());
            }
        }
    }

}
