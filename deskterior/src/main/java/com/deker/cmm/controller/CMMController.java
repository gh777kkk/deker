package com.deker.cmm.controller;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.service.CMMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CMMController {
    private final CMMService cmmService;

    @RequestMapping(value = "/nmb/cmm/get/code", method = RequestMethod.GET)
    public List<CMM> getCode(CMMConditions conditions) {
        conditions.setCodeId("JOB");
        return cmmService.getCode(conditions);
    }
}
