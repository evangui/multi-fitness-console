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
 * 私教课程设置
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-19
 */
@TableName("biz_setting_ptrain")
public class SettingPtrain extends Model<SettingPtrain> {

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
     * 私教每日开始时间秒
     */
    @TableField("start_time")
    private Integer startTime;
    /**
     * 私教每日结束时间秒
     */
    @TableField("end_time")
    private Integer endTime;
    /**
     * 私教卡确认机制
     */
    @TableField("check_type")
    private Integer checkType;
    /**
     * 延迟评价分钟数
     */
    private Integer delay;
    /**
     * 私教类型:1一对一 0一对多
     */
    @TableField("coach_type")
    private Integer coachType;
    /**
     * 非会员是否能预约
     */
    @TableField("no_vip_sub")
    private Integer noVipSub;
    /**
     * 显示已预约会员昵称
     */
    @TableField("is_show_name")
    private Integer isShowName;
  
    @TableField("all_day")
    private Integer allDay;


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

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getCoachType() {
        return coachType;
    }

    public void setCoachType(Integer coachType) {
        this.coachType = coachType;
    }

    public Integer getNoVipSub() {
        return noVipSub;
    }

    public void setNoVipSub(Integer noVipSub) {
        this.noVipSub = noVipSub;
    }

    public Integer getIsShowName() {
        return isShowName;
    }

    public void setIsShowName(Integer isShowName) {
        this.isShowName = isShowName;
    }
    public Integer getAllDay() {
  		return allDay;
  	}

  	public void setAllDay(Integer allDay) {
  		this.allDay = allDay;
  	}
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    

    @Override
    public String toString() {
        return "SettingPtrain{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", checkType=" + checkType +
        ", delay=" + delay +
        ", coachType=" + coachType +
        ", noVipSub=" + noVipSub +
        ", allDay=" + allDay +
        ", isShowName=" + isShowName +
        "}";
    }
}
