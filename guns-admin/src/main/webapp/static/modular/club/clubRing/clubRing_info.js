/**
 * 初始化手环详情对话框
 */
var ClubRingInfoDlg = {
    clubRingInfoData : {}
};

/**
 * 清除数据
 */
ClubRingInfoDlg.clearData = function() {
    this.clubRingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubRingInfoDlg.set = function(key, val) {
    this.clubRingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubRingInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubRingInfoDlg.close = function() {
    parent.layer.close(window.parent.ClubRing.layerIndex);
}

/**
 * 收集数据
 */
ClubRingInfoDlg.collectData = function() {
    this
    .set('id')
    .set('ringNum')
    .set('clubId')
    .set('vipId')
    .set('realname')
    .set('phone')
    .set('insertTime')
    .set('admissionRecordId')
    .set('admissionRemark')
    .set('borrowTime')
    .set('returnTime')
    .set('status');
}
/**
 * 验证数据是否为空
 */
ClubRingInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ClubRingInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubRing/add", function(data){
        Feng.success("添加成功!");
        window.parent.ClubRing.table.refresh();
        ClubRingInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubRingInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubRingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubRing/update", function(data){
        Feng.success("修改成功!");
        window.parent.ClubRing.table.refresh();
        ClubRingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubRingInfoData);
    ajax.start();
}

$(function() {

});
