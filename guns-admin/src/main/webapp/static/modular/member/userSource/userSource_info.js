/**
 * 初始化自定义客户来源详情对话框
 */
var UserSourceInfoDlg = {
    userSourceInfoData : {}
};

/**
 * 清除数据
 */
UserSourceInfoDlg.clearData = function() {
    this.userSourceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserSourceInfoDlg.set = function(key, val) {
    this.userSourceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserSourceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserSourceInfoDlg.close = function() {
    parent.layer.close(window.parent.UserSource.layerIndex);
}

/**
 * 收集数据
 */
UserSourceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('name')
    .set('status')
    .set('sort');
}
/**
 * 验证数据是否为空
 */
UserSourceInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
UserSourceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userSource/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserSource.table.refresh();
        UserSourceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userSourceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserSourceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userSource/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserSource.table.refresh();
        UserSourceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userSourceInfoData);
    ajax.start();
}

$(function() {

});
