package me.zhengjie.modules.test.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-04-03
*/
@Data
public class UserTestDTO implements Serializable {

    /**
     * ID
     */
    private Integer id;

    private String employeeid;

    /**
     * 电话
     */
    private String phone;

    /**
     * 姓名
     */
    private String name;

    /**
     * ;0-1-
     */
    private Integer sign;

    /**
     * 昵称
     */
    private String nickname;

    private String avatarurl;

    /**
     * 0-1-
     */
    private Integer gender;

    /**
     * openid
     */
    private String openid;

    private Timestamp updatetime;

    /**
     * 留言
     */
    private String signMessage;

    /**
     * 等级
     */
    private String dengji;

    /**
     * 标记
     */
    private String biaoji;





}