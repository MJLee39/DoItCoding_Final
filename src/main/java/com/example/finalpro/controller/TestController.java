package com.example.finalpro.controller;

import com.example.finalpro.dao.CustomerDAO;
import com.example.finalpro.entity.Customer;
import com.example.finalpro.service.CustomerService;
import com.example.finalpro.service.CategoryService;
import com.example.finalpro.service.TicketService;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@Setter
public class TestController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerDAO dao;

    @Autowired
    private CategoryService ts;

    @Autowired
    private CustomerService cs;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private TicketService ticketService;

    //public void setDao(CustomerDAO dao){ this.dao = dao; }

    @RequestMapping("/list")
    public void list(Model model){
        model.addAttribute("list", dao.findAll());
    }

    @RequestMapping("/list_jpa")
    public void list_jpa(Model model){
        model.addAttribute("list", ts.findAll());
    }

    @RequestMapping("/list_jpa_id")
    public void list_jpa_id(Model model){
        model.addAttribute("list", ts.findById());
    }

    @RequestMapping("/list_customer")
    public void list_customer(Model model){
        model.addAttribute("list", cs.findAll());
    }

    @RequestMapping("/list_ticket")
    public void list_ticket(Model model){
        model.addAttribute("list", ticketService.findAll());
    }

    @GetMapping("/login")
    public void login(){
    }

    @GetMapping("/loginTest")
    public ModelAndView loginTest(){
        ModelAndView mav = new ModelAndView("/customer/loginTest");
        return mav;
    }

    @GetMapping("/join")
    public void join(){
    }

    @PostMapping("/join")
    public ModelAndView joinSubmit(Customer m) {
//		String encPwd = passwordEncoder.encode(m.getPwd());
//		m.setPwd(encPwd);
        ModelAndView mav = new ModelAndView("redirect:/login");
        m.setPwd(passwordEncoder.encode(m.getPwd()));
        m.setCateid(1);
        m.setEmail("Test1234@gmail.com");
        m.setPhone("010123456");
        m.setGender("a");
        m.setAddr("add");
        m.setRole("customer");
        Date d = new Date();

        m.setBirth("19961225");
        try {
            System.out.println(m);
            customerDAO.save(m);
        }catch (Exception e) {
            mav.addObject("msg", "회원가입에 실패하였습니다.");
            mav.setViewName("error");
        }

		/*
		memberDAO.save(m);
		Optional<Member> obj = memberDAO.findById(m.getId());
		if(obj.isEmpty()) {
			mav.addObject("msg", "회원가입에 실패하였습니다.");
			mav.setViewName("error");
		}*/
        return mav ;
    }

    @GetMapping("/service1")
    public void service1(HttpSession session, Model m) {
        //인증된(로그인한) 회원의 정보를 가져오기 위하여
        //시큐리티의 인증객체가 필요.
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        //이 인증객체를 통해서 인증된(로그인된) User객체를 받아 온다.
        User user = (User)authentication.getPrincipal();

        //이 인증된 User를 통해서 로그인된 회원의 아이디를 가져온다.

        String id = user.getUsername();
        //아이디 정보를 세션에 상태유지 한다.
        //만약, id뿐 아니라 로그인한 회원의 다른정보도 필요하다면 dao를 통해 회원 정보를 가져와서 상태유지

        session.setAttribute("id", id);
        System.out.println("session id = "+session.getAttribute("id"));
        m.addAttribute("id", id);
    }


//    @PostMapping("/login")
//    public ModelAndView loginSubmit(String id, String pwd, HttpSession session){
//        System.out.println("login실행");
//        System.out.println(id + "\n" + pwd);
//        ModelAndView mav = new ModelAndView("redirect:/");
//
//        Optional<Customer> option = customerDAO.findById(id);
//        if(option.isPresent()){
//            String dbPwd = option.get().getPwd();
//            System.out.println(dbPwd);
//            if(pwd.equals(dbPwd)){
//                session.setAttribute("id", id);
//            }else{
//                mav.setViewName("redirect:/login");
//            }
//        }else{
//            mav.addObject("msg", "로그인실패.");
//            mav.setViewName("error");
//        }
//        return mav;
//    }

}
