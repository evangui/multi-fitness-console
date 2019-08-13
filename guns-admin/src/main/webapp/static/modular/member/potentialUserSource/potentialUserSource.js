/**
 * 客户来源自定义字段管理初始化
 */
var PotentialUserSource = {
    id: "PotentialUserSourceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PotentialUserSource.initColumn = function () {
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
PotentialUserSource.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PotentialUserSource.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加客户来源自定义字段
 */
PotentialUserSource.openAddPotentialUserSource = function () {
    var index = layer.open({
        type: 2,
        title: '添加客户来源自定义字段',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/potentialUserSource/potentialUserSource_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看客户来源自定义字段详情
 */
PotentialUserSource.openPotentialUserSourceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '客户来源自定义字段详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/potentialUserSource/potentialUserSource_update/' + PotentialUserSource.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除客户来源自定义字段
 */
PotentialUserSource.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/potentialUserSource/delete", function (data) {
            Feng.success("删除成功!");
            PotentialUserSource.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("potentialUserSourceId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PotentialUserSource.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询客户来源自定义字段列表
 */
PotentialUserSource.search = function () {
    
    PotentialUserSource.table.refresh({query: PotentialUserSource.formParams()});
};

$(function () {
    var defaultColunms = PotentialUserSource.initColumn();
    var table = new BSTable(PotentialUserSource.id, "/potentialUserSource/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PotentialUserSource.formParams());
    
    PotentialUserSource.table = table.init();
});
