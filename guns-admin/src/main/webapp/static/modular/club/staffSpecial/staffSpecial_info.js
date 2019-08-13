/**
 * 初始化会籍-主管-前台详情对话框
 */
var StaffSpecialInfoDlg = {
    staffSpecialInfoData : {}
};

/**
 * 清除数据
 */
StaffSpecialInfoDlg.clearData = function() {
    this.staffSpecialInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffSpecialInfoDlg.set = function(key, val) {
    this.staffSpecialInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffSpecialInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StaffSpecialInfoDlg.close = function() {
    parent.layer.close(window.parent.StaffSpecial.layerIndex);
}

/**
 * 收集数据
 */
StaffSpecialInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('userId')
    .set('nickname')
    .set('realname')
    .set('avatar')
    .set('status')
    .set('screenshotsAlarm')
    .set('newMemberCounts')
    .set('memberCounts')
    .set('auth')
    .set('insertTime')
    .set('type');
}
/**
 * 验证数据是否为空
 */
StaffSpecialInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
StaffSpecialInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/staffSpecial/add", function(data){
        Feng.success("添加成功!");
        window.parent.StaffSpecial.table.refresh();
        StaffSpecialInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffSpecialInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StaffSpecialInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/staffSpecial/update", function(data){
        Feng.success("修改成功!");
        window.parent.StaffSpecial.table.refresh();
        StaffSpecialInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffSpecialInfoData);
    ajax.start();
}

$(function() {

});
