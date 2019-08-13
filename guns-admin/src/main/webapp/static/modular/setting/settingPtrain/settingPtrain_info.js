/**
 * 初始化私教课程设置详情对话框
 */
var SettingPtrainInfoDlg = {
    settingPtrainInfoData : {}
};

/**
 * 清除数据
 */
SettingPtrainInfoDlg.clearData = function() {
    this.settingPtrainInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingPtrainInfoDlg.set = function(key, val) {
    this.settingPtrainInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingPtrainInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettingPtrainInfoDlg.close = function() {
    parent.layer.close(window.parent.SettingPtrain.layerIndex);
}

/**
 * 收集数据
 */
SettingPtrainInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('startTime')
    .set('endTime')
    .set('checkType')
    .set('delay')
    .set('coachType')
    .set('noVipSub')
    .set('isShowName');
}
/**
 * 验证数据是否为空
 */
SettingPtrainInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SettingPtrainInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingPtrain/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettingPtrain.table.refresh();
        SettingPtrainInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingPtrainInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettingPtrainInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingPtrain/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettingPtrain.table.refresh();
        SettingPtrainInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingPtrainInfoData);
    ajax.start();
}

$(function() {

});
