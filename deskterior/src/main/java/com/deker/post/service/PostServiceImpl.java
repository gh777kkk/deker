package com.deker.post.service;

import com.deker.cmm.model.PageInfo;
import com.deker.cmm.util.CMMUtil;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.ProductModel;
import com.deker.post.mapper.PostMapper;
import com.deker.post.model.CommunityProducts;
import com.deker.post.model.MyPost;
import com.deker.post.model.Post;
import com.deker.post.model.PostConditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final JwtProvider jwtProvider;

    private final PostMapper postMapper;

    private final CMMUtil CMMUtil;

    @Override
    public PageInfo<Post> getMemberInfo(HttpServletRequest request, PostConditions conditions) throws Exception{
//        PostConditions conditions = new PostConditions();
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        Post post = new Post();
        if(postMapper.selectMemberProfileImg(conditions) != null) post.setProfileImg(CMMUtil.getImg(postMapper.selectMemberProfileImg(conditions).getProfileImg()));
        post.setPostList(postMapper.selectMyPostList(conditions));
        for (Post data : post.getPostList()) {
            data.setPostImg(CMMUtil.getImg(data.getPostImg()));
        }

        int nonpagedCount = postMapper.selectMyPostListCount(conditions);
        PageInfo<Post> pageInfo = new PageInfo<>(conditions,nonpagedCount);
        pageInfo.setList(post.getPostList());
        pageInfo.setObjectData(post.getProfileImg());
        return pageInfo;
    }



    public void regPost(MyPost mp, MultipartFile img) throws IOException {
        mp.setPostImg(CMMUtil.setImg(img, mp.getMemId()));
        mp.setCommunityId(CMMUtil.nextId("cmId"));
        postMapper.insertPost(mp);

        String pdId = CMMUtil.nextId("pdId");
        mp.setPostDetailId(pdId);
        postMapper.insertPostDetail(mp);

        for (String tag : mp.getCommunityTags()) {
            mp.setTag(tag);
            mp.setPostTagId(CMMUtil.nextId("tagId"));
            postMapper.insertPostTag(mp);
        }

        for (CommunityProducts cp : mp.getCommunityProducts()) {
            cp.setPostDetailId(pdId);
            postMapper.insertPostItem(cp);
        }
    }


//end
}
