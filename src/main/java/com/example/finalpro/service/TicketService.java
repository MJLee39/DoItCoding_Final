package com.example.finalpro.service;

import com.example.finalpro.dao.TicketDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Ticket;
import com.example.finalpro.vo.TicketVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@Setter
public class TicketService {
    @Autowired
    private TicketDAO dao;

    public List<Ticket> findAll(){return dao.findAll();}

    // 관리자 페이지에서 ticket을 넣기
    public void insertTicket(TicketVO ticket){
        // 파일 업로드하기
        // System.getPropery("user.dir") 을 쓰면 프로젝트 경로를 지정해줍니다 (본 프로젝트의 경우 "DoitCoding_Final" 이 기본값)
        String path = System.getProperty("user.dir") + "/src/main/resources/static/images/ticket";
        String path_main = System.getProperty("user.dir") + "/src/main/resources/static/images/ticket_main";
        System.out.println("등록하는 path "+path);
        String fname = "";
        String fname_main = "";
        MultipartFile uploadFile = ticket.getUploadFile();
        MultipartFile uploadFile_main = ticket.getUploadFile_main();
        fname = uploadFile.getOriginalFilename();
        fname_main = uploadFile_main.getOriginalFilename();

        if(fname != null && !fname.equals("")){
            try{
                byte []data = uploadFile.getBytes();
                FileOutputStream fos = new FileOutputStream(path + "/" + fname);
                fos.write(data);
                fos.close();
            }catch(Exception e){
                System.out.println("에러"+e.getMessage());
            }
        }else{
            fname = "";
        }

        if(fname_main != null && !fname_main.equals("")){
            try{
                byte []data = uploadFile_main.getBytes();
                FileOutputStream fos = new FileOutputStream(path_main + "/" + fname_main);
                fos.write(data);
                fos.close();
            }catch(Exception e){
                System.out.println("에러"+e.getMessage());
            }
        }else{
            fname_main = "";
        }

        ticket.setImg_fname(fname);
        ticket.setImg_fname_main(fname_main);

        DBManager.insertTicket(ticket);
    }

    // 관리자 페이지에서 ticket update 하는 기능
    public void updateTicket(TicketVO ticket){
        // 파일 수정하면 적용되게 하기 (수정 안하면 그대로 가게)
        String path = System.getProperty("user.dir") + "/src/main/resources/static/images/ticket";
        String path_main = System.getProperty("user.dir") + "/src/main/resources/static/images/ticket_main";
        String fname = "";
        String fname_main = "";
        String oldfname = ticket.getImg_fname();
        String oldfname_main = ticket.getImg_fname_main();

        MultipartFile uploadFile = ticket.getUploadFile();
        MultipartFile uploadFile_main = ticket.getUploadFile_main();
        fname = uploadFile.getOriginalFilename();
        fname_main = uploadFile_main.getOriginalFilename();

        // uploadFile이 있다면
        if(fname != null && !fname.equals("")){
            try{
                byte []data = uploadFile.getBytes();
                FileOutputStream fos = new FileOutputStream(path + "/" + fname);
                fos.write(data);
                fos.close();

                ticket.setImg_fname(fname);

                // 수정한 게 있으면 기존 파일 삭제하기
                File file = new File(path + "/" + oldfname);
                file.delete();
            }catch(Exception e){
                System.out.println("에러"+e.getMessage());
            }
        }else{
            ticket.setImg_fname(oldfname);
        }

        if(fname_main != null && !fname_main.equals("")){
            try{
                byte []data = uploadFile_main.getBytes();
                FileOutputStream fos = new FileOutputStream(path_main + "/" + fname_main);
                fos.write(data);
                fos.close();

                ticket.setImg_fname_main(fname_main);
                // 수정한 게 있으면 기존 파일 삭제하기
                File file = new File(path + "/" + oldfname_main);
                file.delete();
            }catch(Exception e){
                System.out.println("에러"+e.getMessage());
            }
        }else{
            ticket.setImg_fname_main(oldfname_main);
        }

        DBManager.updateTicket(ticket);
    }


    public Optional<Ticket> findByTicketid(int ticketid){return dao.findById(ticketid);}
}
