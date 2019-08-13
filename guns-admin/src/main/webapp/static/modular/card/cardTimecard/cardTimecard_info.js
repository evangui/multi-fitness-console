/**
 * 初始化会员时间卡详情对话框
 */
var CardTimecardInfoDlg = {
    cardTimecardInfoData : {}
};

/**
 * 清除数据
 */
CardTimecardInfoDlg.clearData = function() {
    this.cardTimecardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardTimecardInfoDlg.set = function(key, val) {
    this.cardTimecardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardTimecardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardTimecardInfoDlg.close = function() {
    parent.layer.close(window.parent.CardTimecard.layerIndex);
}

/**
 * 收集数据
 */
CardTimecardInfoDlg.collectData = function() {
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
    .set('expireDays')
    .set('expireTime')
    .set('insertTime')
    .set('suspendStartTime')
    .set('suspendEndTime')
    .set('actualMoney')
    .set('retainage')
    .set('autoOpenCardStatus')
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
    .set('port');
}
/**
 * 验证数据是否为空
 */
CardTimecardInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardTimecardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardTimecard/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardTimecard.table.refresh();
        CardTimecardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardTimecardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardTimecardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardTimecard/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardTimecard.table.refresh();
        CardTimecardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardTimecardInfoData);
    ajax.start();
}

$(function() {

});
