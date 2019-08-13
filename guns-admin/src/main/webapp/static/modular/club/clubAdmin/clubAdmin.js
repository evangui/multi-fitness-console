/**
 * 俱乐部管理员管理初始化
 */
var ClubAdmin = {
    id: "ClubAdminTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ClubAdmin.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: false, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '用户名', field: 'username', visible: true, align: 'center', valign: 'middle'},
            {title: '密码', field: 'password', visible: false, align: 'center', valign: 'middle'},
            {title: 'md5密码盐', field: 'salt', visible: false, align: 'center', valign: 'middle'},
            {title: '昵称', field: 'nickname', visible: false, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '登录次数', field: 'loginCount', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '上次登录时间', field: 'lastLoginTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '权限', field: 'authority', visible: false, align: 'center', valign: 'middle'},
            {title: '角色', field: 'roles', visible: true, align: 'center', valign: 'middle', formatter: ClubAdmin.formatter.roles },
    ];
};

/**
 * 格式化方法
 */
ClubAdmin.formatter = {
		roles: function(value,row,index) {
		if (value == '超级管理员') {
			return '<small class="label label-warning">超级管理员</small>';
		} else {
			return '<small class="label label-default">' + value + '</small>';
		}
	},
 
};

/**
 * 检查是否选中
 */
ClubAdmin.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ClubAdmin.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部管理员
 */
ClubAdmin.openAddClubAdmin = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部管理员',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/clubAdmin/clubAdmin_add?clubId=' + $("#clubSelBtn").attr('data-id')
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部管理员详情
 */
ClubAdmin.openClubAdminDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部管理员详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/clubAdmin/clubAdmin_update/' + ClubAdmin.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部管理员
 */
ClubAdmin.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/clubAdmin/delete", function (data) {
	            Feng.success("删除成功!");
	            ClubAdmin.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("clubAdminId",ClubAdmin.seItem.id);
	        ajax.start();
    	}
    	
    	Feng.confirm("是否删除管理员： " + this.seItem.realname + "?", operation);
    }
    
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ClubAdmin.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询俱乐部管理员列表
 */
ClubAdmin.search = function () {
    ClubAdmin.table.refresh({query: ClubAdmin.formParams()});
};

$(function () {
    var defaultColunms = ClubAdmin.initColumn();
    var table = new BSTable(ClubAdmin.id, "/clubAdmin/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubAdmin.formParams());
    
    ClubAdmin.table = table.init();
});
