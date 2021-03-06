package com.deker.acct.service;

import com.deker.acct.mapper.AcctMapper;
import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.cmm.model.Result;
import com.deker.exception.*;
import com.deker.jwt.JwtProvider;
import com.deker.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeUtility;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public Acct regMember(AcctConditions conditions) throws Exception {
        Acct acct = acctMapper.selectMemCheck(conditions);
        List<String> nicknameCheck = acctMapper.selectNicknameCheck(conditions);
        if (acct != null) {
            if(conditions.getPlatformCode().equals("P01")) throw new AlreadyMemberException();
            else return loginPrc(conditions);
        }
        if (nicknameCheck.size() > 0) {
            if(conditions.getPlatformCode().equals("P01")) throw new AlreadyNicknameException();
            else {
                int i = 1;
                String nickname = conditions.getNickname();
                while (true){
                    conditions.setNickname(nickname+"#"+i);
                    List<String> check = acctMapper.selectNicknameCheck(conditions);
                    if (check.size() > 0) i++;
                    else break;
                }
            }
        }

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
        conditions.setAuthorityCode("ROLE_USER");
        acctMapper.insertAuthority(conditions);
        return loginPrc(conditions);
    }
    
    @Override
    public Acct getMemId(AcctConditions conditions) throws Exception{
        return loginPrc(conditions);
    }

    @Override
    public Acct getMemberInfo(HttpServletRequest request) throws Exception{
        AcctConditions conditions = new AcctConditions();
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        Acct result = acctMapper.selectMemberInfo(conditions);
        List<Acct> contentsList = acctMapper.selectTagList(conditions);
        List<String> tagList = new ArrayList<>();
        if(contentsList != null){
            for(Acct data : contentsList){
                tagList.add(data.getContents());
            }
        }
        result.setTag(tagList);
        if (result.getProfileImg() != null) result.setProfileImg(CMMUtil.getImg(result.getProfileImg()));
        return result;
    }

    @Override
    public Acct modMemberInfo(MultipartFile profileImg,AcctConditions conditions,HttpServletRequest request) throws Exception{
        AcctConditions tegConditions = new AcctConditions();
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        if (profileImg != null) conditions.setProfileImg(CMMUtil.setImg(profileImg,conditions.getMemId()));
        acctMapper.updateMemberInfo(conditions);

        tegConditions.setMemId(conditions.getMemId());
        acctMapper.deleteTag(conditions);
        if (conditions.getTag() == null) return null;
        for(String contents : conditions.getTag()){
            tegConditions.setContents(contents);
            tegConditions.setMemTagId(CMMUtil.nextId("mtgId"));
            acctMapper.insertTag(tegConditions);
        }
        return null;
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
    public void modPassword(AcctConditions conditions,HttpServletRequest request)throws Exception{
        if (conditions == null || conditions.getPassword() == null || conditions.getPassword().equals("")) throw new PasswordNullException();
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        conditions.setPassword(passwordEncoder.encode(conditions.getPassword()));
        acctMapper.updatePassword(conditions);
    }

    private MimeMessage createMessage(String id, String ePw)throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, id);//????????? ??????
        message.setSubject(MimeUtility.encodeText("Deker???????????? ????????? ??????","UTF-8","B"));//??????

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> ??????????????? Deker?????????. </h1>";
        msgg+= "<br>";
        msgg+= "<p>?????? ????????? ???????????? ????????? ????????? ??????????????????<p>";
        msgg+= "<br>";
        msgg+= "<p>???????????????!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>???????????? ?????? ???????????????.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//??????
        message.setFrom(new InternetAddress("Deker","Deker"));//????????? ??????

        return message;
    }

    private String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // ???????????? 8??????
            int index = rnd.nextInt(3); // 0~2 ?????? ??????

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

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = jwtProvider.getExpToken(jwtToken);
        acct.setExtTokenTime(dateFormat.format(date));

        return acct;
    }
}
