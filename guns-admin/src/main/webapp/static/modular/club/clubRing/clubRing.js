/**
 * 手环管理初始化
 */
var ClubRing = {
    id: "ClubRingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ClubRing.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '手环编号', field: 'ringNum', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '入场记录id', field: 'admissionRecordId', visible: true, align: 'center', valign: 'middle'},
            {title: '入场备注', field: 'admissionRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '借出时间', field: 'borrowTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '归还时间', field: 'returnTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '借出手环是否未还', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ClubRing.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ClubRing.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加手环
 */
ClubRing.openAddClubRing = function () {
    var index = layer.open({
        type: 2,
        title: '添加手环',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/clubRing/clubRing_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看手环详情
 */
ClubRing.openClubRingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '手环详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/clubRing/clubRing_update/' + ClubRing.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除手环
 */
ClubRing.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/clubRing/delete", function (data) {
            Feng.success("删除成功!");
            ClubRing.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("clubRingId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ClubRing.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询手环列表
 */
ClubRing.search = function () {
    
    ClubRing.table.refresh({query: ClubRing.formParams()});
};

$(function () {
    var defaultColunms = ClubRing.initColumn();
    var table = new BSTable(ClubRing.id, "/clubRing/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubRing.formParams());
    
    ClubRing.table = table.init();
});
