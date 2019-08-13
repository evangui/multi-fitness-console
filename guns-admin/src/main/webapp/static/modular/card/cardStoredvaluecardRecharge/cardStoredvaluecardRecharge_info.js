/**
 * 初始化储值卡充值记录详情对话框
 */
var CardStoredvaluecardRechargeInfoDlg = {
    cardStoredvaluecardRechargeInfoData : {}
};

/**
 * 清除数据
 */
CardStoredvaluecardRechargeInfoDlg.clearData = function() {
    this.cardStoredvaluecardRechargeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardStoredvaluecardRechargeInfoDlg.set = function(key, val) {
    this.cardStoredvaluecardRechargeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardStoredvaluecardRechargeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardStoredvaluecardRechargeInfoDlg.close = function() {
    parent.layer.close(window.parent.CardStoredvaluecardRecharge.layerIndex);
}

/**
 * 收集数据
 */
CardStoredvaluecardRechargeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('vipId')
    .set('cardId')
    .set('realname')
    .set('title')
    .set('cardTypeId')
    .set('cardTypeName')
    .set('actualInsertTime')
    .set('openCardTime')
    .set('startUseTime')
    .set('expireTime')
    .set('insertTime')
    .set('sumMoney')
    .set('leftMoney')
    .set('actualMoney')
    .set('retainage')
    .set('autoOpenCardStatus')
    .set('balance')
    .set('banksure')
    .set('contractNumber')
    .set('frontMoneyStatus')
    .set('isRenewCard')
    .set('isUnitedCard')
    .set('rcepteionId')
    .set('rcepteionName')
    .set('membershipId')
    .set('membershipName')
    .set('opeUserId')
    .set('opeUsername')
    .set('payMethod')
    .set('phone')
    .set('sourceId')
    .set('sourceName')
    .set('remark')
    .set('port')
    .set('rechargeId')
    .set('enabled')
    .set('enableRemark')
    .set('isAutoPrint');
}

/**
 * 提交添加
 */
CardStoredvaluecardRechargeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecardRecharge/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardStoredvaluecardRecharge.table.refresh();
        CardStoredvaluecardRechargeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardStoredvaluecardRechargeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardStoredvaluecardRechargeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecardRecharge/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardStoredvaluecardRecharge.table.refresh();
        CardStoredvaluecardRechargeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardStoredvaluecardRechargeInfoData);
    ajax.start();
}

$(function() {

});
