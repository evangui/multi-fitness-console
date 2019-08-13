/**
 * 初始化团操教室详情对话框
 */
var SyllabusClassroomInfoDlg = {
    syllabusClassroomInfoData : {}
};

/**
 * 清除数据
 */
SyllabusClassroomInfoDlg.clearData = function() {
    this.syllabusClassroomInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusClassroomInfoDlg.set = function(key, val) {
    this.syllabusClassroomInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusClassroomInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SyllabusClassroomInfoDlg.close = function() {
    parent.layer.close(window.parent.SyllabusClassroom.layerIndex);
}

/**
 * 收集数据
 */
SyllabusClassroomInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('name')
    .set('number')
    .set('bindSyllabusNum')
    .set('disNums')
    .set('insertTime')
    .set('status');
}
/**
 * 验证数据是否为空
 */
SyllabusClassroomInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SyllabusClassroomInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusClassroom/add", function(data){
        Feng.success("添加成功!");
        window.parent.SyllabusClassroom.table.refresh();
        SyllabusClassroomInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusClassroomInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SyllabusClassroomInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusClassroom/update", function(data){
        Feng.success("修改成功!");
        window.parent.SyllabusClassroom.table.refresh();
        SyllabusClassroomInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusClassroomInfoData);
    ajax.start();
}

$(function() {

});
