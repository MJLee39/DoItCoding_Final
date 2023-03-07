package com.example.finalpro.controller;

import com.example.finalpro.dao.CustomerDAO;
import com.example.finalpro.dao.ReviewDAO;
import com.example.finalpro.dao.TicketDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.service.ReviewService;
import com.example.finalpro.vo.ReviewVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Setter
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private TicketDAO ticketDAO;

    // 특정 티켓의 리뷰 출력, 정렬
    @RequestMapping("/ReviewByTicketid")
    @ResponseBody
    public List<ReviewVO> findReviewByTicketid(int ticketid, int re){
        System.out.println("ticketid-review:"+ticketid);
        System.out.println("re-review:"+re);
        return DBManager.findReviewByTicketid(ticketid,re);
    }

    //ticketid별로 전체 리뷰 출력
    @GetMapping("/CheckReview")
    @ResponseBody
    public List<ReviewVO> CheckReview(@RequestParam int ticketid){
        return DBManager.checkReview(ticketid);
    }

//    // 사용자의 리뷰 출력
//    @RequestMapping("/ReviewByTicketidAndCustid")
//    @ResponseBody
//    public List<MyReviewVO> findReviewByTicketAndCust(String custid, int ticketid){
//        return DBManager.findReviewByTicketAndCust(custid,ticketid);
//    }

    // 티켓의 평균 별점 구하기
    @RequestMapping("/AvgScore")
    @ResponseBody
    public int findAvgScore(int ticketid){
        return DBManager.findAvgScore(ticketid);
    }
}
