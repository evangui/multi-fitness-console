/**
 * 初始化时间卡充值记录详情对话框
 */
var CardTimecardRechargeInfoDlg = {
    cardTimecardRechargeInfoData : {}
};

/**
 * 清除数据
 */
CardTimecardRechargeInfoDlg.clearData = function() {
    this.cardTimecardRechargeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardTimecardRechargeInfoDlg.set = function(key, val) {
    this.cardTimecardRechargeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardTimecardRechargeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardTimecardRechargeInfoDlg.close = function() {
    parent.layer.close(window.parent.CardTimecardRecharge.layerIndex);
}

/**
 * 收集数据
 */
CardTimecardRechargeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('cardId')
    .set('vipId')
    .set('realname')
    .set('title')
    .set('cardTypeId')
    .set('cardTypeName')
    .set('actualInsertTime')
    .set('openCardTime')
    .set('startUseTime')
    .set('expireTime')
    .set('insertTime')
    .set('suspendStartTime')
    .set('suspendEndTime')
    .set('actualMoney')
    .set('retainage')
    .set('balance')
    .set('banksure')
    .set('contractNumber')
    .set('frontMoneyStatus')
    .set('isRenewCard')
    .set('isUnitedCard')
    .set('membershipId')
    .set('membershipName')
    .set('opeUserId')
    .set('opeUsername')
    .set('payMethod')
    .set('phone')
    .set('sourceId')
    .set('sourceName')
    .set('remark')
    .set('isAutoPrint')
    .set('port');
}

/**
 * 提交添加
 */
CardTimecardRechargeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardTimecardRecharge/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardTimecardRecharge.table.refresh();
        CardTimecardRechargeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardTimecardRechargeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardTimecardRechargeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardTimecardRecharge/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardTimecardRecharge.table.refresh();
        CardTimecardRechargeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardTimecardRechargeInfoData);
    ajax.start();
}

$(function() {

});
