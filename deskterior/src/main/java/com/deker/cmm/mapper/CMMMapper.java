package com.deker.cmm.mapper;

import com.deker.cmm.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CMMMapper {
    List<CMM> selectCode(CMMConditions conditions);
    void updateIds(String tableName);
    Integer selectIds(String tableName);
    void insertImg(ImgConditions conditions);
    Img selectImg(String imgId);


    List<Menu> getMenu(String authorityCode);
    String getUserRole(String memId);

    void follow(Follow follow);
    void unFollow(Follow follow);

    int getFollowingCount(String memId);
    List<Follow> getFollowing(Follow follow);

    List<Follow> getFollower(Follow follow);
    int getFollowerCount(String memId);

    void deleteFollowMe(Follow follow);
}
