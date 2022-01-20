package com.deker.acct.service;

import com.deker.acct.mapper.AcctMapper;
import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.exception.*;
import com.deker.jwt.JwtProvider;
import com.deker.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeUtility;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AcctServiceImpl implements AcctService {

    private final AcctMapper acctMapper;

    private final com.deker.cmm.util.CMMUtil CMMUtil;

    private final JavaMailSender emailSender;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final CustomUserDetailsService customUserDetailsService;

    @Value("tracking.key")
    private String trcKey;

    @Override
    @Transactional
    public Acct regMember(AcctConditions conditions) throws Exception {
        Acct acct = acctMapper.selectMemCheck(conditions);
        List<String> nicknameCheck = acctMapper.selectNicknameCheck(conditions);
        if (acct != null) throw new AlreadyMemberException();
        if (nicknameCheck.size() > 0) throw new AlreadyNicknameException();

        conditions.setMemId(CMMUtil.nextId("memId"));
        if(conditions.getPlatformCode().equals("P01")){
            String password = conditions.getPassword();
            conditions.setPassword(passwordEncoder.encode(conditions.getPassword()));
            acctMapper.insertMember(conditions);
            acctMapper.insertDekerMember(conditions);
            conditions.setPassword(password);
        }else {
            acctMapper.insertMember(conditions);
            acctMapper.insertSocialMember(conditions);
        }
        return loginPrc(conditions);
    }
    
    @Override
    public Acct getMemId(AcctConditions conditions) throws Exception{
        return loginPrc(conditions);
    }

    @Override
    public Acct memberIdEmailSend(String id)throws Exception {
        AcctConditions conditions = new AcctConditions();
        conditions.setId(id);
        conditions.setPlatformCode("P01");
        Acct acct = acctMapper.selectMemCheck(conditions);
        if (acct != null) throw new AlreadyMemberException();

        String ePw = createKey();
        MimeMessage message = createMessage(id,ePw);
        conditions.setCheckString(ePw);
        acctMapper.updateMailCheck(conditions);
        emailSender.send(message);
        return null;
    }

    @Override
    public Acct memberMailCheck(AcctConditions conditions)throws Exception {
        Acct mailCheck = acctMapper.selectMailCheckString(conditions);
        if (mailCheck == null) throw new MailCheckNotFoundException();
        return null;
    }

    @Override
    @Transactional
    public List<?> setImgTest(MultipartFile img, AcctConditions conditions)throws Exception{
        CMMUtil.setImg(img,conditions.getMemId());
        return null;
    }

    @Override
    public List<?> getImgTest(AcctConditions conditions, HttpServletRequest request) throws Exception {
        String jwtToken = jwtProvider.getToken(request);
        String a = jwtProvider.getUserNameFromJwtToken(jwtToken);
        Date b = jwtProvider.getExpToken(jwtToken);
        return null;
    }

    @Override
    public Acct getTrcTest(AcctConditions conditions) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://info.sweettracker.co.kr/api/v1/trackingInfo?t_key=GpWefcHZW65BrMbWLDI5EA&t_code=06&t_invoice=31732607830";
        Object data = restTemplate.getForEntity(url, Object.class);
        return null;
    }

    private MimeMessage createMessage(String id, String ePw)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, id);//보내는 대상
        message.setSubject(MimeUtility.encodeText("Deker회원가입 이메일 인증","UTF-8","B"));//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Deker입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("Deker","Deker"));//보내는 사람

        return message;
    }

    private String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    private Acct loginPrc(AcctConditions conditions) throws Exception{
        Acct acct = acctMapper.selectMemCheck(conditions);
        if (acct == null) throw new MemberNotFoundException();
        if (conditions.getPlatformCode().equals("P01")){
            acct = acctMapper.selectDekerLogin(conditions);
            boolean passwordCheck =passwordEncoder.matches(conditions.getPassword(),acct.getPassword());
            acct.setPassword(null);
            if (!passwordCheck) throw new LoginPasswordException();
        }else {
            acct = acctMapper.selectSocialLogin(conditions);
            if (acct == null) throw new LoginSocialIdException();
        }

        UserDetails authentication =customUserDetailsService.loadUserByUsername(acct.getMemId());
        String jwtToken = jwtProvider.generateJwtToken(authentication);
        acct.setJwtToken(jwtToken);
        acct.setExtTokenTime(jwtProvider.getExpToken(jwtToken));

        return acct;
    }
}
