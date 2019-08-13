/**
 * 初始化会员私教卡详情对话框
 */
var CardPtraincardInfoDlg = {
    cardPtraincardInfoData : {}
};

/**
 * 清除数据
 */
CardPtraincardInfoDlg.clearData = function() {
    this.cardPtraincardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardPtraincardInfoDlg.set = function(key, val) {
    this.cardPtraincardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardPtraincardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardPtraincardInfoDlg.close = function() {
    parent.layer.close(window.parent.CardPtraincard.layerIndex);
}

/**
 * 收集数据
 */
CardPtraincardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
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
    .set('port')
    .set('rechargeId')
    .set('enabled');
}
/**
 * 验证数据是否为空
 */
CardPtraincardInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardPtraincardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardPtraincard/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardPtraincard.table.refresh();
        CardPtraincardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardPtraincardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardPtraincardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardPtraincard/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardPtraincard.table.refresh();
        CardPtraincardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardPtraincardInfoData);
    ajax.start();
}

$(function() {

});
