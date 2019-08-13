/**
 * 初始化俱乐部广告详情对话框
 */
var ClubAdvInfoDlg = {
    clubAdvInfoData : {}
};

/**
 * 清除数据
 */
ClubAdvInfoDlg.clearData = function() {
    this.clubAdvInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubAdvInfoDlg.set = function(key, val) {
    this.clubAdvInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubAdvInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubAdvInfoDlg.close = function() {
    parent.layer.close(window.parent.ClubAdv.layerIndex);
}

/**
 * 收集数据
 */
ClubAdvInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('posId')
    .set('posName')
    .set('advUrlType')
    .set('advTitle')
    .set('class')
    .set('content')
    .set('starttime')
    .set('endtime')
    .set('sort')
    .set('clickNum');
}
/**
 * 验证数据是否为空
 */
ClubAdvInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ClubAdvInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubAdv/add", function(data){
        Feng.success("添加成功!");
        window.parent.ClubAdv.table.refresh();
        ClubAdvInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubAdvInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubAdvInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubAdv/update", function(data){
        Feng.success("修改成功!");
        window.parent.ClubAdv.table.refresh();
        ClubAdvInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubAdvInfoData);
    ajax.start();
}

$(function() {

});
