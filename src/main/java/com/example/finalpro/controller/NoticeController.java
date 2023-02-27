package com.example.finalpro.controller;

import com.example.finalpro.db.DBManager;
import com.example.finalpro.service.NoticeService;
import com.example.finalpro.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileOutputStream;

@Controller
@Setter
public class NoticeController {
    @Autowired
    private NoticeService ns;

    @GetMapping("/notice/list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("list",ns.findAll());
        return mav;
    }

    @GetMapping("/notice/detail/{notice_no}")
    public ModelAndView detail(@PathVariable int notice_no){
        ModelAndView mav=new ModelAndView("/notice/detail");
        mav.addObject("n",ns.findById(notice_no).get());
        return mav;
    }

    @GetMapping("/admin/notice/insert")
    public void insertForm(){}

    @PostMapping("/admin/notice/insert")
    public ModelAndView insertSubmit(NoticeVO n, HttpServletRequest request){
        ModelAndView mav=new ModelAndView("redirect:/notice/list");

        //파일 업로드
        String path=request.getServletContext().getRealPath("notice_files");
        MultipartFile uploadFile=n.getUploadFile();
        String fname=uploadFile.getOriginalFilename();
        n.setNotice_fname(fname);

        int re=DBManager.insertNotice(n);
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
}
