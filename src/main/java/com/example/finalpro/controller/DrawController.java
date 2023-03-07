package com.example.finalpro.controller;

import com.example.finalpro.dao.DrawDAO;
import com.example.finalpro.db.DBManager;
import com.example.finalpro.service.DrawService;
import com.example.finalpro.vo.DrawVO;
import com.example.finalpro.vo.SeatVO;
import com.example.finalpro.vo.TicketVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Controller
@Setter
public class DrawController {

    private static String lucker[];

    @Autowired
    private DrawService drawService;

    @Autowired
    private DrawDAO drawDAO;

    @RequestMapping ("/DrawInsert")
    @ResponseBody
    public int drawInsert(int ticketid, String custid){
        return DBManager.drawInsert(ticketid,custid);
    }

    @RequestMapping("/selectDrawNoSame")
    @ResponseBody
    public DrawVO selectDrawNoSame(int ticketid, String custid){
        return DBManager.selectDrawNoSame(ticketid,custid);
    }

    // 잔여좌석이 0이거나 cateid=1일 때 드로우 버튼 활성화
    @RequestMapping("/DrawButtonOpen")
    @ResponseBody
    public TicketVO openDrawButton(int ticketid) {
        System.out.println("ticket번호:"+ticketid);
        return DBManager.findByTicketid(ticketid);
    }

    //드로우 신청한 회원정보 가져오기
    @GetMapping("/drawList")
    @ResponseBody
    public List<DrawVO> drawList(int ticketid){
        List<DrawVO> draw = null;
        draw = DBManager.drawTest(ticketid);
        return draw;
    }

    // 결제완료하면 드로우 인스턴스 삭제
    @RequestMapping("/drawDelete")
    @ResponseBody
    public int drawDelete(int drawid){
        return DBManager.drawDelete(drawid);
    }

    public int[] leftSeat(int ticketid){
        List<SeatVO> seat = DBManager.drawLeftSeat(ticketid);      //seatTable에 남은 좌석 정보 가져오기
        int seatArr[] = new int[seat.size()];
        for(int i = 0; i < seat.size(); i++){
            seatArr[i] = seat.get(i).getSeatid();
            System.out.println(seatArr[i]);
        }
        return seatArr;
    }

    //드로우 신청한 티켓 반환
    public List<TicketVO> selectTicketWhichDraw() {
        return DBManager.selectTicketWhichDraw();
    }

    //드로우 가동
    @GetMapping("/drawExec")
    @ResponseBody
    public String drawExec(int ticketid){
        int count = DBManager.findLeftSeatByTicketid(ticketid);  // 그 티켓의 남은 좌석
        List<DrawVO> draw = drawList(ticketid);
        System.out.println("드로우 사이즈:"+draw.size());
        String custidArr[] = new String[count];           //드로우에 당첨된 회원아이디 저장
        Random r = new Random();

        int seatArr[] = leftSeat(ticketid);


        lucker = new String[count];                 //전역변수에 저장하여 드로우 된 회원들을 저장

        LinkedList<DrawVO> list = new LinkedList<>();

        for(DrawVO d : draw){
            list.add(d);
        }

        if(list.size()>0){
            for (int i = 0; i < count; i++){
                int number = r.nextInt(list.size());        //랜덤함수를 드로우를 신청한 회원 수 만큼의 크기로 설정
                custidArr[i] = list.get(number).getCustid();      //랜덤함수에 배정된 회원아이디를 배열에 저장
                lucker[i] = custidArr[i];
                list.remove(number);                         //당첨된 회원은 linkedList에서 제거하고 반복문 가동
            }
        }



        DBManager.drawDeleteSeatId(ticketid);              //여러번 드로우 할 경우를 대비해 드로우 전 draw테이블에 당첨여부 초기화
        for(int i = 0; i < count; i++){
            DBManager.drawUpdate(lucker[i], seatArr[i],ticketid);    //당첨된 회원이름과 남은 좌석을 draw 테이블에 반영
        }
        return "success";
    }

    @GetMapping("/draw")
    public String[] drawResult(){
        return lucker;
    }
}