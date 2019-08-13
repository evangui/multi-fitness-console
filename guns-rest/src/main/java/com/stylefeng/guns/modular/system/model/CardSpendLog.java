package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * @author guiyj007
 * @since 2018-08-18
 */
@TableName("biz_card_spend_log")
public class CardSpendLog extends Model<CardSpendLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 卡类型
     */
    @TableField("log_type")
    private Integer logType;
    
    public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

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
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    
	/**
     * 次卡id
     */
    @TableField("card_id")
    private Integer cardId;
    
    private String title;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
     * 教练id
     */
    @TableField("coach_id")
    private Integer coachId;
    /**
     * 教练姓名
     */
    @TableField("coach_name")
    private String coachName;
    public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	/**
     * 会籍id
     */
    @TableField("membership_id")
    private Integer membershipId;
    
    @TableField("membership_name")
    private String membershipName;
    
    public String getMembershipName() {
		return membershipName;
	}

	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}

	/**
     * 前台id
     */
    @TableField("reception_id")
    private Integer receptionId;
    
    @TableField("rcepteion_name")
    private String rcepteionName;
    
    
    public String getRcepteionName() {
		return rcepteionName;
	}

	public void setRcepteionName(String rcepteionName) {
		this.rcepteionName = rcepteionName;
	}

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
    private BigDecimal unitPrice;
    /**
     * 扣除次数
     */
    private Integer costs;
    @TableField("last_counts")
    private Integer lastCounts;
    
    public Integer getLastCounts() {
		return lastCounts;
	}

	public void setLastCounts(Integer lastCounts) {
		this.lastCounts = lastCounts;
	}

	/**
     * 扣除金额
     */
    @TableField("cost_money")
    private BigDecimal costMoney;
    
    public BigDecimal getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(BigDecimal costMoney) {
		this.costMoney = costMoney;
	}
	
	@TableField("last_money")
    private BigDecimal lastMoney;
	

	public BigDecimal getLastMoney() {
		return lastMoney;
	}

	public void setLastMoney(BigDecimal lastMoney) {
		this.lastMoney = lastMoney;
	}

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
    
    /**
     * 来源端口
     */
    private Integer port;


    public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

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

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal bigDecimal) {
        this.unitPrice = bigDecimal;
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
        ", cardId=" + cardId +
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
