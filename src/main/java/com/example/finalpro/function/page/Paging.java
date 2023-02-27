package com.example.finalpro.function.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paging {
    // 한 페이지당 보이는 record의 숫자
    private int pageSize = 3;
    // 다음을 누르기 전에 표기되는 page의 숫자
    private int showingPageSize = 5;

    // 현재 페이지
    private int page = 1;

    // 현재 페이지 값을 showingPageSize로 나누고 올림한 값 다음을 얼마나 눌러야 나오는 페이지인지 알 수 있다.
    // startPage, endPage를 계산할 수 있다
    private int page2;
    // 모든 record 숫자
    private int totalRecord;
    // 모든 page 숫자
    private int totalPage;

    // startRecord, endRecord 각 페이지의 시작 record와 끝 record
    private int startRecord;
    private int endRecord;

    // start, end 페이지
    private int startPage;
    private int endPage;

// ex)
//    page : 현재 페이지
//    page2 : 현재 페이지 값을 showingPageSize로 나누고 올림한 값. 다음을 몇번 눌러야 나오는 페이지인지 알 수 있다.
//    totalRecord = 124
//    pageSize (한 페이지에 보여지는 게시글 수) = 10
//    showingPageSize = 5
//    pageView = 5 (페이지가 몇개 나올건지)
//    totalPage = Math.ceil(totalRecord/pageSize) = 124/10 의 올림 = 13
//
// - page
//1 2 3 4 5
//    startRecord(page=1) : pageSize*page-(pageSize-1) = 10*1 - (10-1) = 1
//    endRecord(page=1) :  page * pageSize = 1*10 = 10
//
//    startPage(page2=1) : showingPageSize*1 - (showingPageSize-1) =  1
//    endPage(page2=2) : 1 * showingPageSize = 5
//
//    startRecord(page=2) : pageSize*page-(pageSize-1) = 10*2 - (10-1) = 11
//    endRecord(page=2) :  page * pageSize = 2*10 = 20
//
//
//  - page
//6 7 8 9 10
//    startPage(page2=2) : showingPageSize*2 - (showingPageSize-1) = 6
//    endPage(page2=2) : 2 * showingPageSize = 10

    public Paging(int totalRecord, int page){
        // totalRecord = 총 record 숫자
        // page = 현재 페이지

        // 현재 페이지
        setPage(page);
        // page2
        setPage2( (int)Math.ceil ((double)page / showingPageSize));

        // Record 총 갯수
        setTotalRecord(totalRecord);

        // page의 총 갯수
        // 총 페이지 숫자 (총 record 숫자를 한 페이지 최대 갯수로 나눈 뒤 올림 해준다)
        setTotalPage((int)Math.ceil(((double)totalRecord/pageSize)));

        // start, end 레코드 계산
        setStartRecord((pageSize*page) - (pageSize-1));
        setEndRecord(page*pageSize);

        // start, end 페이지 계산
        setStartPage((showingPageSize*page2) - (showingPageSize-1));
        setEndPage(page2*showingPageSize);
        
        // 만약 totalPage가 6보다 작은, 예를 들어 3이면 1 2 3 페이지만 나오도록.
        // 안그러면 3페이지가 마지막인데 endPage가 계산되어 5페이지까지 나옴
        if(totalPage < 6){
            setEndPage(totalPage);
        }
    }
}
