package com.example.finalpro.controller;

import com.example.finalpro.dao.CustomerDAO;
import com.example.finalpro.dao.ReviewDAO;
import com.example.finalpro.dao.TicketDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.vo.RankingVO;
import com.example.finalpro.vo.TicketVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Setter
public class TicketController {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private TicketDAO ticketDAO;

    @GetMapping("/main")
    public ModelAndView main(){
        ModelAndView mav = new ModelAndView("/ticket/main");
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView search(){
        ModelAndView mav = new ModelAndView("/ticket/search");
        return mav;
    }

    @GetMapping("/category")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("/ticket/category");
        return mav;
    }

    // 랭킹 출력 ajax 통신 받기
    @RequestMapping(value = "/RankingTicket", method = RequestMethod.GET)
    @ResponseBody
    public List<RankingVO> rankingTicket(int cateid){
        System.out.println("cateid "+cateid);
        return DBManager.findAllRankingOrderByScore(cateid);
    }


   // 시간, 장르 별로 Ajax 출력하기
    @RequestMapping("/SelectTicketMain")
    @ResponseBody
    public List<TicketVO> selectTicket(int cateid, int time){
        System.out.println("cateid "+cateid);
        System.out.println("time "+time);

        return DBManager.findAllTicketByCategory(time,cateid);
    }

    // 검색 결과 ticket들 Ajax 출력하기
    @RequestMapping("/SearchTicket")
    @ResponseBody
    public List<TicketVO> selectSearchTicket(String keyword){
        System.out.println("keyword :"+keyword);

        return DBManager.findSearchTicket(keyword);
    }
    
//    @RequestMapping("/RankingTicket")
//    @ResponseBody
//    public List<Ranking> main(@RequestParam("cateid")int cateid){
//        return rankingDAO.findAllRanking(cateid);
//    }

}
