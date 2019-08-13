/**
 * 初始化俱乐部管理员详情对话框
 */
var ClubAdminInfoDlg = {
    clubAdminInfoData : {},
    validateFields: {
    	clubId: {
            validators: {
                notEmpty: {
                    message: '所属俱乐部不能为空'
                }
            }
        },
        username: {
            validators: {
                notEmpty: {
                    message: '用户名不能为空'
                }
            }
        },
        roles: {
            validators: {
                notEmpty: {
                    message: '角色不能为空'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
ClubAdminInfoDlg.clearData = function() {
    this.clubAdminInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubAdminInfoDlg.set = function(key, val) {
    this.clubAdminInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClubAdminInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClubAdminInfoDlg.close = function() {
    parent.layer.close(window.parent.ClubAdmin.layerIndex);
}

/**
 * 收集数据
 */
ClubAdminInfoDlg.collectData = function() {
    this
    .set('id')
    .set('clubId')
    .set('username')
    .set('password')
    .set('salt')
    .set('nickname')
    .set('realname')
    .set('phone')
    .set('insertTime')
    .set('loginCount')
    .set('lastLoginTime')
    .set('authority')
    .set('roles');
    
    if ($("#clubSelBtn").attr('data-id') != "0") {
    	this.set('clubId', $("#clubSelBtn").attr('data-id'));
    }
    
}

/**
 * 验证数据是否为空
 */
ClubAdminInfoDlg.validate = function () {
    $('#defaultForm').data("bootstrapValidator").resetForm();
    $('#defaultForm').bootstrapValidator('validate');
    
    return $("#defaultForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
ClubAdminInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    if ("0" == this.clubAdminInfoData["clubId"] || undefined===this.clubAdminInfoData["clubId"]) {
    	Feng.error("管理员所属俱乐部不能为空");
    	return;
    }
    
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubAdmin/add", function(data){
    	if (data.code == 200) {
    		 Feng.success("添加成功!");
	        window.parent.ClubAdmin.table.refresh();
	        ClubAdminInfoDlg.close();
    	} else {
    		Feng.error("添加失败!" + data.message + "!");
    	}
       
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubAdminInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ClubAdminInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    
    if ("0" == this.clubAdminInfoData["clubId"] || undefined===this.clubAdminInfoData["clubId"]) {
    	Feng.error("管理员所属俱乐部不能为空");
    	return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/clubAdmin/update", function(data){
        Feng.success("修改成功!");
        window.parent.ClubAdmin.table.refresh();
        ClubAdminInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.clubAdminInfoData);
    ajax.start();
}

$(function() {
	//初始化性别选项
    $("#roles").val($("#rolesValue").val());
    
	Feng.initValidator("defaultForm", ClubAdminInfoDlg.validateFields);
});
