package com.xiangxun.AnnualMeeting.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.github.binarywang.demo.wx.cp.utils.JsonUtils;
import com.xiangxun.AnnualMeeting.bean.UserInfo;
import com.xiangxun.AnnualMeeting.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpOAuth2Service;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.Gender;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnnRestController
{
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/data"})
    @ResponseBody
    public List<UserInfo> testData()
    {
        return this.userService.getAllUserInfo();
    }

    @RequestMapping({"/toqrcode/{agentId}"})
    public String toqrcode(@PathVariable Integer agentId, HttpServletRequest request, Model model)
    {
        WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        String qrcode = wxCpService.getOauth2Service().buildAuthorizationUrl("http://avic.631xiangxun.com:8000/tologin/" + String.valueOf(agentId), request.getSession().getId(), "snsapi_base");
        model.addAttribute("qcode", qrcode);
        return "logincode";
    }

    @RequestMapping({"/personlist"})
    public String personlist(HttpServletRequest request, Model model)
    {
        List<UserInfo> list = new ArrayList();
        list = this.userService.getAllPersonInfoByConditation();

        model.addAttribute("list", JsonUtils.toJson(list));
        return "personlist";
    }

    @RequestMapping({"/tologin/{agentId}"})
    public String tologin(@PathVariable Integer agentId, HttpServletRequest request, Model model)
    {
        WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
        String qrcode = wxCpService.getOauth2Service().buildAuthorizationUrl("http://avic.631xiangxun.com:8000/tologin/" + String.valueOf(agentId), request.getSession().getId(), "snsapi_base");
        String code = request.getParameter("code");
        WxCpUser wxCpUser = new WxCpUser();
        try
        {
            this.logger.info("测试用户");
            if ((code == null) || ("".equalsIgnoreCase(code)))
            {
                wxCpUser.setAvatar("http://www.631xiangxun.com/template/week_education/images/logo.png");
                wxCpUser.setName("测试zz");

                wxCpUser.setUserId("qq");
                wxCpUser.setMobile("029881513xx");
                wxCpUser.setGender(Gender.FEMALE);
                wxCpUser.setDepartIds(new Integer[] { Integer.valueOf(1) });
                return "redirect:" + qrcode;
            }
            String[] data = wxCpService.getOauth2Service().getUserInfo(code);
            wxCpUser = wxCpService.getUserService().getById(data[0]);
        }
        catch (WxErrorException e)
        {
            e.printStackTrace();
            return "redirect:" + qrcode;
        }
        UserInfo sign = this.userService.getSignByuserId(wxCpUser.getUserId());

        model.addAttribute("userInfo", sign);
        model.addAttribute("sign", Integer.valueOf(null == sign ? 0 : 1));
        model.addAttribute("gender", wxCpUser.getGender().getCode());
        model.addAttribute("departIds", arrayToString(wxCpUser.getDepartIds()));
        model.addAttribute("wxCpUser", wxCpUser);
        this.logger.info(String.valueOf(sign));
        return "tologin";
    }

    @RequestMapping({"/login"})
    @ResponseBody
    public UserInfo login(UserInfo userInfo, HttpServletRequest request, Model model)
    {
        UserInfo sign = this.userService.getSignByuserId(userInfo.getOpenId());
        if (null != sign) {
            return sign;
        }
        if ("".equalsIgnoreCase(userInfo.getSignMessage())) {
            userInfo.setSignMessage("签到！");
        }
        userInfo.setSign(1);
        userInfo.setNickName(userInfo.getName());
        int ret = this.userService.addUser(userInfo).intValue();
        this.logger.info(userInfo.getName());
        this.logger.info(userInfo.getSignMessage());
        return userInfo;
    }

    @RequestMapping({"/suiJi/{count}"})
    @ResponseBody
    public Integer[] suiJi(@PathVariable Integer count, HttpServletRequest request, Model model)
    {
        this.logger.info(count.toString());
        String dengji = request.getParameter("dengji");
        Integer[] ret = this.userService.random(count, dengji);
        return ret;
    }

    public static String arrayToString(Integer[] data)
    {
        StringBuffer sb = new StringBuffer("");
        for (Integer i : data)
        {
            sb.append(i.toString());
            sb.append(",");
        }
        if (data.length == 0) {
            return "null";
        }
        return sb.subSequence(0, sb.length() - 1).toString();
    }

    @RequestMapping({"/towaigua"})
    public String towaigua(HttpServletRequest request, Model model)
    {
        return "towaigua";
    }

    @RequestMapping({"/waigua"})
    public String waigua(HttpServletRequest request, Model model)
    {
        String name = request.getParameter("name");
        List<UserInfo> list = new ArrayList();
        if ((!"".equalsIgnoreCase(name)) && (name != null)) {
            list = this.userService.getAllUserInfoByConditation(name);
        }
        model.addAttribute("list", list);
        return "userlist";
    }

    @RequestMapping({"/sedengji"})
    @ResponseBody
    public String sedengji(HttpServletRequest request, Model model)
    {
        String id = request.getParameter("id");
        String dengji = request.getParameter("dengji");
        Integer ret = this.userService.sedengji(Integer.valueOf(id), dengji);

        return String.valueOf(ret);
    }

    @RequestMapping({"/chongzhi"})
    @ResponseBody
    public String chongzhi(HttpServletRequest request, Model model)
    {
        Integer ret = this.userService.chongzhi();

        return String.valueOf(ret);
    }
}
