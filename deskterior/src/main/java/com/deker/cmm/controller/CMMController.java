package com.deker.cmm.controller;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.service.CMMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/nmb/file/test", method = RequestMethod.POST)
    public List<CMM> fileTest(CMMConditions conditions, @RequestParam("profileImg")MultipartFile multipartFile) {
        conditions.setCodeId("JOB");
        return cmmService.getCode(conditions);
    }
}
