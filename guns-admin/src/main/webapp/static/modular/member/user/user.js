/**
 * 用户管理初始化
 */
var User = {
    id: "UserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
User.initColumn = function () {
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
User.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        User.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加用户
 */
User.openAddUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加用户',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/user/user_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看用户详情
 */
User.openUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '用户详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/user/user_update/' + User.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
User.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/user/delete", function (data) {
            Feng.success("删除成功!");
            User.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
User.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询用户列表
 */
User.search = function () {
    
    User.table.refresh({query: User.formParams()});
};

$(function () {
    var defaultColunms = User.initColumn();
    var table = new BSTable(User.id, "/user/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(User.formParams());
    
    User.table = table.init();
});
