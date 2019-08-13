/**
 * 初始化私教卡消课记录详情对话框
 */
var CardPtraincardSpendInfoDlg = {
    cardPtraincardSpendInfoData : {}
};

/**
 * 清除数据
 */
CardPtraincardSpendInfoDlg.clearData = function() {
    this.cardPtraincardSpendInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardPtraincardSpendInfoDlg.set = function(key, val) {
    this.cardPtraincardSpendInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardPtraincardSpendInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardPtraincardSpendInfoDlg.close = function() {
    parent.layer.close(window.parent.CardPtraincardSpend.layerIndex);
}

/**
 * 收集数据
 */
CardPtraincardSpendInfoDlg.collectData = function() {
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
    .set('rcepteionId')
    .set('rcepteionName')
    .set('receptionRemark')
    .set('coachId')
    .set('coachName')
    .set('membershipId')
    .set('membershipName')
    .set('opeUserId')
    .set('opeUsername')
    .set('phone')
    .set('remark')
    .set('cardStatus')
    .set('port')
    .set('isSyllabusSub');
}
/**
 * 验证数据是否为空
 */
CardPtraincardSpendInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardPtraincardSpendInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardPtraincardSpend/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardPtraincardSpend.table.refresh();
        CardPtraincardSpendInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardPtraincardSpendInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardPtraincardSpendInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardPtraincardSpend/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardPtraincardSpend.table.refresh();
        CardPtraincardSpendInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardPtraincardSpendInfoData);
    ajax.start();
}

$(function() {

});
