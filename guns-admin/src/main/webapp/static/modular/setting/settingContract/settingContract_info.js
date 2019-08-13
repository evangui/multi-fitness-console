/**
 * 初始化合同设置详情对话框
 */
var SettingContractInfoDlg = {
    settingContractInfoData : {}
};

/**
 * 清除数据
 */
SettingContractInfoDlg.clearData = function() {
    this.settingContractInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingContractInfoDlg.set = function(key, val) {
    this.settingContractInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingContractInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettingContractInfoDlg.close = function() {
    parent.layer.close(window.parent.SettingContract.layerIndex);
}

/**
 * 收集数据
 */
SettingContractInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('contractStatus')
    .set('contractVip')
    .set('contractRecharge')
    .set('contractContract')
    .set('contractSign');
}
/**
 * 验证数据是否为空
 */
SettingContractInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SettingContractInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingContract/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettingContract.table.refresh();
        SettingContractInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingContractInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettingContractInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingContract/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettingContract.table.refresh();
        SettingContractInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingContractInfoData);
    ajax.start();
}

$(function() {

});
