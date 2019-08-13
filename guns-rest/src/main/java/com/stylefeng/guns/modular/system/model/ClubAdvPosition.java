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
 * 广告位表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-10-10
 */
@TableName("biz_club_adv_position")
public class ClubAdvPosition extends Model<ClubAdvPosition> {

    private static final long serialVersionUID = 1L;

    /**
     * 广告位置id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 俱乐部ID
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 广告位置名
     */
    private String name;
    /**
     * 广告类别：0图片1文字2幻灯3Flash
     */
    private Integer type;
    /**
     * 广告展示方式：0幻灯片1多广告展示2单广告展示
     */
    private Integer display;
    /**
     * 广告位是否启用：0不启用1启用
     */
    private Integer enable;
    /**
     * 广告位宽度
     */
    private Integer width;
    /**
     * 广告位高度
     */
    private Integer height;
    /**
     * 广告位点击数
     */
    @TableField("click_num")
    private Integer clickNum;
    /**
     * 广告位默认内容
     */
    @TableField("default_content")
    private String defaultContent;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ClubAdvPosition{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", name=" + name +
        ", type=" + type +
        ", display=" + display +
        ", enable=" + enable +
        ", width=" + width +
        ", height=" + height +
        ", clickNum=" + clickNum +
        ", defaultContent=" + defaultContent +
        "}";
    }
}
