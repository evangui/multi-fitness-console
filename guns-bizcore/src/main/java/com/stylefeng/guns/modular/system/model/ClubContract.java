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
 * 俱乐部合同
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_club_contract")
public class ClubContract extends Model<ClubContract> {

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
     * 合同名称
     */
    private String title;
    /**
     * 合同类型 1时间卡合同  2私教卡合同 3次卡合同
     */
    private Integer type;
    /**
     * 合同编号
     */
    @TableField("contract_num")
    private String contractNum;
    /**
     * 是否隐藏
     */
    private Integer hidden;
    /**
     * 添加时间
     */
    private String insertTime;
    /**
     * 合同正文
     */
    private String content;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ClubContract{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", title=" + title +
        ", type=" + type +
        ", contractNum=" + contractNum +
        ", hidden=" + hidden +
        ", insertTime=" + insertTime +
        ", content=" + content +
        "}";
    }
}
