/**
 * 初始化私教课程设置详情对话框
 */
var SettingClubPtrainInfoDlg = {
    settingClubPtrainInfoData : {}
};

/**
 * 清除数据
 */
SettingClubPtrainInfoDlg.clearData = function() {
    this.settingClubPtrainInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingClubPtrainInfoDlg.set = function(key, val) {
    this.settingClubPtrainInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingClubPtrainInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettingClubPtrainInfoDlg.close = function() {
    parent.layer.close(window.parent.SettingClubPtrain.layerIndex);
}

/**
 * 收集数据
 */
SettingClubPtrainInfoDlg.collectData = function() {
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
SettingClubPtrainInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SettingClubPtrainInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingClubPtrain/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettingClubPtrain.table.refresh();
        SettingClubPtrainInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingClubPtrainInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettingClubPtrainInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingClubPtrain/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettingClubPtrain.table.refresh();
        SettingClubPtrainInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingClubPtrainInfoData);
    ajax.start();
}

$(function() {

});
