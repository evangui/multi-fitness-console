/**
 * 初始化VIP用户自定义字段详情对话框
 */
var VipUserFieldInfoDlg = {
    vipUserFieldInfoData : {}
};

/**
 * 清除数据
 */
VipUserFieldInfoDlg.clearData = function() {
    this.vipUserFieldInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VipUserFieldInfoDlg.set = function(key, val) {
    this.vipUserFieldInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VipUserFieldInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VipUserFieldInfoDlg.close = function() {
    parent.layer.close(window.parent.VipUserField.layerIndex);
}

/**
 * 收集数据
 */
VipUserFieldInfoDlg.collectData = function() {
    this
    .set('fieldId')
    .set('clubId')
    .set('filedName')
    .set('filedOption')
    .set('filedType')
    .set('hidden')
    .set('sort');
}
/**
 * 验证数据是否为空
 */
VipUserFieldInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
VipUserFieldInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vipUserField/add", function(data){
        Feng.success("添加成功!");
        window.parent.VipUserField.table.refresh();
        VipUserFieldInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vipUserFieldInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VipUserFieldInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vipUserField/update", function(data){
        Feng.success("修改成功!");
        window.parent.VipUserField.table.refresh();
        VipUserFieldInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vipUserFieldInfoData);
    ajax.start();
}

$(function() {

});
