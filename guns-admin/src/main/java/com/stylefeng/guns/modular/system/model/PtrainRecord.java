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
 * 私教记录
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-18
 */
@TableName("biz_ptrain_record")
public class PtrainRecord extends Model<PtrainRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部id
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 会员id
     */
    @TableField("vip_id")
    private Integer vipId;
    /**
     * 次卡id
     */
    @TableField("once_card_id")
    private Integer onceCardId;
    /**
     * 教练id
     */
    @TableField("coach_id")
    private Integer coachId;
    /**
     * 会籍id
     */
    @TableField("membership_id")
    private Integer membershipId;
    /**
     * 前台id
     */
    @TableField("reception_id")
    private Integer receptionId;
    private String remark;
    /**
     * 前台备注
     */
    @TableField("reception_remark")
    private String receptionRemark;
    /**
     * 单价
     */
    @TableField("unit_price")
    private Integer unitPrice;
    /**
     * 扣除次数
     */
    private Integer costs;
    /**
     * 类型，含义待定
     */
    private Integer type;
    /**
     * 课程id or 私教预约记录id
     */
    @TableField("syllabus_id")
    private Integer syllabusId;
    /**
     * 是否是团操课预约-用以区分团操课or私教课
     */
    @TableField("is_syllabus_sub")
    private Integer isSyllabusSub;
    /**
     * 用户姓名
     */
    @TableField("user_realname")
    private String userRealname;
    /**
     * 用户ip
     */
    @TableField("ip_address")
    private String ipAddress;
    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 用户评价
     */
    @TableField("comment_content")
    private String commentContent;
    /**
     * 评分
     */
    @TableField("comment_rank")
    private Integer commentRank;
    /**
     * 评论标签，多个用,分隔
     */
    @TableField("comment_tags")
    private String commentTags;
    /**
     * 确认码-标志记录的唯一性-通常在记录id不明确时由客户端生成
     */
    @TableField("conform_code")
    private String conformCode;


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

    public Integer getOnceCardId() {
        return onceCardId;
    }

    public void setOnceCardId(Integer onceCardId) {
        this.onceCardId = onceCardId;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public Integer getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Integer receptionId) {
        this.receptionId = receptionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceptionRemark() {
        return receptionRemark;
    }

    public void setReceptionRemark(String receptionRemark) {
        this.receptionRemark = receptionRemark;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCosts() {
        return costs;
    }

    public void setCosts(Integer costs) {
        this.costs = costs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSyllabusId() {
        return syllabusId;
    }

    public void setSyllabusId(Integer syllabusId) {
        this.syllabusId = syllabusId;
    }

    public Integer getIsSyllabusSub() {
        return isSyllabusSub;
    }

    public void setIsSyllabusSub(Integer isSyllabusSub) {
        this.isSyllabusSub = isSyllabusSub;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getCommentRank() {
        return commentRank;
    }

    public void setCommentRank(Integer commentRank) {
        this.commentRank = commentRank;
    }

    public String getCommentTags() {
        return commentTags;
    }

    public void setCommentTags(String commentTags) {
        this.commentTags = commentTags;
    }

    public String getConformCode() {
        return conformCode;
    }

    public void setConformCode(String conformCode) {
        this.conformCode = conformCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PtrainRecord{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", vipId=" + vipId +
        ", onceCardId=" + onceCardId +
        ", coachId=" + coachId +
        ", membershipId=" + membershipId +
        ", receptionId=" + receptionId +
        ", remark=" + remark +
        ", receptionRemark=" + receptionRemark +
        ", unitPrice=" + unitPrice +
        ", costs=" + costs +
        ", type=" + type +
        ", syllabusId=" + syllabusId +
        ", isSyllabusSub=" + isSyllabusSub +
        ", userRealname=" + userRealname +
        ", ipAddress=" + ipAddress +
        ", userPhone=" + userPhone +
        ", insertTime=" + insertTime +
        ", commentContent=" + commentContent +
        ", commentRank=" + commentRank +
        ", commentTags=" + commentTags +
        ", conformCode=" + conformCode +
        "}";
    }
}
