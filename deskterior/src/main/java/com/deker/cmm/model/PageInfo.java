package com.deker.cmm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo<T> {
    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 조회 결과의 전체 행의 수 */
    private int totalCount;

    private boolean lastPage;

    private Object objectData;

    private List<T> list;

    public PageInfo(PagingConditions conditions, int nonpagedCount) {
        totalCount = nonpagedCount;
        currentPageNo = (conditions.getCurrentPageNo()<=0) ? 1 : conditions.getCurrentPageNo();
        lastPage = conditions.getEndRowNo() >= totalCount;
    }
}
