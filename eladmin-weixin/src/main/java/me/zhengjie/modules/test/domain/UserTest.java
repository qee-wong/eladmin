package me.zhengjie.modules.test.domain;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-04-03
*/
@Entity
@Data
@Table(name="user_test")
public class UserTest implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "employeeid")
    private String employeeid;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 姓名
     */
    @Column(name = "name",nullable = false)
    private String name;

    /**
     * ;0-1-
     */
    @Column(name = "sign")
    private Integer sign;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatarurl")
    private String avatarurl;

    /**
     * 0-1-
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * openid
     */
    @Column(name = "openid")
    private String openid;

    @Column(name = "updatetime")
    private Timestamp updatetime;

    /**
     * 留言
     */

    @Column(name = "signmessage")
    private String signMessage;
    /**
     * 等级
     */
    @Column(name = "dengji")
    private String dengji;

    /**
     * 标记
     */
    private String biaoji;



}