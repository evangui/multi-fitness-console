/**
 * 初始化会员储值卡详情对话框
 */
var CardStoredvaluecardInfoDlg = {
    cardStoredvaluecardInfoData : {}
};

/**
 * 清除数据
 */
CardStoredvaluecardInfoDlg.clearData = function() {
    this.cardStoredvaluecardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardStoredvaluecardInfoDlg.set = function(key, val) {
    this.cardStoredvaluecardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardStoredvaluecardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardStoredvaluecardInfoDlg.close = function() {
    parent.layer.close(window.parent.CardStoredvaluecard.layerIndex);
}

/**
 * 收集数据
 */
CardStoredvaluecardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
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
    .set('enableRemark');
}
/**
 * 验证数据是否为空
 */
CardStoredvaluecardInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardStoredvaluecardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecard/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardStoredvaluecard.table.refresh();
        CardStoredvaluecardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardStoredvaluecardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardStoredvaluecardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecard/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardStoredvaluecard.table.refresh();
        CardStoredvaluecardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardStoredvaluecardInfoData);
    ajax.start();
}

$(function() {

});
