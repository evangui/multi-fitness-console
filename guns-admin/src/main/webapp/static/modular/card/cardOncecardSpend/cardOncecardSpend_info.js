/**
 * 初始化次卡消次记录详情对话框
 */
var CardOncecardSpendInfoDlg = {
    cardOncecardSpendInfoData : {}
};

/**
 * 清除数据
 */
CardOncecardSpendInfoDlg.clearData = function() {
    this.cardOncecardSpendInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardOncecardSpendInfoDlg.set = function(key, val) {
    this.cardOncecardSpendInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardOncecardSpendInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardOncecardSpendInfoDlg.close = function() {
    parent.layer.close(window.parent.CardOncecardSpend.layerIndex);
}

/**
 * 收集数据
 */
CardOncecardSpendInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('cardId')
    .set('vipId')
    .set('syllabusId')
    .set('realname')
    .set('costs')
    .set('insertTime')
    .set('unitPrice')
    .set('title')
    .set('coachId')
    .set('coachName')
    .set('membershipId')
    .set('membershipName')
    .set('opeUserId')
    .set('opeUsername')
    .set('phone')
    .set('remark')
    .set('cardStatus')
    .set('port');
}
/**
 * 验证数据是否为空
 */
CardOncecardSpendInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardOncecardSpendInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardOncecardSpend/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardOncecardSpend.table.refresh();
        CardOncecardSpendInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardOncecardSpendInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardOncecardSpendInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardOncecardSpend/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardOncecardSpend.table.refresh();
        CardOncecardSpendInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardOncecardSpendInfoData);
    ajax.start();
}

$(function() {

});
