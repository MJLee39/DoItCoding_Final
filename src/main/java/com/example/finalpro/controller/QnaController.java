package com.example.finalpro.controller;

import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Qna;
import com.example.finalpro.entity.Ticket;
import com.example.finalpro.service.QnaService;
import com.example.finalpro.service.TicketService;
import com.example.finalpro.vo.QnaVO;
import com.example.finalpro.vo.TicketVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Setter
public class QnaController {
    @Autowired
    private QnaService qs;

    @Autowired
    private TicketService ts;

    @GetMapping("/qna/list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("list", qs.findAll());
        return mav;
    }

    @GetMapping("/qna/detail/{qna_no}")
    public ModelAndView detail(@PathVariable int qna_no, HttpSession session){
        ModelAndView mav=new ModelAndView("/qna/detail");
        Optional<Qna> optionalQna=qs.findById(qna_no);
        if(optionalQna.isPresent()){
            Qna q =optionalQna.get();
            String custidByQna_no=q.getCustomer().getCustid();
            String custidInSession=(String)session.getAttribute("id");
            if(!custidInSession.equals(custidByQna_no) && !custidInSession.equals("admin") && q.getQna_open().equals("n")){
                mav.addObject("msg","비공개 글입니다.");
                mav.setViewName("/error");
            }else {
                mav.addObject("q",q);
            }
        }else{
            mav.addObject("msg","삭제된 글이거나 잘못된 접근입니다.");
            mav.setViewName("/error");
        }


//          다른 방법 (이게 더 복잡한 듯)
//        //비공개 글일 경우
//        if(qna_open.equals("n")){
//            //작성자가 맞는지 확인
//            QnaVO q=new QnaVO();
//            q.setQna_no(qna_no);
//            q.setCustid((String) session.getAttribute("id"));
//
//            // 작성자가 아니면
//            if(DBManager.checkWriter(q)!=1){
//                mav.addObject("msg","비공개 글입니다.");
//                mav.setViewName("/error");
//                //작성자면
//            }else{
//                mav.addObject("q",qs.findById(qna_no).get());
//            }
//        }else{
//            mav.addObject("q",qs.findById(qna_no).get());
//        }
        return mav;
    }

    @GetMapping("/qna/insert")
    public ModelAndView insertForm(HttpSession session){
        ModelAndView mav=new ModelAndView();
        
        // 세션에 저장된 아이디로 유저가 예매한 티켓 VO 목록 가져오기
        String loginId=(String)session.getAttribute("id");
        List<Integer> ticketidList=DBManager.findTicketidByCustid(loginId);
        List<Ticket> ticketVOList=new ArrayList<Ticket>();
        for(int ticketid:ticketidList){
            Optional<Ticket> optionalTicket=ts.findByTicketid(ticketid);
            if(optionalTicket.isPresent()) {
                ticketVOList.add(optionalTicket.get());
            }
        }
        mav.addObject("ticketVOList",ticketVOList);
        return mav;
    }

    @PostMapping("/qna/insert")
    public ModelAndView insertSubmit(QnaVO q, HttpServletRequest request){
        ModelAndView mav=new ModelAndView("redirect:/qna/list");

        if(q.getQna_answer()==null){
            q.setQna_answer("");
        }

        //파일 업로드
        String path=request.getServletContext().getRealPath("qna_files");
        MultipartFile uploadFile=q.getUploadFile();
        String fname=uploadFile.getOriginalFilename();
        q.setQna_fname(fname);

        int re= DBManager.insertQna(q);

        if(re!=1) {
            //실패하면
            mav.setViewName("/error");
        }else{
            //성공하면
            if(fname != null && !fname.equals("")) {
                //fname이 있으면 (= 파일 업로드 했으면)
                try {
                    FileOutputStream fos = new FileOutputStream(path + "/" + fname);
                    FileCopyUtils.copy(uploadFile.getBytes(), fos);
                    fos.close();
                }catch (Exception e) {
                    System.out.println("예외발생:"+e.getMessage());
                }
            }
        }
        return mav;
    }

    @GetMapping("/qna/update/{qna_no}")
    public ModelAndView updateForm(@PathVariable int qna_no, HttpSession session){
        ModelAndView mav=new ModelAndView("/qna/update");
        Optional<Qna> optionalQna=qs.findById(qna_no);
        if(optionalQna.isPresent()) {
            mav.addObject("q",optionalQna.get());

            // 세션에 저장된 아이디로 유저가 예매한 티켓 VO 목록 가져오기
            String loginId = (String) session.getAttribute("id");
            List<Integer> ticketidList = DBManager.findTicketidByCustid(loginId);
            List<Ticket> ticketVOList = new ArrayList<Ticket>();
            for (int ticketid : ticketidList) {
                Optional<Ticket> optionalTicket=ts.findByTicketid(ticketid);
                if(optionalTicket.isPresent()) {
                    ticketVOList.add(optionalTicket.get());
                }
            }
            mav.addObject("ticketVOList", ticketVOList);
        }else{
            mav.addObject("msg","잘못된 접근입니다.");
            mav.setViewName("/error");
        }
        return mav;
    }

    @PostMapping("/qna/update")
    public ModelAndView updateSubmit(QnaVO qnaVO, HttpServletRequest request){
        ModelAndView mav=new ModelAndView("redirect:/qna/detail/"+qnaVO.getQna_no());

        if(qnaVO.getQna_answer()==null){
            qnaVO.setQna_answer("");
        }

        //파일 업로드
        String path=request.getServletContext().getRealPath("qna_files");
        MultipartFile uploadFile=qnaVO.getUploadFile();
        String oldFname=qnaVO.getQna_fname();
        String newFname=uploadFile.getOriginalFilename();

        //          Fname 처리:
        //         새로운 파일이 있으면 fname을 set
        //         새로운 파일 없고 예전 파일 있으면 그대로
        //         새로운 파일 없고 예전 파일 없으면 ""을 set

        //새로운 파일이 있으면 저장한다. 이 때 예전 파일이 있으면 지운다.
        //새로운 파일이 있으면
        if(newFname != null && !newFname.equals("")) {
            //새로운 파일을 저장한다.
            try {
                FileOutputStream fos = new FileOutputStream(path + "/" + newFname);
                FileCopyUtils.copy(uploadFile.getBytes(), fos);
                fos.close();
            }catch (Exception e) {
                System.out.println("예외발생:"+e.getMessage());
            }
            //새로운 파일 이름을 set
            qnaVO.setQna_fname(newFname);
            //예전 파일이 있으면
            if(oldFname!=null && !oldFname.equals("")){
                //예전 파일을 지운다.
                File file=new File(path+"/"+oldFname);
                file.delete();
            }
        }else{
            if(oldFname==null){
                qnaVO.setQna_fname("");
            }
        }

        DBManager.updateQna(qnaVO);
        return mav;
    }

    //답글 등록 Ajax
    @ResponseBody
    @GetMapping("/qna/answer/update")
    public int updateAnswer(int qna_no, String qna_answer){
        QnaVO q=new QnaVO();
        q.setQna_no(qna_no);
        q.setQna_answer(qna_answer);
        return DBManager.updateAnswer(q);
    }

    //답글 삭제 Ajax
    @ResponseBody
    @GetMapping("/qna/answer/delete")
    public int deleteAnswer(int qna_no){
        return DBManager.deleteAnswer(qna_no);
    }


}
