/**
 * 初始化俱乐部合同详情对话框
 */
var ClubContractInfoDlg = {
    clubContractInfoData : {}
};

/**
 * 清除数据
 */
ClubContractInfoDlg.clearData = function() {
    this.clubContractInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubContractInfoDlg.set = function(key, val) {
    this.clubContractInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubContractInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubContractInfoDlg.close = function() {
    parent.layer.close(window.parent.ClubContract.layerIndex);
}

/**
 * 收集数据
 */
ClubContractInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('title')
    .set('type')
    .set('contractNum')
    .set('hidden')
    .set('insertTime')
    .set('content');
}
/**
 * 验证数据是否为空
 */
ClubContractInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ClubContractInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubContract/add", function(data){
        Feng.success("添加成功!");
        window.parent.ClubContract.table.refresh();
        ClubContractInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubContractInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubContractInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubContract/update", function(data){
        Feng.success("修改成功!");
        window.parent.ClubContract.table.refresh();
        ClubContractInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubContractInfoData);
    ajax.start();
}

$(function() {

});
