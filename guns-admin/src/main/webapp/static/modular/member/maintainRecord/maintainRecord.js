/**
 * 维护记录管理初始化
 */
var MaintainRecord = {
    id: "MaintainRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MaintainRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '会员id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: 'vip id', field: 'vipUserId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '是否是vip用户 0否 1是', field: 'vipUserType', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '跟进内容说明', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '是否是待办 0否 1是', field: 'isTodo', visible: true, align: 'center', valign: 'middle'},
            {title: '跟进类型 0潜在客户跟进 3vip用户跟进', field: 'recordType', visible: true, align: 'center', valign: 'middle'},
            {title: '状态,字段待定', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
    ];
};

/**
 * 检查是否选中
 */
MaintainRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MaintainRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加维护记录
 */
MaintainRecord.openAddMaintainRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加维护记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/maintainRecord/maintainRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看维护记录详情
 */
MaintainRecord.openMaintainRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '维护记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/maintainRecord/maintainRecord_update/' + MaintainRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除维护记录
 */
MaintainRecord.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/maintainRecord/delete", function (data) {
            Feng.success("删除成功!");
            MaintainRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("maintainRecordId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
MaintainRecord.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询维护记录列表
 */
MaintainRecord.search = function () {
    
    MaintainRecord.table.refresh({query: MaintainRecord.formParams()});
};

$(function () {
    var defaultColunms = MaintainRecord.initColumn();
    var table = new BSTable(MaintainRecord.id, "/maintainRecord/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(MaintainRecord.formParams());
    
    MaintainRecord.table = table.init();
});
