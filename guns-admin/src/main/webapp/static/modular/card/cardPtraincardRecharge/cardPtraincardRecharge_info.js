/**
 * 初始化私教卡充值记录详情对话框
 */
var CardPtraincardRechargeInfoDlg = {
    cardPtraincardRechargeInfoData : {}
};

/**
 * 清除数据
 */
CardPtraincardRechargeInfoDlg.clearData = function() {
    this.cardPtraincardRechargeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardPtraincardRechargeInfoDlg.set = function(key, val) {
    this.cardPtraincardRechargeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardPtraincardRechargeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardPtraincardRechargeInfoDlg.close = function() {
    parent.layer.close(window.parent.CardPtraincardRecharge.layerIndex);
}

/**
 * 收集数据
 */
CardPtraincardRechargeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('cardId')
    .set('vipId')
    .set('realname')
    .set('title')
    .set('cardTypeId')
    .set('cardTypeName')
    .set('counts')
    .set('leftCounts')
    .set('actualInsertTime')
    .set('openCardTime')
    .set('startUseTime')
    .set('expireTime')
    .set('insertTime')
    .set('unitPrice')
    .set('actualMoney')
    .set('retainage')
    .set('autoOpenCardStatus')
    .set('balance')
    .set('banksure')
    .set('contractNumber')
    .set('frontMoneyStatus')
    .set('isRenewCard')
    .set('isUnitedCard')
    .set('coachId')
    .set('coachName')
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
 * 验证数据是否为空
 */
CardPtraincardRechargeInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardPtraincardRechargeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardPtraincardRecharge/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardPtraincardRecharge.table.refresh();
        CardPtraincardRechargeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardPtraincardRechargeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardPtraincardRechargeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardPtraincardRecharge/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardPtraincardRecharge.table.refresh();
        CardPtraincardRechargeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardPtraincardRechargeInfoData);
    ajax.start();
}

$(function() {

});
