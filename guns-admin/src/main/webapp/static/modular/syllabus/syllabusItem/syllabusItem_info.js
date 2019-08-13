/**
 * 初始化团操课排期表课程详情对话框
 */
var SyllabusItemInfoDlg = {
    syllabusItemInfoData : {}
};

/**
 * 清除数据
 */
SyllabusItemInfoDlg.clearData = function() {
    this.syllabusItemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusItemInfoDlg.set = function(key, val) {
    this.syllabusItemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusItemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SyllabusItemInfoDlg.close = function() {
    parent.layer.close(window.parent.SyllabusItem.layerIndex);
}

/**
 * 收集数据
 */
SyllabusItemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('groupId')
    .set('scheduleSettingId')
    .set('day')
    .set('startTime')
    .set('endTime')
    .set('hasSubscribe')
    .set('status')
    .set('title')
    .set('coachId')
    .set('coachName')
    .set('temporaryCoach')
    .set('insertTime');
}
/**
 * 验证数据是否为空
 */
SyllabusItemInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SyllabusItemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusItem/add", function(data){
        Feng.success("添加成功!");
        window.parent.SyllabusItem.table.refresh();
        SyllabusItemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusItemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SyllabusItemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusItem/update", function(data){
        Feng.success("修改成功!");
        window.parent.SyllabusItem.table.refresh();
        SyllabusItemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusItemInfoData);
    ajax.start();
}

$(function() {

});
