/**
 * 初始化课程种类详情对话框
 */
var SyllabusGroupInfoDlg = {
    syllabusGroupInfoData : {}
};

/**
 * 清除数据
 */
SyllabusGroupInfoDlg.clearData = function() {
    this.syllabusGroupInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusGroupInfoDlg.set = function(key, val) {
    this.syllabusGroupInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusGroupInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SyllabusGroupInfoDlg.close = function() {
    parent.layer.close(window.parent.SyllabusGroup.layerIndex);
}

/**
 * 收集数据
 */
SyllabusGroupInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('courseType')
    .set('type')
    .set('title')
    .set('place')
    .set('classroomId')
    .set('peopleCount')
    .set('lowerLimit')
    .set('isSelectNum')
    .set('isSubDeductNum')
    .set('coachId')
    .set('coachName')
    .set('temporaryCoach')
    .set('tags')
    .set('description')
    .set('content')
    .set('isVipUserSub')
    .set('image')
    .set('startTime')
    .set('endTime')
    .set('openGroupDesc')
    .set('insertTime')
    .set('selfCancel')
    .set('hidden');
}
/**
 * 验证数据是否为空
 */
SyllabusGroupInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SyllabusGroupInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusGroup/add", function(data){
        Feng.success("添加成功!");
        window.parent.SyllabusGroup.table.refresh();
        SyllabusGroupInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusGroupInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SyllabusGroupInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusGroup/update", function(data){
        Feng.success("修改成功!");
        window.parent.SyllabusGroup.table.refresh();
        SyllabusGroupInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusGroupInfoData);
    ajax.start();
}

$(function() {

});
