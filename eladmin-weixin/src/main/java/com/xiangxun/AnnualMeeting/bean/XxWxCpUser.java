package com.xiangxun.AnnualMeeting.bean;

import me.chanjar.weixin.cp.bean.WxCpUser;

public class XxWxCpUser
    extends WxCpUser
{
    private String signMessage;

    public String getSignMessage()
    {
        return this.signMessage;
    }

    public void setSignMessage(String signMessage)
    {
        this.signMessage = signMessage;
    }
}
