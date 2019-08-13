/**
 * 初始化团操课设置详情对话框
 */
var SettingSyllabusInfoDlg = {
    settingSyllabusInfoData : {}
};

/**
 * 清除数据
 */
SettingSyllabusInfoDlg.clearData = function() {
    this.settingSyllabusInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingSyllabusInfoDlg.set = function(key, val) {
    this.settingSyllabusInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingSyllabusInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettingSyllabusInfoDlg.close = function() {
    parent.layer.close(window.parent.SettingSyllabus.layerIndex);
}

/**
 * 收集数据
 */
SettingSyllabusInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('subscribeStartTime')
    .set('subscribeEndTime')
    .set('notEnoughCancelTime')
    .set('selfCancelTime')
    .set('startDate')
    .set('endDate');
}
/**
 * 验证数据是否为空
 */
SettingSyllabusInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SettingSyllabusInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingSyllabus/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettingSyllabus.table.refresh();
        SettingSyllabusInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingSyllabusInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettingSyllabusInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingSyllabus/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettingSyllabus.table.refresh();
        SettingSyllabusInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingSyllabusInfoData);
    ajax.start();
}

$(function() {

});
