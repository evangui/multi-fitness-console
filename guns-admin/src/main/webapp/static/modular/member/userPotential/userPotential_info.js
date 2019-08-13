/**
 * 初始化潜在客户详情对话框
 */
var UserPotentialInfoDlg = {
    userPotentialInfoData : {}
};

/**
 * 清除数据
 */
UserPotentialInfoDlg.clearData = function() {
    this.userPotentialInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserPotentialInfoDlg.set = function(key, val) {
    this.userPotentialInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserPotentialInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserPotentialInfoDlg.close = function() {
    parent.layer.close(window.parent.UserPotential.layerIndex);
}

/**
 * 收集数据
 */
UserPotentialInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('realname')
    .set('nickname')
    .set('realavatar')
    .set('gender')
    .set('phone')
    .set('coachId')
    .set('coachName')
    .set('membershipId')
    .set('membershipName')
    .set('remark')
    .set('isImport')
    .set('lastMaintainRecord')
    .set('lastMaintainTime')
    .set('insertTime')
    .set('sourceId')
    .set('sourceName')
    .set('labelLevels')
    .set('isVipUser');
}
/**
 * 验证数据是否为空
 */
UserPotentialInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
UserPotentialInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userPotential/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserPotential.table.refresh();
        UserPotentialInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userPotentialInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserPotentialInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userPotential/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserPotential.table.refresh();
        UserPotentialInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userPotentialInfoData);
    ajax.start();
}

$(function() {

});
