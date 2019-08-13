/**
 * 初始化团操课预约记录详情对话框
 */
var SyllabusSubscribeRecordInfoDlg = {
    syllabusSubscribeRecordInfoData : {}
};

/**
 * 清除数据
 */
SyllabusSubscribeRecordInfoDlg.clearData = function() {
    this.syllabusSubscribeRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusSubscribeRecordInfoDlg.set = function(key, val) {
    this.syllabusSubscribeRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SyllabusSubscribeRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SyllabusSubscribeRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.SyllabusSubscribeRecord.layerIndex);
}

/**
 * 收集数据
 */
SyllabusSubscribeRecordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('syllabusId')
    .set('syllabusName')
    .set('groupId')
    .set('vipId')
    .set('realname')
    .set('phone')
    .set('classroomId')
    .set('seatNumber')
    .set('classStartTime')
    .set('classEndTime')
    .set('cardType')
    .set('cardId')
    .set('cardDeductNum')
    .set('coachId')
    .set('coachName')
    .set('MembershipId')
    .set('MembershipName')
    .set('remark')
    .set('insertTime')
    .set('userType')
    .set('status');
}
/**
 * 验证数据是否为空
 */
SyllabusSubscribeRecordInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
SyllabusSubscribeRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusSubscribeRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.SyllabusSubscribeRecord.table.refresh();
        SyllabusSubscribeRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusSubscribeRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SyllabusSubscribeRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/syllabusSubscribeRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.SyllabusSubscribeRecord.table.refresh();
        SyllabusSubscribeRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.syllabusSubscribeRecordInfoData);
    ajax.start();
}

$(function() {

});
