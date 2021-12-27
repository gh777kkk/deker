package com.deker.security;

import com.deker.acct.mapper.AcctMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AcctMapper acctMapper;

    @Override
    public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
        if (!memId.equals("test")) throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다.");
        return new SecurityUser(memId, Arrays.asList("ROLE_AUTH"));
    }
}
