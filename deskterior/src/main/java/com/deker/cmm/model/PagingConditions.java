package com.deker.cmm.model;

public class PagingConditions {
    /** 현재의 페이지 번호 */
    private int currentPageNo;

    private int startRowNo;

    private int endRowNo;

    //    public PagingConditions(int currentPageNo){
//        if (currentPageNo <=0) this.currentPageNo = 1;
//        else this.currentPageNo = currentPageNo;
//        startRowNo = ((this.currentPageNo-1)*100)+1;
//        endRowNo = startRowNo+99;
//    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        if (currentPageNo <=0) this.currentPageNo = 1;
        else this.currentPageNo = currentPageNo;
    }

    public int getStartRowNo() {
        /** 조회할 페이지의 시작 행번호 */
        if (currentPageNo <=0) this.currentPageNo = 1;
        return ((currentPageNo - 1) * 100);
    }

    public int getEndRowNo() {
        /** 조회할 페이지의 끝 행번호 */
        if (currentPageNo <=0) this.currentPageNo = 1;
        return ((currentPageNo - 1) * 100) + 99;
    }
}
