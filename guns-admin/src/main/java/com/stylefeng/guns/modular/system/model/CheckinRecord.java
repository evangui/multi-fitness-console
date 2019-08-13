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
 * 签到记录
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_checkin_record")
public class CheckinRecord extends Model<CheckinRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部id
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 用户id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 录入时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 最近签到时间
     */
    @TableField("last_check_in_time")
    private Integer lastCheckInTime;
    /**
     * 签退时间
     */
    @TableField("check_out_time")
    private Integer checkOutTime;
    /**
     * 入场次数
     */
    @TableField("admission_record_count")
    private Integer admissionRecordCount;
    /**
     * 代签用户ID
     */
    @TableField("ope_user_id")
    private Integer opeUserId;
    /**
     * 代签用户名
     */
    @TableField("ope_username")
    private String opeUsername;
    /**
     * 入场状态 3已入场  2已出场
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 财务是否确认
     */
    private Integer banksure;
    /**
     * 财务备注
     */
    @TableField("bank_remark")
    private String bankRemark;
    /**
     * 借出手环是否未还
     */
    @TableField("ring_status")
    private Integer ringStatus;
    
	/**
     * 手环号码
     */
    @TableField("ring_num")
    private String ringNum;
    /**
     * 消次端口 0默认 1前台电脑签到 2前台手机签到 3扫一扫签到 4指纹门禁签到 5智能门禁签到
     */
    private Integer port;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getLastCheckInTime() {
        return lastCheckInTime;
    }

    public void setLastCheckInTime(Integer lastCheckInTime) {
        this.lastCheckInTime = lastCheckInTime;
    }

    public Integer getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Integer checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getAdmissionRecordCount() {
        return admissionRecordCount;
    }

    public void setAdmissionRecordCount(Integer admissionRecordCount) {
        this.admissionRecordCount = admissionRecordCount;
    }

    public Integer getOpeUserId() {
        return opeUserId;
    }

    public void setOpeUserId(Integer opeUserId) {
        this.opeUserId = opeUserId;
    }

    public String getOpeUsername() {
        return opeUsername;
    }

    public void setOpeUsername(String opeUsername) {
        this.opeUsername = opeUsername;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getBanksure() {
        return banksure;
    }

    public void setBanksure(Integer banksure) {
        this.banksure = banksure;
    }

    public String getBankRemark() {
        return bankRemark;
    }

    public void setBankRemark(String bankRemark) {
        this.bankRemark = bankRemark;
    }

	public String getRingNum() {
		return ringNum;
	}

	public void setRingNum(String ringNum2) {
		this.ringNum = ringNum2;
	}
    public Integer getRingStatus() {
        return ringStatus;
    }

    public void setRingStatus(Integer ringStatus) {
        this.ringStatus = ringStatus;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CheckinRecord{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", vipId=" + vipId +
        ", realname=" + realname +
        ", phone=" + phone +
        ", insertTime=" + insertTime +
        ", lastCheckInTime=" + lastCheckInTime +
        ", checkOutTime=" + checkOutTime +
        ", admissionRecordCount=" + admissionRecordCount +
        ", opeUserId=" + opeUserId +
        ", opeUsername=" + opeUsername +
        ", status=" + status +
        ", remark=" + remark +
        ", banksure=" + banksure +
        ", bankRemark=" + bankRemark +
        ", ringStatus=" + ringStatus +
        ", port=" + port +
        "}";
    }
}
