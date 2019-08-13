/**
 * 初始化卡种详情对话框
 */
var CardCategoryInfoDlg = {
    cardCategoryInfoData : {}
};

/**
 * 清除数据
 */
CardCategoryInfoDlg.clearData = function() {
    this.cardCategoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardCategoryInfoDlg.set = function(key, val) {
    this.cardCategoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardCategoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardCategoryInfoDlg.close = function() {
    parent.layer.close(window.parent.CardCategory.layerIndex);
}

/**
 * 收集数据
 */
CardCategoryInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('title')
    .set('expireTime')
    .set('expireDay')
    .set('value')
    .set('leaveMaxDay')
    .set('enabled')
    .set('shareRestrict')
    .set('cardType');
}
/**
 * 验证数据是否为空
 */
CardCategoryInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
CardCategoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardCategory/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardCategory.table.refresh();
        CardCategoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardCategoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardCategoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardCategory/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardCategory.table.refresh();
        CardCategoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardCategoryInfoData);
    ajax.start();
}

$(function() {

});
