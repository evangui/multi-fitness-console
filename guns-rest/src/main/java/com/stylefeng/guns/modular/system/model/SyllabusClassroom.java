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
 * 团操教室
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_syllabus_classroom")
public class SyllabusClassroom extends Model<SyllabusClassroom> {

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
     * 教室名称
     */
    private String name;
    /**
     * 座次数量
     */
    private Integer number;
    /**
     * 绑定课程数
     */
    @TableField("bind_syllabus_num")
    private Integer bindSyllabusNum;
    /**
     * 禁用座次
     */
    @TableField("dis_nums")
    private String disNums;
    /**
     * 添加时间
     */
    @TableField("insert_time")
    private Integer insertTime;
    /**
     * 状态 含义待定
     */
    private Integer status;


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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getBindSyllabusNum() {
        return bindSyllabusNum;
    }

    public void setBindSyllabusNum(Integer bindSyllabusNum) {
        this.bindSyllabusNum = bindSyllabusNum;
    }

    public String getDisNums() {
        return disNums;
    }

    public void setDisNums(String disNums) {
        this.disNums = disNums;
    }

    public Integer getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
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
        return "SyllabusClassroom{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", name=" + name +
        ", number=" + number +
        ", bindSyllabusNum=" + bindSyllabusNum +
        ", disNums=" + disNums +
        ", insertTime=" + insertTime +
        ", status=" + status +
        "}";
    }
}
