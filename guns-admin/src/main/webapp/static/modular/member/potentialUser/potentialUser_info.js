/**
 * 初始化潜在客户详情对话框
 */
var PotentialUserInfoDlg = {
    potentialUserInfoData : {}
};

/**
 * 清除数据
 */
PotentialUserInfoDlg.clearData = function() {
    this.potentialUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PotentialUserInfoDlg.set = function(key, val) {
    this.potentialUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PotentialUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PotentialUserInfoDlg.close = function() {
    parent.layer.close(window.parent.PotentialUser.layerIndex);
}

/**
 * 收集数据
 */
PotentialUserInfoDlg.collectData = function() {
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
PotentialUserInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
PotentialUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/potentialUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.PotentialUser.table.refresh();
        PotentialUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.potentialUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PotentialUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/potentialUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.PotentialUser.table.refresh();
        PotentialUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.potentialUserInfoData);
    ajax.start();
}

$(function() {

});
