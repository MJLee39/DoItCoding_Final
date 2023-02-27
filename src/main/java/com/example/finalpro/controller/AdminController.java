package com.example.finalpro.controller;

import com.example.finalpro.dao.CustomerDAO;
import com.example.finalpro.dao.ReviewDAO;
import com.example.finalpro.dao.TicketDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Customer;
import com.example.finalpro.entity.Ticket;
import com.example.finalpro.function.page.Paging;
import com.example.finalpro.service.CustomerService;
import com.example.finalpro.service.TicketService;
import com.example.finalpro.vo.CustomerVO;
import com.example.finalpro.vo.QnaVO;
import com.example.finalpro.vo.TicketVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Setter
public class AdminController {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CustomerService customerService;


    // 페이징 처리를 위한 변수 선언 (ticket)
    int totalTicketRecord = 0; // ticket의 총 레코드 숫자
    int pageSizeTicket =2; // ticket 목록에서 한 페이지에서 몇개의 record를 출력할지
    int totalPageTicket = 0; // ticket 목록에서 페이지의 총 숫자
    

    // 관리자 페이지에서 listTicket 페이지를 열기
    @GetMapping("/admin/listTicket")
    public ModelAndView adminListTicket(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword){
        ModelAndView mav = new ModelAndView("/admin/ticket/listTicket");
        
        // 페이징 처리
            // int page : 현재 페이지
            // int totalRecord : 총 ticket 숫자
            // int startRecord : 현재 page에서 출력되는 record의 시작 rownum
            // int endRecord : 현재 page에서 출력되는 record의 끝 rownum
            // int startPage : '이전'을 누르기 전에 출력되는 가장 작은 페이지 버튼 숫자
            // int endPage : '다음'을 누르기 전에 출력되는 가장 큰 페이지 버튼 숫자
            int totalRecord = DBManager.getTotalRecord(keyword);
            Paging paging = new Paging(totalRecord, page);
            int startRecord = paging.getStartRecord();
            int endRecord = paging.getEndRecord();
            int startPage = paging.getStartPage();
            int endPage = paging.getEndPage();

        // 검색기능
        System.out.println("keyword :"+keyword);

            //상태유지하기
        session.setAttribute("keyword", keyword);
        if(session.getAttribute("keyword") == null){
            session.setAttribute("keyword", "");
        }
        String keyword_session = (String) session.getAttribute("keyword");
        
        mav.addObject("paging", paging);
        mav.addObject("keyword", keyword_session);
        mav.addObject("list", DBManager.findTicketPagingSearch(startRecord, endRecord, keyword_session));

        return mav;
    }

    // admin 에서 insertTicket 페이지를 열고 form을 보내기
    @GetMapping("/admin/insertTicket")
    public ModelAndView adminInsertTicket(){
        ModelAndView mav = new ModelAndView("/admin/ticket/insertTicket");
        return mav;
    }

    @PostMapping(value = {"/admin/insertTicket"})
    public ModelAndView adminInsertTicketSubmit(TicketVO ticket, String ticket_date2) throws ParseException {
        ModelAndView mav = new ModelAndView("redirect:/admin/listTicket");

        System.out.println("여기 도착!!!!!");
        System.out.println("등록하는 ticket "+ticket);
        
        // html에서 받은 날짜 + 시간을 합치고 Date 형태로 변환하여 db에 넣기
        String ticket_date = ticket.getTicket_date();
        ticket_date = ticket_date + ticket_date2;
       ticket.setTicket_date(ticket_date);
        System.out.println(ticket_date);

        ticketService.insertTicket(ticket);

        return mav;
    }

    // admin에서 updateTicket 기능하기
    @GetMapping("/admin/updateTicket/{ticketid}")
    public ModelAndView adminUpdateTicket(@PathVariable int ticketid){
        ModelAndView mav = new ModelAndView("/admin/ticket/updateTicket");

    if(ticketDAO.findById(ticketid).isPresent()){
        Ticket ticket = ticketDAO.findById(ticketid).get();
        mav.addObject("ticket", ticket);
        
        // view에서 ticket_date를 날짜와 시간값을 한번에 보낼 수 없으므로 두 개를 잘라서 따로따로 보내기 위한 작업
        // ticket_date1 2023-03-04
        // ticket_date2 13:15:00
        // 형태로 각각 보내서 수정할 때 일일히 값을 입력하지 않아도 날짜 데이터가 자동으로 나오게 상태유지한다
        String ticket_date = ticket.getTicket_date();
        String[] list_ticket_date = ticket_date.split("\\s");
        String ticket_date1 = list_ticket_date[0];
        String ticket_date2 = list_ticket_date[1];

        mav.addObject("ticket_date1", ticket_date1);
        mav.addObject("ticket_date2", ticket_date2);

    }
        return mav;
    }

    @PostMapping(value = {"/admin/updateTicket"})
    public ModelAndView adminUpdateTicketSubmit(TicketVO ticket, String ticket_date2){
        ModelAndView mav = new ModelAndView("redirect:/admin/listTicket");

        System.out.println("등록하는 ticket "+ticket);

        // html에서 받은 날짜 + 시간을 합쳐서 db에 넣기
        String ticket_date = ticket.getTicket_date();
        ticket_date = ticket_date + ticket_date2;
        ticket.setTicket_date(ticket_date);
        System.out.println(ticket_date);

        ticketService.updateTicket(ticket);

        return mav;
    }

    // ticket 삭제하기
    @RequestMapping("/admin/deleteTicket/{ticketid}")
    @ResponseBody
    public ModelAndView adminDeleteTicket(@PathVariable int ticketid){
        ModelAndView mav = new ModelAndView("redirect:/admin/listTicket");
        ticketDAO.deleteById(ticketid);
        
        return mav;
    }

    // 관리자 페이지에서 customer의 list 출력
    @GetMapping("/admin/listCustomer")
    public ModelAndView adminListCustomer(@RequestParam(defaultValue = "1") int page, HttpSession session, @RequestParam(required = false) String keyword){
        ModelAndView mav = new ModelAndView("/admin/customer/list");

        // 페이징 처리
        // int page : 현재 페이지
        // int totalRecord : 총 ticket 숫자
        // int startRecord : 현재 page에서 출력되는 record의 시작 rownum
        // int endRecord : 현재 page에서 출력되는 record의 끝 rownum
        // int startPage : '이전'을 누르기 전에 출력되는 가장 작은 페이지 버튼 숫자
        // int endPage : '다음'을 누르기 전에 출력되는 가장 큰 페이지 버튼 숫자
        int totalRecord = DBManager.getTotalCustomerRecord(keyword);
        Paging paging = new Paging(totalRecord, page);
        int startRecord = paging.getStartRecord();
        int endRecord = paging.getEndRecord();
        int startPage = paging.getStartPage();
        int endPage = paging.getEndPage();

        // 검색기능
            System.out.println("keyword :"+keyword);
        //상태유지하기
        session.setAttribute("keyword", keyword);
        if(session.getAttribute("keyword") == null){
            session.setAttribute("keyword", "");
        }
        String keyword_session = (String) session.getAttribute("keyword");

        mav.addObject("customerList", DBManager.findCustomerPagingSearch(startRecord, endRecord, keyword_session));
        mav.addObject("keyword", keyword_session);
        mav.addObject("paging", paging);
        return mav;
    }

    // 관리자 페이지 listCustomer에서 회원 아이디를 누르면 정보 수정 페이지로
    @GetMapping("/admin/updateCustomer/{custid}")
    public ModelAndView adminUpdateCustomer(@PathVariable String custid){
        ModelAndView mav = new ModelAndView("/admin/customer/update");
        if(customerService.findCustomerByCustid(custid).isPresent()){
            Customer customer = customerService.findCustomerByCustid(custid).get();

            // 생년월일을 yyyy-mm-dd 형태로만 나타내기 (공백을 기준으로 문자열을 잘라서 뒤에 00:00:00 없애기)
            String birth = customer.getBirth();
            String[] list_birth = birth.split("\\s");
            birth = list_birth[0];
            mav.addObject("customer", customer);
            mav.addObject("birth", birth);
        }
        return mav;
    }

    // customer 정보 수정
    @PostMapping("/admin/updateCustomer")
    public ModelAndView adminUpdateCustomerSubmit(CustomerVO customer){
        ModelAndView mav = new ModelAndView("redirect:/admin/listCustomer");
        System.out.println("등록하는 customer "+customer);
        customerService.updateCustomer(customer);
        return mav;
    }
    
    // customer 삭제
    @RequestMapping("/admin/deleteCustomer/{custid}")
    @ResponseBody
    public ModelAndView adminDeleteCustomer(@PathVariable String custid){
        ModelAndView mav = new ModelAndView("redirect:/admin/listCustomer");
        customerService.deleteCustomer(custid);
        return mav;
    }

    // custid 별로 작성한 qna 목록 출력하기
    @GetMapping("/admin/listQnaByCustid/{custid}")
    public ModelAndView adminListQnaByCustid(@PathVariable String custid){
        ModelAndView mav = new ModelAndView("/admin/customer/listQna");
        List<QnaVO> list = DBManager.listQnaByCustid(custid);
        mav.addObject("listQna", list);
        return mav;
    }

    // custid 별로 예매한 ticket 내역 출력하기
    @GetMapping("/admin/listTicketByCustid/{custid}")
    public ModelAndView adminListTicketByCustid(@PathVariable String custid){
        ModelAndView mav = new ModelAndView("/admin/customer/listTicket");
        // 아이디로 유저가 예매한 티켓 VO 목록 가져오기
        List<Integer> ticketidList=DBManager.findTicketidByCustid(custid);
        List<Ticket> ticketVOList=new ArrayList<Ticket>();
        for(int ticketid:ticketidList){
            Optional<Ticket> optionalTicket=ticketService.findByTicketid(ticketid);
            if(optionalTicket.isPresent()) {
                ticketVOList.add(optionalTicket.get());
            }
        }
        mav.addObject("ticketVOList",ticketVOList);

        return mav;
    }

}
