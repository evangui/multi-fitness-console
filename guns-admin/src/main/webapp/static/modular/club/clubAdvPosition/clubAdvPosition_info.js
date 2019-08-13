/**
 * 初始化俱乐部广告位详情对话框
 */
var ClubAdvPositionInfoDlg = {
    clubAdvPositionInfoData : {}
};

/**
 * 清除数据
 */
ClubAdvPositionInfoDlg.clearData = function() {
    this.clubAdvPositionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubAdvPositionInfoDlg.set = function(key, val) {
    this.clubAdvPositionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubAdvPositionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubAdvPositionInfoDlg.close = function() {
    parent.layer.close(window.parent.ClubAdvPosition.layerIndex);
}

/**
 * 收集数据
 */
ClubAdvPositionInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('name')
    .set('class')
    .set('display')
    .set('enable')
    .set('width')
    .set('height')
    .set('clickNum')
    .set('defaultContent');
}
/**
 * 验证数据是否为空
 */
ClubAdvPositionInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ClubAdvPositionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubAdvPosition/add", function(data){
        Feng.success("添加成功!");
        window.parent.ClubAdvPosition.table.refresh();
        ClubAdvPositionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubAdvPositionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubAdvPositionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubAdvPosition/update", function(data){
        Feng.success("修改成功!");
        window.parent.ClubAdvPosition.table.refresh();
        ClubAdvPositionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubAdvPositionInfoData);
    ajax.start();
}

$(function() {

});
