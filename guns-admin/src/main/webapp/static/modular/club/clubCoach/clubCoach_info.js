/**
 * 初始化教练详情对话框
 */
var ClubCoachInfoDlg = {
    clubCoachInfoData : {}
};

/**
 * 清除数据
 */
ClubCoachInfoDlg.clearData = function() {
    this.clubCoachInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubCoachInfoDlg.set = function(key, val) {
    this.clubCoachInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubCoachInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubCoachInfoDlg.close = function() {
    parent.layer.close(window.parent.ClubCoach.layerIndex);
}

/**
 * 收集数据
 */
ClubCoachInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('userId')
    .set('nickname')
    .set('realname')
    .set('avatar')
    .set('desc')
    .set('goodAt')
    .set('order')
    .set('status')
    .set('monthPtrainCount')
    .set('monthAvgScore')
    .set('vipCount')
    .set('auth')
    .set('insertTime');
}
/**
 * 验证数据是否为空
 */
ClubCoachInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ClubCoachInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubCoach/add", function(data){
        Feng.success("添加成功!");
        window.parent.ClubCoach.table.refresh();
        ClubCoachInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubCoachInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubCoachInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubCoach/update", function(data){
        Feng.success("修改成功!");
        window.parent.ClubCoach.table.refresh();
        ClubCoachInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubCoachInfoData);
    ajax.start();
}

$(function() {

});
