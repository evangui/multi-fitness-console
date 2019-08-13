/**
 * 初始化会员卡设置详情对话框
 */
var SettingCardInfoDlg = {
    settingCardInfoData : {}
};

/**
 * 清除数据
 */
SettingCardInfoDlg.clearData = function() {
    this.settingCardInfoData = {};
}
/**
 * 验证数据是否为空
 */
SettingCardInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingCardInfoDlg.set = function(key, val) {
    this.settingCardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettingCardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettingCardInfoDlg.close = function() {
    parent.layer.close(window.parent.SettingCard.layerIndex);
}

/**
 * 收集数据
 */
SettingCardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('leaveDays')
    .set('lessLeaveDays')
    .set('deadline')
    .set('record')
    .set('leftAmountHide')
    .set('frontConsumeSwitch')
    .set('onceCardUsedPass')
    .set('cardStartUseTimeSwitch')
    .set('vipPasswd');
}

/**
 * 提交添加
 */
SettingCardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingCard/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettingCard.table.refresh();
        SettingCardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingCardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettingCardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settingCard/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettingCard.table.refresh();
        SettingCardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settingCardInfoData);
    ajax.start();
}

$(function() {

});
