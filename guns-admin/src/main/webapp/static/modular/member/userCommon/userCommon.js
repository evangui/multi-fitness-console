/**
 * 注册用户管理初始化
 */
var UserCommon = {
    id: "UserCommonTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserCommon.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '会员id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '会员密码', field: 'passwd', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '会员头像', field: 'avatar', visible: true, align: 'center', valign: 'middle'},
            {title: '会员性别（0：女，1：男）', field: 'gender', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '国家号码编号', field: 'country', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'weixinOpenid', visible: true, align: 'center', valign: 'middle'},
            {title: '微信用户统一标识', field: 'weixinUnionid', visible: true, align: 'center', valign: 'middle'},
            {title: '微信用户相关信息', field: 'weixinInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '登录次数', field: 'loginNum', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '用户注册IP', field: 'regIp', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '上次活动时间', field: 'lastActiveTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '用户标签', field: 'labels', visible: true, align: 'center', valign: 'middle'},
            {title: '是否系统虚拟用户', field: 'virtual', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UserCommon.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserCommon.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加注册用户
 */
UserCommon.openAddUserCommon = function () {
    var index = layer.open({
        type: 2,
        title: '添加注册用户',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userCommon/userCommon_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看注册用户详情
 */
UserCommon.openUserCommonDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '注册用户详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userCommon/userCommon_update/' + UserCommon.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除注册用户
 */
UserCommon.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/userCommon/delete", function (data) {
            Feng.success("删除成功!");
            UserCommon.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userCommonId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
UserCommon.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询注册用户列表
 */
UserCommon.search = function () {
    
    UserCommon.table.refresh({query: UserCommon.formParams()});
};

$(function () {
    var defaultColunms = UserCommon.initColumn();
    var table = new BSTable(UserCommon.id, "/userCommon/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(UserCommon.formParams());
    
    UserCommon.table = table.init();
});
