/**
 * 自定义客户来源管理初始化
 */
var UserSource = {
    id: "UserSourceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserSource.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '来源名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '是否禁用', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'sort', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UserSource.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserSource.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加自定义客户来源
 */
UserSource.openAddUserSource = function () {
    var index = layer.open({
        type: 2,
        title: '添加自定义客户来源',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userSource/userSource_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看自定义客户来源详情
 */
UserSource.openUserSourceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '自定义客户来源详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userSource/userSource_update/' + UserSource.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除自定义客户来源
 */
UserSource.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/userSource/delete", function (data) {
            Feng.success("删除成功!");
            UserSource.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userSourceId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
UserSource.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询自定义客户来源列表
 */
UserSource.search = function () {
    
    UserSource.table.refresh({query: UserSource.formParams()});
};

$(function () {
    var defaultColunms = UserSource.initColumn();
    var table = new BSTable(UserSource.id, "/userSource/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(UserSource.formParams());
    
    UserSource.table = table.init();
});
