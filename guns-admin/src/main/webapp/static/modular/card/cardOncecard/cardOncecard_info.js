/**
 * 初始化会员次卡详情对话框
 */
var CardOncecardInfoDlg = {
    cardOncecardInfoData : {}
};

/**
 * 清除数据
 */
CardOncecardInfoDlg.clearData = function() {
    this.cardOncecardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardOncecardInfoDlg.set = function(key, val) {
    this.cardOncecardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardOncecardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardOncecardInfoDlg.close = function() {
    parent.layer.close(window.parent.CardOncecard.layerIndex);
}

/**
 * 收集数据
 */
CardOncecardInfoDlg.collectData = function() {
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
CardOncecardInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
/**
 * 提交添加
 */
CardOncecardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardOncecard/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardOncecard.table.refresh();
        CardOncecardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardOncecardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardOncecardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardOncecard/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardOncecard.table.refresh();
        CardOncecardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardOncecardInfoData);
    ajax.start();
}

$(function() {

});
