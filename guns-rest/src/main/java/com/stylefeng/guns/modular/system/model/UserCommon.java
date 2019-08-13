package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author guiyj007123
 * @since 2018-06-22
 */
@TableName("biz_user_common")
public class UserCommon extends Model<UserCommon> {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员密码
     */
    private String passwd;
    
    /**
     * 绑定的vip用户id
     */
    @TableField("vip_id")
    private Integer vipId;

	/**
     * 姓名
     */
    private String realname;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 会员头像
     */
    private String avatar;
    /**
     * 会员性别（0：女，1：男）
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 国家号码编号
     */
    private String country;
    @TableField("weixin_openid")
    private String weixinOpenid;
    /**
     * 微信用户统一标识
     */
    @TableField("weixin_unionid")
    private String weixinUnionid;
    /**
     * 微信用户相关信息
     */
    @TableField("weixin_info")
    private String weixinInfo;
    /**
     * 登录次数
     */
    @TableField("login_num")
    private Integer loginNum;
    /**
     * 用户注册IP
     */
    @TableField("reg_ip")
    private String regIp;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 上次活动时间
     */
    @TableField("last_active_time")
    private Integer lastActiveTime;
    /**
     * 用户标签
     */
    private String labels;
    /**
     * 是否系统虚拟用户
     */
    private Integer virtual;
    
    private Integer role;
    
    @TableField("is_universal")
    private Integer isUniversal;

	public Integer getIsUniversal() {
		return isUniversal;
	}

	public void setIsUniversal(Integer isUniversal) {
		this.isUniversal = isUniversal;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getVipId() {
		return vipId;
	}

	public void setVipId(Integer vipId) {
		this.vipId = vipId;
	}
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    public String getWeixinUnionid() {
        return weixinUnionid;
    }

    public void setWeixinUnionid(String weixinUnionid) {
        this.weixinUnionid = weixinUnionid;
    }

    public String getWeixinInfo() {
        return weixinInfo;
    }

    public void setWeixinInfo(String weixinInfo) {
        this.weixinInfo = weixinInfo;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Integer lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
    }
    
    public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
    
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserCommon{" +
        "id=" + id +
        ", vipId=" + vipId +
        ", passwd=" + passwd +
        ", realname=" + realname +
        ", nickname=" + nickname +
        ", avatar=" + avatar +
        ", gender=" + gender +
        ", phone=" + phone +
        ", country=" + country +
        ", weixinOpenid=" + weixinOpenid +
        ", weixinUnionid=" + weixinUnionid +
        ", weixinInfo=" + weixinInfo +
        ", loginNum=" + loginNum +
        ", regIp=" + regIp +
        ", insertTime=" + insertTime +
        ", lastActiveTime=" + lastActiveTime +
        ", labels=" + labels +
        ", virtual=" + virtual +
        ", role=" + role +
        "}";
    }
}
