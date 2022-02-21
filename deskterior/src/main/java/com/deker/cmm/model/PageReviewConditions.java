package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageReviewConditions {
    /** 현재의 페이지 번호 */
    private int currentPageNo;

    /** 조회할 페이지의 시작 행번호 */
    private int startRowNo;

    /** 조회할 페이지의 끝 행번호 */
    private int endRowNo;

    /** 하나의 페이지에 나타낼 행의 수 */
    private int fetchCount;

    /** 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 */
    private int linksPerPage;

    /**
     * 조회할 페이지 번호를 반환한다.
     * @return
     */
    public int getCurrentPageNo() {
        if(currentPageNo<=0) currentPageNo = 1;
        return currentPageNo;
    }
}
