/**
 * 初始化课程活动报名记录详情对话框
 */
var ActivityCourseSubscribeInfoDlg = {
    activityCourseSubscribeInfoData : {}
};

/**
 * 清除数据
 */
ActivityCourseSubscribeInfoDlg.clearData = function() {
    this.activityCourseSubscribeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityCourseSubscribeInfoDlg.set = function(key, val) {
    this.activityCourseSubscribeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityCourseSubscribeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ActivityCourseSubscribeInfoDlg.close = function() {
    parent.layer.close(window.parent.ActivityCourseSubscribe.layerIndex);
}

/**
 * 收集数据
 */
ActivityCourseSubscribeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('vipId')
    .set('activityId')
    .set('phone')
    .set('realname')
    .set('sign')
    .set('status')
    .set('insertTime');
}

/**
 * 验证数据是否为空
 */
ActivityCourseSubscribeInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ActivityCourseSubscribeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activityCourseSubscribe/add", function(data){
        Feng.success("添加成功!");
        window.parent.ActivityCourseSubscribe.table.refresh();
        ActivityCourseSubscribeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityCourseSubscribeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ActivityCourseSubscribeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activityCourseSubscribe/update", function(data){
        Feng.success("修改成功!");
        window.parent.ActivityCourseSubscribe.table.refresh();
        ActivityCourseSubscribeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityCourseSubscribeInfoData);
    ajax.start();
}

$(function() {

});
