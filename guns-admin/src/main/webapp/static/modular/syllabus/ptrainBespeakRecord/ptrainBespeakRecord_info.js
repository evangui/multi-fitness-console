/**
 * 初始化私教预约记录详情对话框
 */
var PtrainBespeakRecordInfoDlg = {
    ptrainBespeakRecordInfoData : {}
};

/**
 * 清除数据
 */
PtrainBespeakRecordInfoDlg.clearData = function() {
    this.ptrainBespeakRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PtrainBespeakRecordInfoDlg.set = function(key, val) {
    this.ptrainBespeakRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PtrainBespeakRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PtrainBespeakRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.PtrainBespeakRecord.layerIndex);
}

/**
 * 收集数据
 */
PtrainBespeakRecordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('coachId')
    .set('coachName')
    .set('vipId')
    .set('realname')
    .set('phone')
    .set('fromTime')
    .set('toTime')
    .set('date')
    .set('MembershipId')
    .set('MembershipName')
    .set('insertTime')
    .set('remark')
    .set('cardType')
    .set('cardId')
    .set('cardDeductNum')
    .set('recordType')
    .set('userType')
    .set('status');
}
/**
 * 验证数据是否为空
 */
PtrainBespeakRecordInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
PtrainBespeakRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ptrainBespeakRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.PtrainBespeakRecord.table.refresh();
        PtrainBespeakRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ptrainBespeakRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PtrainBespeakRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ptrainBespeakRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.PtrainBespeakRecord.table.refresh();
        PtrainBespeakRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ptrainBespeakRecordInfoData);
    ajax.start();
}

$(function() {

});
