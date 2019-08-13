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
 * 维护记录
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_maintain_record")
public class MaintainRecord extends Model<MaintainRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * vip id
     */
    @TableField("vip_user_id")
    private Integer vipUserId;
    /**
     * 是否是vip用户 0否 1是
     */
    @TableField("vip_user_type")
    private Integer vipUserType;
    /**
     * 会籍ID
     */
    @TableField("membership_id")
    private Integer membershipId;
    /**
     * 跟进内容说明
     */
    private String content;
    /**
     * 是否是待办 0否 1是
     */
    @TableField("is_todo")
    private Integer isTodo;
    /**
     * 跟进类型 0潜在客户跟进 3vip用户跟进
     */
    @TableField("record_type")
    private Integer recordType;
    /**
     * 状态,字段待定
     */
    private Integer status;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private String insertTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipUserId() {
        return vipUserId;
    }

    public void setVipUserId(Integer vipUserId) {
        this.vipUserId = vipUserId;
    }

    public Integer getVipUserType() {
        return vipUserType;
    }

    public void setVipUserType(Integer vipUserType) {
        this.vipUserType = vipUserType;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsTodo() {
        return isTodo;
    }

    public void setIsTodo(Integer isTodo) {
        this.isTodo = isTodo;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MaintainRecord{" +
        "id=" + id +
        ", vipUserId=" + vipUserId +
        ", vipUserType=" + vipUserType +
        ", membershipId=" + membershipId +
        ", content=" + content +
        ", isTodo=" + isTodo +
        ", recordType=" + recordType +
        ", status=" + status +
        ", insertTime=" + insertTime +
        "}";
    }
}
