package com.deker.post.service;

import com.deker.cmm.util.CMMUtil;
import com.deker.jwt.JwtProvider;
import com.deker.post.mapper.PostMapper;
import com.deker.post.model.Post;
import com.deker.post.model.PostConditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final JwtProvider jwtProvider;

    private final PostMapper postMapper;

    private final CMMUtil CMMUtil;

    @Override
    public Post getMemberInfo(HttpServletRequest request) throws Exception{
        PostConditions conditions = new PostConditions();
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        Post post = new Post();
        if(postMapper.selectMemberProfileImg(conditions) != null) post.setProfileImg(CMMUtil.getImg(postMapper.selectMemberProfileImg(conditions).getProfileImg()));
        post.setPostList(postMapper.selectMyPostList(conditions));
        return post;
    }
}
