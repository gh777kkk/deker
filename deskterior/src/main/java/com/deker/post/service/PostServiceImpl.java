package com.deker.post.service;

import com.deker.cmm.model.Follow;
import com.deker.cmm.model.PageInfo;
import com.deker.cmm.util.CMMUtil;
import com.deker.exception.PostIdMemIdDifferentException;
import com.deker.post.mapper.PostMapper;
import com.deker.post.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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



    public void regPost(MyPost mp, MultipartFile img) throws IOException,Exception {
        if (img != null) {
            mp.setPostImg(CMMUtil.setImg(img, mp.getMemId()));
        }
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



    public void modPost(MyPost mp, MultipartFile img) throws IOException,Exception {
        if (img != null) {
            mp.setPostImg(CMMUtil.setImg(img, mp.getMemId()));
        }
        postMapper.updatePost(mp);

        String pdId = postMapper.getPostDetailId(mp);
        mp.setPostDetailId(pdId);
        postMapper.updatePostDetail(mp);

        List<String> tagId = postMapper.getPostTagId(mp);
        List<String> tags = mp.getCommunityTags();

        for (int i =0; i<tagId.size(); i++) {
            mp.setTag(tags.get(i));
            mp.setPostTagId(tagId.get(i));
            postMapper.updatePostTag(mp);
        }

        for (CommunityProducts cp : mp.getCommunityProducts()) {
            cp.setPostDetailId(pdId);
            postMapper.updatePostItem(cp);
        }
    }





    public PostMain getPostMain(String memId){

        PostMain pm = new PostMain();
        MyPost mp = new MyPost();
        mp.setMemId(memId);

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

            mp.setCommunityId(pp.getCommunityId());
            String myLike = postMapper.getSelectPostDetailLiked(mp);
            if(pp.getCommunityId().equals(myLike)) {
                pp.setLiked(true);
            }
            else{
                pp.setLiked(false);
            }
        }

            pm.setRanks(rank);



            List<PostProperties> follow = postMapper.getPostMyFollow(memId);
            for (PostProperties pp : follow) {
                pp.setMemId(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setMemId(memId);
                pp.setFollowingCheck(true);


                mp.setCommunityId(pp.getCommunityId());
                String myLike = postMapper.getSelectPostDetailLiked(mp);
                if(pp.getCommunityId().equals(myLike)) {
                    pp.setLiked(true);
                }
                else{
                    pp.setLiked(false);
                }

            }
            pm.setFollow(follow);


            List<PostProperties> custom = postMapper.getPostCustom(memId);
            for (PostProperties pp : custom) {
                pp.setMemId(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setMemId(memId);
                pp.setFollowingCheck(postMapper.getPostFollow(pp));


                mp.setCommunityId(pp.getCommunityId());
                String myLike = postMapper.getSelectPostDetailLiked(mp);
                if(pp.getCommunityId().equals(myLike)) {
                    pp.setLiked(true);
                }
                else{
                    pp.setLiked(false);
                }
            }

            pm.setCustom(custom);



        return pm;
    }



    public PageInfo<PostProperties> getMorePostMain(PostConditions conditions){

        PageInfo<PostProperties> pageInfo = null;
        MyPost mp = new MyPost();
        mp.setMemId(conditions.getMemId());

        if(conditions.getType().equals("rank")){

            int nonpagedCount = postMapper.getMorePostCount();
            pageInfo = new PageInfo<>(conditions,nonpagedCount);

            List<PostProperties> rank = postMapper.getMorePostLike(conditions);
            for (PostProperties pp : rank) {
                pp.setUserProfileImg(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setCommentCount(postMapper.getPostCommentCount(pp.getCommunityId()));
                pp.setMemId(conditions.getMemId());
                pp.setFollowingCheck(postMapper.getPostFollow(pp));


                mp.setCommunityId(pp.getCommunityId());
                String myLike = postMapper.getSelectPostDetailLiked(mp);
                if(pp.getCommunityId().equals(myLike)) {
                    mp.setLiked(true);
                }
                else{
                    mp.setLiked(false);
                }
            }

            pageInfo.setList(rank);

        }
        else if(conditions.getType().equals("follow")){

            int nonpagedCount = postMapper.getPostMyFollowCount(conditions);
            pageInfo = new PageInfo<>(conditions,nonpagedCount);

            List<PostProperties> follow = postMapper.getMorePostMyFollow(conditions);
            for (PostProperties pp : follow) {
                pp.setMemId(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setMemId(conditions.getMemId());
                pp.setFollowingCheck(true);

                mp.setCommunityId(pp.getCommunityId());
                String myLike = postMapper.getSelectPostDetailLiked(mp);
                if(pp.getCommunityId().equals(myLike)) {
                    mp.setLiked(true);
                }
                else{
                    mp.setLiked(false);
                }

            }
            pageInfo.setList(follow);


        }
        else if(conditions.getType().equals("custom")){

            int nonpagedCount = postMapper.getMorePostCustomCount(conditions);
            pageInfo = new PageInfo<>(conditions,nonpagedCount);

            List<PostProperties> custom = postMapper.getMorePostCustom(conditions);
            for (PostProperties pp : custom) {
                pp.setMemId(CMMUtil.getImg(pp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(pp.getCommunityImage()));
                pp.setMemId(conditions.getMemId());
                pp.setFollowingCheck(postMapper.getPostFollow(pp));

                mp.setCommunityId(pp.getCommunityId());
                String myLike = postMapper.getSelectPostDetailLiked(mp);
                if(pp.getCommunityId().equals(myLike)) {
                    mp.setLiked(true);
                }
                else{
                    mp.setLiked(false);
                }
            }
            pageInfo.setList(custom);


        }
        return pageInfo;

    }








    public PostMain getNPostMain(){

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
            pp.setFollowingCheck(postMapper.getPostFollow(pp));
            pp.setLiked(false);
        }

        pm.setRanks(rank);



        return pm;
    }



    public PageInfo<PostProperties> getNMorePostMain(PostConditions conditions){

        PageInfo<PostProperties> pageInfo = null;

        if(conditions.getType().equals("rank")){

            int nonpagedCount = postMapper.getMorePostCount();
            pageInfo = new PageInfo<>(conditions,nonpagedCount);

            List<PostProperties> rank = postMapper.getMorePostLike(conditions);
            for (PostProperties pp : rank) {
                PostProperties mypp = postMapper.getPostDetail(pp.getCommunityId());
                pp.setCommunityTitle(mypp.getCommunityTitle());
                pp.setUserId(mypp.getUserId());
                pp.setUserNick(mypp.getUserNick());
                pp.setUserProfileImg(CMMUtil.getImg(mypp.getUserProfileImg()));
                pp.setCommunityImage(CMMUtil.getImg(mypp.getCommunityImage()));
                pp.setCommentCount(postMapper.getPostCommentCount(pp.getCommunityId()));
                pp.setFollowingCheck(postMapper.getPostFollow(pp));
                pp.setLiked(false);
            }

            pageInfo.setList(rank);

        }

        return pageInfo;

    }









    public PageInfo<PostComment> getPostComments(PostCommentConditions conditions){


        List<PostComment> myComm = postMapper.getPostComment(conditions);
        int nonpagedCount = postMapper.getPostCommentCount(conditions.getCommunityId());
        PageInfo<PostComment> pageInfo = new PageInfo<>(conditions,nonpagedCount);

        for (PostComment pc : myComm) {
            pc.setWriterProfileImgUrl(CMMUtil.getImg(pc.getWriterProfileImgUrl()));
        }
        pageInfo.setObjectData(conditions.getCommunityId());
        pageInfo.setTotalCount(nonpagedCount);

        pageInfo.setList(myComm);


        return pageInfo;
    }




    public void regPostComments(PostComment pc){

        pc.setPostCommentId(CMMUtil.nextId("pcId"));

        postMapper.insertPostComment(pc);

    }



    public PostDetail getPostDetail(PostDetail pd){

        MyPost mp = postMapper.getSelectPostDetail(pd.getCommunityPostId());
        mp.setPostImg(CMMUtil.getImg(mp.getPostImg()));
        mp.setLiked(false);

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
        pd.setCommunityPostIsUserWrtitten(false);

        return pd;
    }




    public PostDetail getMPostDetail(PostDetail pd){

        MyPost mp = postMapper.getSelectPostDetail(pd.getCommunityPostId());
        mp.setPostImg(CMMUtil.getImg(mp.getPostImg()));

        if (mp.getMemId().equals(pd.getMemId())){
            pd.setCommunityPostIsUserWrtitten(true);
        }
        else{
            pd.setCommunityPostIsUserWrtitten(false);
        }


        String myLike = postMapper.getSelectPostDetailLiked(mp);
        if(pd.getCommunityPostId().equals(myLike)) {
            mp.setLiked(true);
        }
        else{
            mp.setLiked(false);
        }

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


    public void likePost(Post post){
        postMapper.likePost(post);
    }

    public void dislikePost(Post post){
        postMapper.dislikePost(post);
    }


//end
}
