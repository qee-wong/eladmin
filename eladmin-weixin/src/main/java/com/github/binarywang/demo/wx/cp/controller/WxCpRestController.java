package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.xiangxun.AnnualMeeting.bean.UserInfo;
import com.xiangxun.AnnualMeeting.service.UserService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.zhengjie.modules.test.domain.UserTest;
import me.zhengjie.modules.test.service.UserTestService;
import me.zhengjie.modules.test.service.dto.UserTestDTO;
import me.zhengjie.security.AuthenticationInfo;
import me.zhengjie.security.JwtUser;
import me.zhengjie.security.util.JwtTokenUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletRequest;
import java.sql.Timestamp;
import java.util.*;


@RestController
public class WxCpRestController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserTestService userTestService;

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
        String userId = req.getParameter("userId");
        if(code !=null)
            System.out.printf(code);
        WxCpUser wxCpUser = new WxCpUser();
        List<Map> roles = null;
        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        WxJsapiSignature wxJsapiSignature = null;
        try {
            if(code !=null) {
                String[] data = wxCpService.getOauth2Service().getUserInfo(code);
                wxCpUser = wxCpService.getUserService().getById(data[0]);
            }
            if(userId != null){
                wxCpUser = wxCpService.getUserService().getById(userId);
            }

            roles =  userService.findAllRoles();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        try {
            final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(wxCpUser.getUserId());
            // 生成令牌
            final String token = jwtTokenUtil.generateToken(jwtUser);
            // 返回 token
            return ResponseEntity.ok(new AuthenticationInfo(token,jwtUser));
        }catch (Exception jwtUser){
            Map data  = new HashMap<String, Object>();
            data.put("token","no_token");
            data.put("user",wxCpUser);
            data.put("roles",roles);
            return ResponseEntity.ok(data);
        }

    }


    @PostMapping({"/jspapi/addUser/{agentId}"})
    public UserInfo addUser(@PathVariable Integer agentId,@RequestBody WxCpUser wxCpUser, ServletRequest req)
    {

        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        //保存微信用户
        /*UserTest userTest = new UserTest();
        userTest.setAvatarurl(wxCpUser.getAvatar());
        userTest.setOpenid(wxCpUser.getUserId());
        userTest.setName(wxCpUser.getName());
        userTest.setEmployeeid(ArrayUtils.toString(wxCpUser.getDepartIds(), ","));
        userTest.setSex(wxCpUser.getGender().toString());
        userTest.setPhone(wxCpUser.getTelephone());
        UserTestDTO userTestDTO =userTestService.create(userTest);*/
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatarUrl(wxCpUser.getAvatar());
        userInfo.setOpenId(wxCpUser.getUserId());
        userInfo.setName(wxCpUser.getName());
        userInfo.setEmployeeid(ArrayUtils.toString(wxCpUser.getDepartIds(), ","));
        userInfo.setSex(wxCpUser.getGender().toString());
        userInfo.setPhone(wxCpUser.getTelephone());

        int ret = userService.saveWxUser(userInfo).intValue();
        //保存user用户信息和角色
        userInfo.setEmail(wxCpUser.getEmail());
        userInfo.setPassword("40f1be2cd897b1f101e5257253178638");
        userInfo.setEnabled(1);
        List roleids = wxCpUser.getExtAttrs();
        ArrayList urlist  = new ArrayList();
        roleids.forEach((item)->{
            Map userAndRole = new HashMap();
            userAndRole.put("userId",userInfo.getId());
            userAndRole.put("roleId",((WxCpUser.Attr) item).getValue());
            urlist.add(userAndRole);
        });
        userService.saveUser(userInfo,urlist);

        return userInfo;
    }
}
