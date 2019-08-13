/**
 * 初始化储值卡消费记录详情对话框
 */
var CardStoredvaluecardSpendInfoDlg = {
    cardStoredvaluecardSpendInfoData : {}
};

/**
 * 清除数据
 */
CardStoredvaluecardSpendInfoDlg.clearData = function() {
    this.cardStoredvaluecardSpendInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardStoredvaluecardSpendInfoDlg.set = function(key, val) {
    this.cardStoredvaluecardSpendInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardStoredvaluecardSpendInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardStoredvaluecardSpendInfoDlg.close = function() {
    parent.layer.close(window.parent.CardStoredvaluecardSpend.layerIndex);
}

/**
 * 收集数据
 */
CardStoredvaluecardSpendInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('cardId')
    .set('cardNumber')
    .set('title')
    .set('userId')
    .set('realname')
    .set('spendMoney')
    .set('insertTime')
    .set('rcepteionId')
    .set('rcepteionName')
    .set('coachId')
    .set('coachName')
    .set('membershipId')
    .set('membershipName')
    .set('opeUserId')
    .set('opeUsername')
    .set('phone')
    .set('remark')
    .set('bankremark')
    .set('cardStatus')
    .set('port');
}

/**
 * 提交添加
 */
CardStoredvaluecardSpendInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecardSpend/add", function(data){
        Feng.success("添加成功!");
        window.parent.CardStoredvaluecardSpend.table.refresh();
        CardStoredvaluecardSpendInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardStoredvaluecardSpendInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardStoredvaluecardSpendInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecardSpend/update", function(data){
        Feng.success("修改成功!");
        window.parent.CardStoredvaluecardSpend.table.refresh();
        CardStoredvaluecardSpendInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardStoredvaluecardSpendInfoData);
    ajax.start();
}

$(function() {

});
