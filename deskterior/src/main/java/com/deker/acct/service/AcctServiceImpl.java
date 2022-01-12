package com.deker.acct.service;

import com.deker.acct.mapper.AcctMapper;
import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.cmm.util.IDSUtil;
import com.deker.exception.AlreadyMemberException;
import com.deker.exception.AlreadyNicknameException;
import com.deker.exception.MailCheckNotFoundException;
import com.deker.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AcctServiceImpl implements AcctService {

    private final AcctMapper acctMapper;

    private final IDSUtil IDSUtil;

    private final JavaMailSender emailSender;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<?> regMember(AcctConditions conditions) throws Exception {
        if(conditions.getPlatformCode().equals("P01")) conditions.setPassword(passwordEncoder.encode(conditions.getPassword()));
        Acct acct = acctMapper.selectMemCheck(conditions);
        List<String> nicknameCheck = acctMapper.selectNicknameCheck(conditions);
        if (acct != null) throw new AlreadyMemberException();
        if (nicknameCheck.size() > 0) throw new AlreadyNicknameException();

        conditions.setMemId(IDSUtil.nextId("memId"));
        if(conditions.getPlatformCode().equals("P01")){
            acctMapper.insertMember(conditions);
            acctMapper.insertDekerMember(conditions);
        }else {
            acctMapper.insertMember(conditions);
            acctMapper.insertSocialMember(conditions);
        }
        return null;
    }
    
    @Override
    public Acct getMemId(AcctConditions conditions) {
        Acct acct = acctMapper.selectMemCheck(conditions);
        if (acct == null) new IllegalAccessException("가입되지 않은 회원 입니다.");
        return acct;
    }

    @Override
    public void memberIdEmailSend(String id)throws Exception {
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
    }

    @Override
    public void memberMailCheck(AcctConditions conditions)throws Exception {
        Acct mailCheck = acctMapper.selectMailCheckString(conditions);
        if (mailCheck == null) throw new MailCheckNotFoundException();
    }


    private MimeMessage createMessage(String id, String ePw)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, id);//보내는 대상
        message.setSubject("Deker회원가입 이메일 인증");//제목

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

    public String createKey() {
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

}
