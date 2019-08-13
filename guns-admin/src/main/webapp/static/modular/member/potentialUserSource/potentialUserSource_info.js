/**
 * 初始化客户来源自定义字段详情对话框
 */
var PotentialUserSourceInfoDlg = {
    potentialUserSourceInfoData : {}
};

/**
 * 清除数据
 */
PotentialUserSourceInfoDlg.clearData = function() {
    this.potentialUserSourceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PotentialUserSourceInfoDlg.set = function(key, val) {
    this.potentialUserSourceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PotentialUserSourceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PotentialUserSourceInfoDlg.close = function() {
    parent.layer.close(window.parent.PotentialUserSource.layerIndex);
}

/**
 * 收集数据
 */
PotentialUserSourceInfoDlg.collectData = function() {
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
PotentialUserSourceInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
PotentialUserSourceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/potentialUserSource/add", function(data){
        Feng.success("添加成功!");
        window.parent.PotentialUserSource.table.refresh();
        PotentialUserSourceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.potentialUserSourceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PotentialUserSourceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/potentialUserSource/update", function(data){
        Feng.success("修改成功!");
        window.parent.PotentialUserSource.table.refresh();
        PotentialUserSourceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.potentialUserSourceInfoData);
    ajax.start();
}

$(function() {

});
