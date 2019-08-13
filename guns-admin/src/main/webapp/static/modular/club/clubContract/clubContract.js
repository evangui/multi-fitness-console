/**
 * 俱乐部合同管理初始化
 */
var ClubContract = {
    id: "ClubContractTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ClubContract.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '合同名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '合同类型 1时间卡合同  2私教卡合同 3次卡合同', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '合同编号', field: 'contractNum', visible: true, align: 'center', valign: 'middle'},
            {title: '是否隐藏', field: 'hidden', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '合同正文', field: 'content', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ClubContract.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ClubContract.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部合同
 */
ClubContract.openAddClubContract = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部合同',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/clubContract/clubContract_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部合同详情
 */
ClubContract.openClubContractDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部合同详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/clubContract/clubContract_update/' + ClubContract.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部合同
 */
ClubContract.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/clubContract/delete", function (data) {
            Feng.success("删除成功!");
            ClubContract.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("clubContractId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ClubContract.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询俱乐部合同列表
 */
ClubContract.search = function () {
    
    ClubContract.table.refresh({query: ClubContract.formParams()});
};

$(function () {
    var defaultColunms = ClubContract.initColumn();
    var table = new BSTable(ClubContract.id, "/clubContract/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubContract.formParams());
    
    ClubContract.table = table.init();
});
