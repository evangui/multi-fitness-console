/**
 * 俱乐部普通用户管理初始化
 */
var UserClub = {
    id: "UserClubTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserClub.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '会员id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '会员头像', field: 'avatar', visible: true, align: 'center', valign: 'middle'},
            {title: '会员性别（0未知 1男 2女）', field: 'gender', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UserClub.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserClub.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部普通用户
 */
UserClub.openAddUserClub = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部普通用户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userClub/userClub_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部普通用户详情
 */
UserClub.openUserClubDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部普通用户详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userClub/userClub_update/' + UserClub.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部普通用户
 */
UserClub.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userClub/delete", function (data) {
            Feng.success("删除成功!");
            UserClub.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userClubId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
UserClub.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');
    return queryData;
}

/**
 * 查询俱乐部普通用户列表
 */
UserClub.search = function () {
	UserClub.table.refresh({query: UserClub.formParams()});
};

$(function () {
    var defaultColunms = UserClub.initColumn();
    var table = new BSTable(UserClub.id, "/userClub/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(UserClub.formParams());
    UserClub.table = table.init();
});
