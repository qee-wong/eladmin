package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.xiangxun.AnnualMeeting.bean.UserInfo;
import com.xiangxun.AnnualMeeting.service.UserService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.zhengjie.security.AuthenticationInfo;
import me.zhengjie.security.JwtUser;
import me.zhengjie.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
public class WxCpRestController
{
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping({"/jspapi/{agentId}"})
    public WxJsapiSignature jsapiTest(@PathVariable Integer agentId, ServletRequest req)
    {
//        List<UserInfo> list = new ArrayList();
//        list = this.userService.getAllPersonInfoByConditation();
        String url_p = req.getParameter("url");
        if(url_p !=null)
            System.out.printf(url_p);

        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        WxJsapiSignature wxJsapiSignature = null;
        try {
            wxJsapiSignature = wxCpService.createJsapiSignature(url_p);

        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return wxJsapiSignature;
    }

    @GetMapping({"/jspapi/{agentId}"})
    public ResponseEntity jsapiTestCode(@PathVariable Integer agentId, ServletRequest req)
    {
//        List<UserInfo> list = new ArrayList();
//        list = this.userService.getAllPersonInfoByConditation();
        String code = req.getParameter("code");
        if(code !=null)
            System.out.printf(code);
        WxCpUser wxCpUser = new WxCpUser();
        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        WxJsapiSignature wxJsapiSignature = null;
        try {
            String[] data = wxCpService.getOauth2Service().getUserInfo(code);
            wxCpUser = wxCpService.getUserService().getById(data[0]);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(wxCpUser.getUserId());
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
    }
}
