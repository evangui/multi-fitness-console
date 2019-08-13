/**
 * 初始化签到记录详情对话框
 */
var CheckinRecordInfoDlg = {
    checkinRecordInfoData : {}
};

/**
 * 清除数据
 */
CheckinRecordInfoDlg.clearData = function() {
    this.checkinRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CheckinRecordInfoDlg.set = function(key, val) {
    this.checkinRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CheckinRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CheckinRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.CheckinRecord.layerIndex);
}

/**
 * 收集数据
 */
CheckinRecordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('vipId')
    .set('realname')
    .set('phone')
    .set('insertTime')
    .set('lastCheckInTime')
    .set('checkOutTime')
    .set('admissionRecordCount')
    .set('opeUserId')
    .set('opeUsername')
    .set('status')
    .set('remark')
    .set('banksure')
    .set('bankRemark')
    .set('ringStatus')
    .set('port');
}
/**
 * 验证数据是否为空
 */
CheckinRecordInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CheckinRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkinRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.CheckinRecord.table.refresh();
        CheckinRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.checkinRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CheckinRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkinRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.CheckinRecord.table.refresh();
        CheckinRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.checkinRecordInfoData);
    ajax.start();
}

$(function() {

});
