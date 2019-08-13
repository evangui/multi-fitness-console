/**
 * 初始化VIP用户详情对话框
 */
var VipUserInfoDlg = {
    vipUserInfoData : {}
};

/**
 * 清除数据
 */
VipUserInfoDlg.clearData = function() {
    this.vipUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VipUserInfoDlg.set = function(key, val) {
    this.vipUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VipUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VipUserInfoDlg.close = function() {
    parent.layer.close(window.parent.VipUser.layerIndex);
}

/**
 * 收集数据
 */
VipUserInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('realname')
    .set('nickname')
    .set('realavatar')
    .set('payPasswd')
    .set('gender')
    .set('phone')
    .set('birthday')
    .set('idCardNumber')
    .set('cardId')
    .set('cardNumber')
    .set('cardType')
    .set('coachId')
    .set('coachName')
    .set('introducePersonCount')
    .set('introducePersonId')
    .set('introducePersonName')
    .set('membershipId')
    .set('membershipName')
    .set('country')
    .set('address')
    .set('remark')
    .set('points')
    .set('usedPoints')
    .set('isImport')
    .set('isUniverseVip')
    .set('lastMaintainRecord')
    .set('lastMaintainTime')
    .set('lastSignTime')
    .set('admissionTime')
    .set('admissionRemark')
    .set('insertTime')
    .set('updateTime')
    .set('sourceId')
    .set('personalTrainerCardUsedCounts')
    .set('personalTrainerCardCounts')
    .set('personalTrainerCardNum')
    .set('onceCardUsedCounts')
    .set('onceCardCounts')
    .set('onceCardNum')
    .set('storedValueCardUsedCounts')
    .set('storedValueCardCounts')
    .set('storedValueCardNum')
    .set('expireTime')
    .set('expireStatus')
    .set('labelLevels');
}
/**
 * 验证数据是否为空
 */
VipUserInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
VipUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vipUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.VipUser.table.refresh();
        VipUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vipUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VipUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/vipUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.VipUser.table.refresh();
        VipUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.vipUserInfoData);
    ajax.start();
}

$(function() {

});
