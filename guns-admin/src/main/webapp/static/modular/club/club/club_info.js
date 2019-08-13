/**
 * 初始化俱乐部主体信息详情对话框
 */
var ClubInfoDlg = {
    clubInfoData : {},
    validateFields: {
    	name: {
    		validators: {
                notEmpty: {
                    message: '俱乐部名称不能为空'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
ClubInfoDlg.clearData = function() {
    this.clubInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubInfoDlg.set = function(key, val) {
    this.clubInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubInfoDlg.close = function() {
    parent.layer.close(window.parent.Club.layerIndex);
}

/**
 * 收集数据
 */
ClubInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('clubSort')
    .set('abbreviate')
    .set('logo')
    .set('clubPhone')
    .set('background')
    .set('qrcodeImg')
    .set('microWebsite')
    .set('lng')
    .set('lat')
    .set('clubProvince')
    .set('clubCity')
    .set('clubArea')
    .set('clubAddress')
    .set('clubStyle')
    .set('parentId')
    .set('trialClub')
    .set('remarkBy')
    .set('userCount')
    .set('startTime')
    .set('expireTime')
    .set('notEnoughCancelTime')
    .set('shorturl')
    .set('cashAmount')
    .set('applyTime')
    .set('applyNum')
    .set('chat')
    .set('pointsStatus');
    
    console.log(this.clubInfoData['expireTime'])
    if ("0" != this.clubInfoData['expireTime'] && this.clubInfoData['expireTime']) {
    	this.set('expireTime', Math.floor(new Date(this.get('expireTime')).getTime()/1000));
    }
    if ("0" != this.clubInfoData['startTime'] && this.clubInfoData['startTime']) {
    	this.set('startTime', Math.floor(new Date(this.get('startTime')).getTime()/1000));
    }
    if ("0" != this.clubInfoData['applyTime'] && this.clubInfoData['applyTime']) {
    	this.set('applyTime', Math.floor(new Date(this.get('applyTime')).getTime()/1000));
    }
    
}

/**
 * 验证数据是否为空
 */
ClubInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    return $("#defaultForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
ClubInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/club/add", function(data){
        Feng.success("添加成功!");
        window.parent.Club.table.refresh();
        ClubInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/club/update", function(data){
        Feng.success("修改成功!");
        window.parent.Club.table.refresh();
        ClubInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubInfoData);
    ajax.start();
}

$(function() {
	//初始化性别选项
    $("#clubStyle").val($("#clubStyleValue").val());
    $("#trialClub").val($("#trialClubValue").val());
    
	Feng.initValidator("defaultForm", ClubInfoDlg.validateFields);
});
