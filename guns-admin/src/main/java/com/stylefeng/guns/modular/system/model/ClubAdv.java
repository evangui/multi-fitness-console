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
 * 广告表
 * </p>
 *
 * @author guiyj007123
 * @since 2018-10-10
 */
@TableName("biz_club_adv")
public class ClubAdv extends Model<ClubAdv> {

    private static final long serialVersionUID = 1L;

    /**
     * 广告自增标识编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部ID
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 广告位id
     */
    @TableField("pos_id")
    private Integer posId;
    /**
     * 广告位名称
     */
    @TableField("pos_name")
    private String posName;
    /**
     * 0：(跳转到链接)，1:(跳转到详情),2(跳转到列表),3(跳转到文章)
     */
    @TableField("adv_url_type")
    private Integer advUrlType;
    /**
     * 广告标题
     */
    @TableField("adv_title")
    private String advTitle;
    /**
     * 广告类别：0图片1文字
     */
    private Integer type;
    /**
     * 广告内容
     */
    private String content;
    /**
     * 广告开始时间
     */
    private Integer starttime;
    /**
     * 广告结束时间
     */
    private Integer endtime;
    /**
     * 广告排序
     */
    private Integer sort;
    /**
     * 广告点击数
     */
    @TableField("click_num")
    private Integer clickNum;


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

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getAdvUrlType() {
        return advUrlType;
    }

    public void setAdvUrlType(Integer advUrlType) {
        this.advUrlType = advUrlType;
    }

    public String getAdvTitle() {
        return advTitle;
    }

    public void setAdvTitle(String advTitle) {
        this.advTitle = advTitle;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStarttime() {
        return starttime;
    }

    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }

    public Integer getEndtime() {
        return endtime;
    }

    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ClubAdv{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", posId=" + posId +
        ", posName=" + posName +
        ", advUrlType=" + advUrlType +
        ", advTitle=" + advTitle +
        ", type=" + type +
        ", content=" + content +
        ", starttime=" + starttime +
        ", endtime=" + endtime +
        ", sort=" + sort +
        ", clickNum=" + clickNum +
        "}";
    }
}
