package com.xiangxun.AnnualMeeting.bean;

import java.util.Date;

public class UserInfo
{
    private int id;
    private String name;
    private String phone;
    private String avatarUrl;
    private int sign;
    private String nickName;
    private String signMessage;
    private String employeeid;
    private int gender;
    private Date updatetime;
    private String openId;
    private String biaoji;
    private String dengji;

    public Date getUpdatetime()
    {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }

    public int getGender()
    {
        return this.gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getEmployeeid()
    {
        return this.employeeid;
    }

    public void setEmployeeid(String employeeid)
    {
        this.employeeid = employeeid;
    }

    public String getOpenId()
    {
        return this.openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public int getSign()
    {
        return this.sign;
    }

    public void setSign(int sign)
    {
        this.sign = sign;
    }

    public String getNickName()
    {
        return this.nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getSignMessage()
    {
        return this.signMessage;
    }

    public void setSignMessage(String signMessage)
    {
        this.signMessage = signMessage;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAvatarUrl()
    {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getBiaoji()
    {
        return this.biaoji;
    }

    public void setBiaoji(String biaoji)
    {
        this.biaoji = biaoji;
    }

    public String getDengji()
    {
        return this.dengji;
    }

    public void setDengji(String dengji)
    {
        this.dengji = dengji;
    }
}
