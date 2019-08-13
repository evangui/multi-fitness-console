/**
 * 初始化维护记录详情对话框
 */
var MaintainRecordInfoDlg = {
    maintainRecordInfoData : {}
};

/**
 * 清除数据
 */
MaintainRecordInfoDlg.clearData = function() {
    this.maintainRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MaintainRecordInfoDlg.set = function(key, val) {
    this.maintainRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MaintainRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MaintainRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.MaintainRecord.layerIndex);
}

/**
 * 收集数据
 */
MaintainRecordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('vipUserId')
    .set('vipUserType')
    .set('membershipId')
    .set('content')
    .set('isTodo')
    .set('recordType')
    .set('status')
    .set('insertTime');
}
/**
 * 验证数据是否为空
 */
MaintainRecordInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
MaintainRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/maintainRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.MaintainRecord.table.refresh();
        MaintainRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.maintainRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MaintainRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/maintainRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.MaintainRecord.table.refresh();
        MaintainRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.maintainRecordInfoData);
    ajax.start();
}

$(function() {

});
