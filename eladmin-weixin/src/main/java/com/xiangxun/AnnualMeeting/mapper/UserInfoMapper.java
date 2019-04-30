package com.xiangxun.AnnualMeeting.mapper;

import com.xiangxun.AnnualMeeting.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface UserInfoMapper
{
    public abstract List<UserInfo> selectAll();

    public abstract Integer addUser(UserInfo paramUserInfo);

    public abstract Integer saveWxUser(UserInfo paramUserInfo);

    public Integer saveUser(UserInfo userInfo);

    public Integer saveUserAndRole(ArrayList list);

    public abstract UserInfo getSignByuserId(String paramString);

    public abstract Integer[] getLeftPerson();

    public abstract Integer[] getDengjiPerson(String paramString);

    public abstract void saveTheData(Map paramMap);

    public abstract List<UserInfo> getAllUserInfoByConditation(String paramString);

    public abstract List<UserInfo> getAllUserInfoByConditation();

    public abstract Integer sedengji(Map paramMap);

    public abstract Integer chongzhi();

    public List<Map> findAllRoles();
}
