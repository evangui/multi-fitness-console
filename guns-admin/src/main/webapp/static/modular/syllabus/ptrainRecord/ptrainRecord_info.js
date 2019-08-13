/**
 * 初始化私教记录详情对话框
 */
var PtrainRecordInfoDlg = {
    ptrainRecordInfoData : {}
};

/**
 * 清除数据
 */
PtrainRecordInfoDlg.clearData = function() {
    this.ptrainRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PtrainRecordInfoDlg.set = function(key, val) {
    this.ptrainRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PtrainRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PtrainRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.PtrainRecord.layerIndex);
}

/**
 * 收集数据
 */
PtrainRecordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('vipId')
    .set('onceCardId')
    .set('coachId')
    .set('membershipId')
    .set('receptionId')
    .set('remark')
    .set('receptionRemark')
    .set('unitPrice')
    .set('costs')
    .set('type')
    .set('syllabusId')
    .set('isSyllabusSub')
    .set('userRealname')
    .set('ipAddress')
    .set('userPhone')
    .set('insertTime')
    .set('commentContent')
    .set('commentRank')
    .set('commentTags')
    .set('conformCode');
}
/**
 * 验证数据是否为空
 */
PtrainRecordInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
PtrainRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ptrainRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.PtrainRecord.table.refresh();
        PtrainRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ptrainRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PtrainRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ptrainRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.PtrainRecord.table.refresh();
        PtrainRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ptrainRecordInfoData);
    ajax.start();
}

$(function() {

});
