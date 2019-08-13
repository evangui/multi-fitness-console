/**
 * 初始化积分项目设置详情对话框
 */
var SettingPointsItemInfoDlg = {
    settingPointsItemInfoData : {}
};

/**
 * 清除数据
 */
SettingPointsItemInfoDlg.clearData = function() {
    this.settingPointsItemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingPointsItemInfoDlg.set = function(key, val) {
    this.settingPointsItemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingPointsItemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettingPointsItemInfoDlg.close = function() {
    parent.layer.close(window.parent.SettingPointsItem.layerIndex);
}

/**
 * 收集数据
 */
SettingPointsItemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('name')
    .set('title')
    .set('dayTimes')
    .set('type')
    .set('num');
}
/**
 * 验证数据是否为空
 */
SettingPointsItemInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SettingPointsItemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingPointsItem/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettingPointsItem.table.refresh();
        SettingPointsItemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingPointsItemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettingPointsItemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingPointsItem/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettingPointsItem.table.refresh();
        SettingPointsItemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingPointsItemInfoData);
    ajax.start();
}

$(function() {

});
