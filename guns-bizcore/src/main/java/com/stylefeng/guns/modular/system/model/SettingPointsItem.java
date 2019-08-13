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
 * 积分项目设置
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_setting_points_item")
public class SettingPointsItem extends Model<SettingPointsItem> {

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
     * 积分项目英文名
     */
    private String name;
    /**
     * 积分项目中文名
     */
    private String title;
    /**
     * 每日可赠送次数
     */
    private String dayTimes;
    /**
     *  赠送方式,0赠送固定数 1按消费额赠送
     */
    private Integer type;
    /**
     * 赠送数量
     */
    private Integer num;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(String dayTimes) {
        this.dayTimes = dayTimes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SettingPointsItem{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", name=" + name +
        ", title=" + title +
        ", dayTimes=" + dayTimes +
        ", type=" + type +
        ", num=" + num +
        "}";
    }
}
