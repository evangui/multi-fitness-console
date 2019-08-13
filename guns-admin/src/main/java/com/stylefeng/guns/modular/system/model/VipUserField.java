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
 * Vip用户自定义字段
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_vip_user_field")
public class VipUserField extends Model<VipUserField> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "field_id", type = IdType.AUTO)
    private Integer fieldId;
    /**
     * 俱乐部id
     */
    @TableField("club_id")
    private Integer clubId;
    /**
     * 字段提示名称
     */
    @TableField("filed_name")
    private String filedName;
    /**
     * 是否可选
     */
    @TableField("filed_option")
    private Integer filedOption;
    /**
     * 填写方式 1小输入框 2大输入框 3选择框
     */
    @TableField("filed_type")
    private Integer filedType;
    /**
     * 是否隐藏
     */
    private Integer hidden;
    /**
     * 排序
     */
    private Integer sort;


    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public Integer getFiledOption() {
        return filedOption;
    }

    public void setFiledOption(Integer filedOption) {
        this.filedOption = filedOption;
    }

    public Integer getFiledType() {
        return filedType;
    }

    public void setFiledType(Integer filedType) {
        this.filedType = filedType;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.fieldId;
    }

    @Override
    public String toString() {
        return "VipUserField{" +
        "fieldId=" + fieldId +
        ", clubId=" + clubId +
        ", filedName=" + filedName +
        ", filedOption=" + filedOption +
        ", filedType=" + filedType +
        ", hidden=" + hidden +
        ", sort=" + sort +
        "}";
    }
}
