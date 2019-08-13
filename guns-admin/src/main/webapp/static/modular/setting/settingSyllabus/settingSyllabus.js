/**
 * 团操课设置管理初始化
 */
var SettingSyllabus = {
    id: "SettingSyllabusTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettingSyllabus.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '预约开始时间（秒）', field: 'subscribeStartTime', visible: true, align: 'center', valign: 'middle'},
            {title: '预约结束时间', field: 'subscribeEndTime', visible: true, align: 'center', valign: 'middle'},
            {title: '人数不足自动取消时间', field: 'notEnoughCancelTime', visible: true, align: 'center', valign: 'middle'},
            {title: '会员自主取消时间', field: 'selfCancelTime', visible: true, align: 'center', valign: 'middle'},
            {title: '团操课查看开始时间戳', field: 'startDate', visible: true, align: 'center', valign: 'middle'},
            {title: '团操课查看结束时间戳', field: 'endDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SettingSyllabus.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettingSyllabus.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加团操课设置
 */
SettingSyllabus.openAddSettingSyllabus = function () {
    var index = layer.open({
        type: 2,
        title: '添加团操课设置',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settingSyllabus/settingSyllabus_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看团操课设置详情
 */
SettingSyllabus.openSettingSyllabusDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '团操课设置详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settingSyllabus/settingSyllabus_update/' + SettingSyllabus.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除团操课设置
 */
SettingSyllabus.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/settingSyllabus/delete", function (data) {
            Feng.success("删除成功!");
            SettingSyllabus.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settingSyllabusId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SettingSyllabus.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询团操课设置列表
 */
SettingSyllabus.search = function () {
    
    SettingSyllabus.table.refresh({query: SettingSyllabus.formParams()});
};

$(function () {
    var defaultColunms = SettingSyllabus.initColumn();
    var table = new BSTable(SettingSyllabus.id, "/settingSyllabus/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SettingSyllabus.formParams());
    
    SettingSyllabus.table = table.init();
});
