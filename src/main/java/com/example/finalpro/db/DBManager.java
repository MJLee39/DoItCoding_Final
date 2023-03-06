package com.example.finalpro.db;

import com.example.finalpro.entity.Customer;
import com.example.finalpro.vo.CustomerVO;
import com.example.finalpro.entity.Qna;
import com.example.finalpro.vo.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			String resource = "db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
					new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static List<CustomerVO> findAllCustomer() {
		List<CustomerVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("customer.findAll");
		session.close();
		return list;
	}

	public static List<TicketVO> findAllTicket(){
		List<TicketVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findAll");
		session.close();
		return list;
	}

	public static List<BookVO> findAllBook(){
		List<BookVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("book.findAll");
		session.close();
		return list;
	}

	public static List<CategoryVO> findAllCategory(){
		List<CategoryVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("category.findAll");
		session.close();
		return list;
	}

	public static List<DrawVO> findAllDraw(){
		List<DrawVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("draw.findAll");
		session.close();
		return list;
	}

	public static List<NoticeVO> findAllNotice(){
		List<NoticeVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("notice.findAll");
		session.close();
		return list;
	}
	public static List<PlaceVO> findAllPlace(){
		List<PlaceVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("place.findAll");
		session.close();
		return list;
	}

	public static List<QnaVO> findAllQna(){
		List<QnaVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("qna.findAll");
		session.close();
		return list;
	}

	public static List<ReviewVO> findAllReview(){
		List<ReviewVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.findAll");
		session.close();
		return list;
	}

	public static List<SeatVO> findAllSeat(){
		List<SeatVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("seat.findAll");
		session.close();
		return list;
	}

	// 회원정보 조회
	public static CustomerVO findByCustid(String custid){
		CustomerVO c = null;
		SqlSession session = sqlSessionFactory.openSession();
		c = session.selectOne("customer.findByCustid", custid);
		session.close();
		return c;
	}

	// 특정 좌석의 잔여좌석 조회
	public static int findLeftSeatByTicketid(int ticketid){
		int num = 0;
		SqlSession session = sqlSessionFactory.openSession();
		num = session.selectOne("seat.selectLeftSeat",ticketid);
		System.out.println("잔여좌석DBM에서:"+num);
		session.close();
		return num;
	}

	// 티켓 상세 정보 조회
	public static TicketVO findByTicketid(int ticketid){
		TicketVO t = null;
		SqlSession session = sqlSessionFactory.openSession();
		t = session.selectOne("ticket.findByTicketid", ticketid);
		System.out.println("ticket객체:"+t);
		session.close();
		return t;
	}


	// ******** admin.ticket ********

	// 메인 페이지에서 카테고리 , 시간 별로 상영작 출력하기
	// time=0은 과거, time=1은 현재, time=2는 미래
	public static List<TicketVO> findAllTicketByCategory(int time, int cateid){
		List<TicketVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("time", time);
		map.put("cateid", cateid);

		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findAllTicketByCategory", map);
		session.close();

		return list;
	}

	// admin의 ticketList
	// ticket의 page에 따라 startRecord, endRecord에 해당하는 ticket 목록 출력
	public static List<TicketVO> findTicketPaging(int startRecord, int endRecord){
		List<TicketVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);

		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findTicketPaging", map);
		session.close();

		return list;
	}

	// ticket의 page에 따라 startRecord, endRecord에 해당하는 ticket 목록 출력
	// +search 기능
	public static List<TicketVO> findTicketPagingSearch(int startRecord, int endRecord, String keyword){
		List<TicketVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("keyword", keyword);


		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findTicketPagingSearch", map);
		session.close();

		return list;
	}

	// ticket의 page에 따라 startRecord, endRecord에 해당하는 ticket 목록 출력
	// +search 기능
	// +정렬 기능
	public static List<TicketVO> findTicketPagingSearchOrderBy(int startRecord, int endRecord, String keyword, String order){
		List<TicketVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("keyword", keyword);
		map.put("order", order);

		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findTicketPagingSearchOrderBy", map);
		session.close();

		return list;
	}

	// ticket의 totalRecord를 구하기
	public static int getTotalRecord(String keyword){
		int totalRecord = 0;
		SqlSession session = sqlSessionFactory.openSession();
		totalRecord = session.selectOne("ticket.getTotalRecord", keyword);
		session.close();
		if(totalRecord==0){
			totalRecord = 1;
		}
		return totalRecord;
	}

	public static List<RankingVO> findAllRankingOrderByScore(int cateid){
		List<RankingVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ranking.findAllRankingOrderByScore", cateid);
		session.close();
		return list;
	}

	// ticket을 검색
	public static List<TicketVO> findSearchTicket(String keyword){
		List<TicketVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findSearchTicket", keyword);
		session.close();
		return list;
	}

	// 티켓의 리뷰 출력, 정렬
	public static List<ReviewVO> findReviewByTicketid(int ticketid, int re){
		List<ReviewVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ticketid", ticketid);
		map.put("re", re);
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.findReviewByTicketid",map);
		session.close();
		return list;
	}

	// 사용자의 티켓리뷰 출력
	public static List<MyReviewVO> findReviewByTicketAndCust(String custid, int ticketid){
		List<MyReviewVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("ticketid", ticketid);
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.reviewByTicketAndCust",map);
		session.close();
		return list;
	}

	// ticketid로 리뷰내역이 있나 확인
	public static List<MyReviewVO> checkReviewByTicketid(int ticketid){
		List<MyReviewVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.checkReview",ticketid);
		session.close();
		return list;
	}

	// 티켓 후기의 평균별점 구하기
	public static int findAvgScore(int ticketid){
		int avg = 0;
		SqlSession session = sqlSessionFactory.openSession();
		avg = session.selectOne("review.findAvgScore",ticketid);
		session.close();
		return avg;
	}

	// 성별별로 예약자수 구하기
	public static List<CountGenderVO> countGender(int ticketid){
		List<CountGenderVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("book.countGender",ticketid);
		System.out.println("countGender:"+list);
		session.close();
		return list;
	}

	// 세대별로 예약자수 구하기
	public static List<CountGenerationVO> countGeneration(int ticketid){
		List<CountGenerationVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("book.countGeneration",ticketid);
		System.out.println("countGeneration:"+list);
		session.close();
		return list;
	}







	// admin에서 ticket을 insert
	public static int insertTicket(TicketVO ticket){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.insert("ticket.insertTicket", ticket);
		session.commit();
		session.close();
		return re;
	}
	// admin에서 ticket을 update
	public static int updateTicket(TicketVO ticket){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.insert("ticket.updateTicket", ticket);
		session.commit();
		session.close();
		return re;
	}


	// ******** admin.customer ********

	//Customer 아이디 찾기
	public static CustomerVO findCustid(String name, String phone){
		CustomerVO vo = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("phone", phone);
		SqlSession session = sqlSessionFactory.openSession();
		vo= session.selectOne("customer.findCustid", map);
		session.close();
		return vo;
	}

	//전화번호로 개인정보 확인
	public static CustomerVO checkByPhone(String custid, String phone){
		CustomerVO vo = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("phone", phone);
		SqlSession session = sqlSessionFactory.openSession();
		vo= session.selectOne("customer.checkByPhone", map);
		session.close();
		return vo;
	}

	//Customer 전화번호로 비밀번호 재설정
	public static int updatePwdbyPhone(CustomerVO customer){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.update("customer.updatePwdbyPhone", customer);
		session.commit();
		session.close();
		return re;
	}

	//이메일로 개인정보 확인
	public static CustomerVO checkByEmail(String custid, String email){
		CustomerVO vo = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("email", email);
		SqlSession session = sqlSessionFactory.openSession();
		vo= session.selectOne("customer.checkByEmail", map);
		session.close();
		return vo;
	}

	//Customer 이메일로 비밀번호 재설정
	public static int updatePwdbyEmail(CustomerVO customer){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.update("customer.updatePwdbyEmail", customer);
		session.commit();
		session.close();
		return re;
	}

	//Customer 아이디와 비번 일치 login
	public static CustomerVO matchIdPwd(String custid, String pwd){
		CustomerVO vo = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("pwd", pwd);
		SqlSession session = sqlSessionFactory.openSession();
		vo= session.selectOne("customer.matchIdPwd", map);
		session.close();
		return vo;
	}

	// 고객정보 수정
	public static int updateCustomer(CustomerVO customer){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.update("customer.updateCustomer", customer);
		session.commit();
		session.close();
		return re;
	}

	// custid에 따른 qna 작성 리스트
	public static List<QnaVO> listQnaByCustid(String custid){
		List<QnaVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("qna.selectQnaByCustid", custid);
		session.close();
		return list;
	}

	public static List<CustomerVO> findCustomerPagingSearch(int startRecord, int endRecord, String keyword){
		List<CustomerVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("keyword", keyword);

		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("customer.findCustomerPagingSearch", map);
		session.close();

		return list;
	}

	public static int getTotalCustomerRecord(String keyword){
		int totalRecord = 0;
		SqlSession session = sqlSessionFactory.openSession();
		totalRecord = session.selectOne("customer.getTotalRecord", keyword);
		session.close();
		if(totalRecord==0){
			totalRecord = 1;
		}

		return totalRecord;
	}

    // Tikcetid의 전체 좌석 목록 출력
    public static List<SeatVO> listSeatByTicketid(int ticketid){
        List<SeatVO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        list = session.selectList("seat.listSeatByTicketid",ticketid);
        System.out.println("listSeatByTicketid:"+list);
        session.close();
        return list;
    }

	// 예매를 위해 ticketid와 seatname으로 좌석 아이디 찾기
	public static int findSeatId(int ticketid, String seatname){
		int seatid = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ticketid", ticketid);
		map.put("seatname", seatname);
		SqlSession session = sqlSessionFactory.openSession();
		seatid = session.selectOne("seat.findSeatId",map);
		System.out.println("seatid:"+seatid);
		return seatid;
	}

	// 좌석예매
	public static int registSeat(int seatid){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("seat.registSeat",seatid);
		System.out.println("registSeat_re:"+re);
		session.close();
		return re;
	}

	// 티켓예매
	public static int bookTicket(String custid, int ticketid, int seatid){
		int re = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("ticketid", ticketid);
		map.put("seatid", seatid);
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("book.bookTicket",map);
		System.out.println("registSeat_re:"+re);
		session.close();
		return re;
	}


//	public static List<CustomerVO> findAll() {
//		List<CustomerVO> list = null;
//		SqlSession session = sqlSessionFactory.openSession();
//		list = session.selectList("customer.findAll");
//		session.close();
//		return list;
//	}

	// ******** Notice ********
	// 공지 등록
	public static int insertNotice(NoticeVO n){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.insert("notice.insert",n);
		session.close();
		System.out.println("DBManager에서 re:"+re);
		return re;
	}

	// 조회수 증가
	public static int updateNoticeHit(int notice_no){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("notice.updateHit",notice_no);
		session.close();
		return re;
	}

	public static int updateNotice(NoticeVO n){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("notice.update",n);
		session.close();
		return re;
	}

	// Notice Paging
	public static int getTotalNoticeRecord(HashMap<String, Object> hashMap){
		int totalRecord=-1;
		SqlSession session=sqlSessionFactory.openSession();
		totalRecord=session.selectOne("notice.getTotalRecord",hashMap);
		session.close();
		return totalRecord;
	}

	public static Object findAllNotice(HashMap<String, Object> hashMap) {
		List<NoticeVO> list=null;
		SqlSession session=sqlSessionFactory.openSession();
		list=session.selectList("notice.findAll", hashMap);
		session.close();
		return list;
	}

	// ******** QNA ********
	// QNA 등록
    public static int insertQna(QnaVO q) {
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.insert("qna.insert",q);
		session.close();
		return re;
    }

	// QNA update
	public static int updateQna(QnaVO qnaVO) {
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("qna.update",qnaVO);
		session.close();
		return re;
	}

	// QNA 답변 작성 및 수정
	public static int updateAnswer(QnaVO q){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("qna.updateAnswer",q);
		session.close();
		return re;
	}

	// QNA 답변 삭제
	public static int deleteAnswer(int qna_no){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("qna.deleteAnswer",qna_no);
		session.close();
		return re;
	}
	// 비공개 QNA일 경우 작성자가 로그인한 사용자와 일치하는지 확인
//	public static int checkWriter(QnaVO q){
//		int re=-1;
//		SqlSession session=sqlSessionFactory.openSession(true);
//		re=session.selectOne("qna.checkWriter",q);
//		session.close();
//		return re;
//	}

	// 해당 사용자가 예매한 티켓 아이디 목록 가져오기 (for QNA)
	public static List<Integer> findTicketidByCustid(String custid){
		List<Integer> list=null;
		SqlSession session=sqlSessionFactory.openSession();
		list=session.selectList("book.findTicketidByCuistid", custid);
		session.close();
		return list;
	}

	// 조회수 증가
	public static int updateQNAHit(int qna_no){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("qna.updateHit",qna_no);
		session.close();
		return re;
	}

	//	페이징 처리를 위한 총 레코드 수 계산
	public static int getTotalQnaRecord(HashMap<String, Object> hashMap){
		int totalRecord=-1;
		SqlSession session=sqlSessionFactory.openSession();
		totalRecord=session.selectOne("qna.getTotalRecord",hashMap);
		session.close();
		return totalRecord;
	}

	public static List<NoticeVO> findAllQna(HashMap<String, Object> hashMap) {
		List<NoticeVO> list=null;
		SqlSession session=sqlSessionFactory.openSession();
		list=session.selectList("qna.findAll", hashMap);
		session.close();
		return list;
	}

	// 알림 생성
	public static int insertNotification(NotificationVO notificationVO){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.insert("notification.insert", notificationVO);
		session.close();
		return re;
	}

	// 알림 불러오기
	public static List<NotificationByCustidVO> findNotificationByCustid(String custid){
		List<NotificationByCustidVO> list=null;
		SqlSession session=sqlSessionFactory.openSession();
		list=session.selectList("notification.findByCustid",custid);
		session.close();
		return list;
	}

	// 체크 안 한 알림 갯수
	public static int countNChecked(){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession();
		re=session.selectOne("notification.countNChecked");
		session.close();
		return re;
	}

	// 알림 checked : n->y\
	public static int updateCheckedToY(){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.update("notification.updateCheckedToY");
		session.close();
		return re;
	}

	// 알림 삭제
	public static int deleteNotification(int notif_no){
		int re=-1;
		SqlSession session=sqlSessionFactory.openSession(true);
		re=session.delete("notification.delete",notif_no);
		session.close();
		return re;
	}
}
