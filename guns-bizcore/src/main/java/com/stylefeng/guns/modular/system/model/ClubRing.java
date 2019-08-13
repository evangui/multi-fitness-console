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
 * 手环
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_club_ring")
public class ClubRing extends Model<ClubRing> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 手环编号
     */
    @TableField("ring_num")
    private String ringNum;
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
    private String insertTime;
    /**
     * 入场记录id
     */
    @TableField("admission_record_id")
    private Integer admissionRecordId;
    /**
     * 入场备注
     */
    @TableField("admission_remark")
    private Integer admissionRemark;
    /**
     * 借出时间
     */
    @TableField("borrow_time")
    private String borrowTime;
    /**
     * 归还时间
     */
    @TableField("return_time")
    private String returnTime;
    /**
     * 借出手环是否未还
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRingNum() {
        return ringNum;
    }

    public void setRingNum(String ringNum) {
        this.ringNum = ringNum;
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

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getAdmissionRecordId() {
        return admissionRecordId;
    }

    public void setAdmissionRecordId(Integer admissionRecordId) {
        this.admissionRecordId = admissionRecordId;
    }

    public Integer getAdmissionRemark() {
        return admissionRemark;
    }

    public void setAdmissionRemark(Integer admissionRemark) {
        this.admissionRemark = admissionRemark;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ClubRing{" +
        "id=" + id +
        ", ringNum=" + ringNum +
        ", clubId=" + clubId +
        ", vipId=" + vipId +
        ", realname=" + realname +
        ", phone=" + phone +
        ", insertTime=" + insertTime +
        ", admissionRecordId=" + admissionRecordId +
        ", admissionRemark=" + admissionRemark +
        ", borrowTime=" + borrowTime +
        ", returnTime=" + returnTime +
        ", status=" + status +
        "}";
    }
}
