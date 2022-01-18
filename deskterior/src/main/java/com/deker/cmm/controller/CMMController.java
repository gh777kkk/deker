package com.deker.cmm.controller;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.model.Result;
import com.deker.cmm.service.CMMService;
import com.deker.mkt.model.request.ProductBuy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CMMController {
    private final CMMService cmmService;

    @RequestMapping(value = "/nmb/cmm/get/code", method = RequestMethod.POST)
    public ResponseEntity<?> getCode(@RequestBody CMMConditions conditions) {
        return ResponseEntity.ok(
                new Result("200", "코드 조회 성공",
                        cmmService.getCode(conditions)
                )
        );
    }

    @RequestMapping(value = "/nmb/file/test", method = RequestMethod.POST)
    public List<CMM> fileTest(CMMConditions conditions, @RequestParam("profileImg")MultipartFile multipartFile) {
        conditions.setCodeId("JOB");
        return cmmService.getCode(conditions);
    }
}
