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
 * 合同设置
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@TableName("biz_setting_contract")
public class SettingContract extends Model<SettingContract> {

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
     * 合同自动签发
     */
    @TableField("contract_status")
    private Integer contractStatus;
    /**
     * 会员信息是否显示
     */
    @TableField("contract_vip")
    private Integer contractVip;
    /**
     * 充值信息是否显示
     */
    @TableField("contract_recharge")
    private Integer contractRecharge;
    /**
     * 合同信息是否显示
     */
    @TableField("contract_contract")
    private Integer contractContract;
    /**
     * 签署信息是否显示
     */
    @TableField("contract_sign")
    private Integer contractSign;


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

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getContractVip() {
        return contractVip;
    }

    public void setContractVip(Integer contractVip) {
        this.contractVip = contractVip;
    }

    public Integer getContractRecharge() {
        return contractRecharge;
    }

    public void setContractRecharge(Integer contractRecharge) {
        this.contractRecharge = contractRecharge;
    }

    public Integer getContractContract() {
        return contractContract;
    }

    public void setContractContract(Integer contractContract) {
        this.contractContract = contractContract;
    }

    public Integer getContractSign() {
        return contractSign;
    }

    public void setContractSign(Integer contractSign) {
        this.contractSign = contractSign;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SettingContract{" +
        "id=" + id +
        ", clubId=" + clubId +
        ", contractStatus=" + contractStatus +
        ", contractVip=" + contractVip +
        ", contractRecharge=" + contractRecharge +
        ", contractContract=" + contractContract +
        ", contractSign=" + contractSign +
        "}";
    }
}
