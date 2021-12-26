package com.deker.security;

import com.deker.acct.mapper.AcctMapper;
import com.deker.acct.model.AcctConditions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AcctMapper acctMapper;

    @Override
    public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
        AcctConditions condition = new AcctConditions();
        condition.setMemId(memId);
        return new SecurityUser(condition);
    }
}
