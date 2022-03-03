package com.be.deskterior.acctTest;

import com.deker.DeskteriorApplication;
import com.deker.acct.model.AcctConditions;
import com.deker.acct.service.AcctService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes= DeskteriorApplication.class)
//@AutoConfigureMockMvc
@Transactional
public class AcctTest {

    @MockBean
    private AcctService acctService;

    private MockMvc mockMvc;

    @Test
    public void regMember() throws Exception {
        AcctConditions conditions = new AcctConditions();
//        conditions.setId("gh767@miraeit.net");
//        conditions.setPlatformCode("P01");
//        conditions.setPassword("af7185634@");
//        conditions.setNickname("ggg");
//        conditions.setAgreeYn("Y");
        acctService.regMember(conditions);
    }

    @Test
    public void login() throws Exception {
        AcctConditions conditions = new AcctConditions();
        conditions.setId("gh77@miraeit.net");
        conditions.setPlatformCode("P01");
        conditions.setPassword("af7185634@");
        acctService.getMemId(conditions);
    }
}
