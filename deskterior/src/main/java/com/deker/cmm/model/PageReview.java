package com.deker.cmm.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageReview<T> {
    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 하나의 페이지에 나타낼 행의 수 */
    private int fetchCount;

    /** 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 */
    private int linksPerPage;

    /** 조회 결과의 전체 행의 수 */
    private int totalCount;

    /** 조회 결과의 전체 페이지 수 */
    private int totalPageCount;

    /** 현재 페이지에서 첫 행이 가지는 역순번호( 총 10개라면 첫 행이 10 마지막 행이 1) */
    private int maxNoOfCurrentPage;

    /** 조회 결과의 전체 페이지 수를 linksPerPage의 수로 나눌때 현재 페이지가 속하는 그룹의 번호 */
    private int currentPageGroup;

    /** 전체 페이지의 그룹중 마지막 그룹의 번호 */
    private int lastPageGroup;

    /** 현재 페이지의 시작 행번호 */
    private int startRowNo;

    /** 현재 페이지의 끝 행번호 */
    private int endRowNo;

    /** 현재 페이지 그룹에 포함되는 페이지 번호 목록 */
    private List<Integer> linkPages;

    /** 다음 페이지 그룹의 시작 페이지 번호 */
    private int nextGroupPageNo;

    /** 이전 페이지 그룹의 마지막 페이지 번호 */
    private int prevGroupPageNo;

    /** 이전 페이지 그룹의 존재 여부 */
    private boolean hasPrevPageGroup;

    /** 다음 페이지 그룹의 존재 여부 */
    private boolean hasNextPageGroup;

    private List<T> list;

    public PageReview(PageReviewConditions conditions, int nonpagedCount) {
        totalCount = nonpagedCount;
        currentPageNo = (conditions.getCurrentPageNo()<=0) ? 1 : conditions.getCurrentPageNo();
        linksPerPage = (conditions.getLinksPerPage()<=0) ? 10 : conditions.getLinksPerPage();
        fetchCount = (conditions.getFetchCount()<=0) ? 10 : (conditions.getFetchCount()==-1) ? nonpagedCount : conditions.getFetchCount();

        this.maxNoOfCurrentPage = totalCount - (this.fetchCount *(this.currentPageNo-1));
        totalPageCount = 0;
        if(fetchCount != 0) {
            totalPageCount = totalCount/ fetchCount;
            if((totalCount% fetchCount) != 0)
                totalPageCount++;
        }

        currentPageGroup = (currentPageNo-1) / linksPerPage;
        lastPageGroup = (totalPageCount-1) / linksPerPage;
        conditions.setStartRowNo(startRowNo = (currentPageNo-1) * fetchCount);
        conditions.setEndRowNo(endRowNo = startRowNo + fetchCount);

        linkPages = new ArrayList<>();
        for(int i = (currentPageGroup*linksPerPage)+1 ; i<=(currentPageGroup+1)*linksPerPage && i<=totalPageCount ; i++) {
            linkPages.add(i);
        }

        hasNextPageGroup = currentPageGroup<lastPageGroup;
        hasPrevPageGroup = currentPageGroup>0;
    }
}
