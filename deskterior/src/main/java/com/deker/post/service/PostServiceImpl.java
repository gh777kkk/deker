package com.deker.post.service;

import com.deker.cmm.model.PageInfo;
import com.deker.cmm.util.CMMUtil;
import com.deker.exception.PostIdMemIdDifferentException;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.ProductModel;
import com.deker.post.mapper.PostMapper;
import com.deker.post.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostMapper postMapper;

    private final CMMUtil CMMUtil;

    public PageInfo<Post> getMemberInfo(PostConditions conditions) throws Exception{
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


    public PostMain getPostMain(String memId){



        PostMain pm = new PostMain();

        List<PostProperties> rank = postMapper.getPostLike();
        for (PostProperties pp : rank) {
            PostProperties mypp = postMapper.getPostDetail(pp.getCommunityId());
            pp.setCommunityTitle(mypp.getCommunityTitle());
            pp.setUserId(mypp.getUserId());
            pp.setUserNick(mypp.getUserNick());
            pp.setUserProfileImg(CMMUtil.getImg(mypp.getUserProfileImg()));
            pp.setCommunityImage(CMMUtil.getImg(mypp.getCommunityImage()));
            pp.setCommentCount(postMapper.getPostCommentCount(pp.getCommunityId()));
            pp.setMemId(memId);
            pp.setFollowingCheck(postMapper.getPostFollow(pp));
        }

            pm.setRanks(rank);


        if(memId != null) {

            List<PostProperties> follow = postMapper.getPostMyFollow(memId);
            for (PostProperties pp : follow) {
                pp.setMemId(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setMemId(memId);
                pp.setFollowingCheck(postMapper.getPostFollow(pp));

            }
            pm.setFollow(follow);


            List<PostProperties> custom = postMapper.getPostCustom(memId);
            for (PostProperties pp : custom) {
                pp.setMemId(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setMemId(memId);
                pp.setFollowingCheck(postMapper.getPostFollow(pp));

            }

            pm.setCustom(custom);

        }

        return pm;
    }



    public PageInfo<PostComment> getPostComments(PostCommentConditions conditions){


        int nonpagedCount = postMapper.getPostCommentCount(conditions.getCommunityId());
        PageInfo<PostComment> pageInfo = new PageInfo<>(conditions,nonpagedCount);


        pageInfo.setObjectData(conditions.getCommunityId());
        pageInfo.setTotalCount(nonpagedCount);
        pageInfo.setList(postMapper.getPostComment(conditions));


        return pageInfo;
    }



    public PostDetail getPostDetail(PostDetail pd){

        MyPost mp = postMapper.getSelectPostDetail(pd.getCommunityPostId());
        mp.setPostImg(CMMUtil.getImg(mp.getPostImg()));

        List<MyPost> tags = postMapper.getPostTag(mp.getPostDetailId());
        List<String> communityTags = new ArrayList<>();
        for (MyPost tag : tags) {
            communityTags.add(tag.getTag());
        }
        mp.setCommunityTags(communityTags);

        pd.setCommunityPost(mp);

        List<CommunityProducts> cpList = postMapper.getPostProduct(mp.getPostDetailId());
        for (CommunityProducts cp : cpList){
            cp.setProductImgUrl(CMMUtil.getImg(cp.getProductImgUrl()));
        }
        pd.setCommunityPostSelectedProduct(cpList);

        return pd;
    }

    public void rmvPost(PostConditions conditions) throws Exception{
        if (postMapper.selectPostMemId(conditions) == null) throw new PostIdMemIdDifferentException();
        if (!conditions.getMemId().equals(postMapper.selectPostMemId(conditions).getMemId())) throw new PostIdMemIdDifferentException();
        postMapper.deletePostList(conditions);
        postMapper.deletePostDetail(conditions);
    }


//end
}
