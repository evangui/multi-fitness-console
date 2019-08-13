/**
 * 初始化俱乐部活动详情对话框
 */
var ActivityInfoDlg = {
    activityInfoData : {}
};

/**
 * 清除数据
 */
ActivityInfoDlg.clearData = function() {
    this.activityInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityInfoDlg.set = function(key, val) {
    this.activityInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ActivityInfoDlg.close = function() {
    parent.layer.close(window.parent.Activity.layerIndex);
}

/**
 * 收集数据
 */
ActivityInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('type')
    .set('title')
    .set('cover')
    .set('editPwd')
    .set('courseAddress')
    .set('courseTime')
    .set('number')
    .set('signCode')
    .set('content')
    .set('teachers')
    .set('buyTimes')
    .set('totalFee')
    .set('fieldAddress')
    .set('fieldPhone')
    .set('enrollStartTime')
    .set('enrollEndTime')
    .set('startTime')
    .set('endTime')
    .set('customFields')
    .set('projects')
    .set('insertTime')
    .set('sort')
    .set('hidden')
    .set('status');
}

/**
 * 验证数据是否为空
 */
ActivityInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
ActivityInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activity/add", function(data){
        Feng.success("添加成功!");
        window.parent.Activity.table.refresh();
        ActivityInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ActivityInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activity/update", function(data){
        Feng.success("修改成功!");
        window.parent.Activity.table.refresh();
        ActivityInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityInfoData);
    ajax.start();
}

$(function() {

});
