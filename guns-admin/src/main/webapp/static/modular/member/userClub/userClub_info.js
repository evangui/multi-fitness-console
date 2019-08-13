/**
 * 初始化俱乐部普通用户详情对话框
 */
var UserClubInfoDlg = {
    userClubInfoData : {}
};

/**
 * 清除数据
 */
UserClubInfoDlg.clearData = function() {
    this.userClubInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserClubInfoDlg.set = function(key, val) {
    this.userClubInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserClubInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserClubInfoDlg.close = function() {
    parent.layer.close(window.parent.UserClub.layerIndex);
}

/**
 * 收集数据
 */
UserClubInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('clubId')
    .set('nickname')
    .set('avatar')
    .set('gender')
    .set('phone')
    .set('insertTime');
}
/**
 * 验证数据是否为空
 */
UserClubInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
UserClubInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userClub/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserClub.table.refresh();
        UserClubInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userClubInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserClubInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userClub/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserClub.table.refresh();
        UserClubInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userClubInfoData);
    ajax.start();
}

$(function() {

});
