/**
 * 初始化团操课排期设置详情对话框
 */
var SyllabusScheduleSettingInfoDlg = {
    syllabusScheduleSettingInfoData : {}
};

/**
 * 清除数据
 */
SyllabusScheduleSettingInfoDlg.clearData = function() {
    this.syllabusScheduleSettingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusScheduleSettingInfoDlg.set = function(key, val) {
    this.syllabusScheduleSettingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusScheduleSettingInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SyllabusScheduleSettingInfoDlg.close = function() {
    parent.layer.close(window.parent.SyllabusScheduleSetting.layerIndex);
}

/**
 * 收集数据
 */
SyllabusScheduleSettingInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('groupId')
    .set('title')
    .set('coachId')
    .set('coachName')
    .set('startTime')
    .set('endTime')
    .set('delTime')
    .set('lessonTotal')
    .set('delManager')
    .set('week')
    .set('insertTime')
    .set('status');
}
/**
 * 验证数据是否为空
 */
SyllabusScheduleSettingInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SyllabusScheduleSettingInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusScheduleSetting/add", function(data){
        Feng.success("添加成功!");
        window.parent.SyllabusScheduleSetting.table.refresh();
        SyllabusScheduleSettingInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusScheduleSettingInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SyllabusScheduleSettingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusScheduleSetting/update", function(data){
        Feng.success("修改成功!");
        window.parent.SyllabusScheduleSetting.table.refresh();
        SyllabusScheduleSettingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusScheduleSettingInfoData);
    ajax.start();
}

$(function() {

});
