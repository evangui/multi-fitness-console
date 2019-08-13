/**
 * 签到记录管理初始化
 */
var CheckinRecord = {
    id: "CheckinRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CheckinRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},,
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '最近签到时间', field: 'lastCheckInTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '签退时间', field: 'checkOutTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '入场次数', field: 'admissionRecordCount', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '代签用户ID', field: 'opeUserId', visible: true, align: 'center', valign: 'middle'},
            {title: '代签用户名', field: 'opeUsername', visible: true, align: 'center', valign: 'middle'},
            {title: '入场状态 3已入场  2已出场', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '财务是否确认', field: 'banksure', visible: true, align: 'center', valign: 'middle'},
            {title: '财务备注', field: 'bankRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '借出手环是否未还', field: 'ringStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '消次端口 0默认 1前台电脑签到 2前台手机签到 3扫一扫签到 4指纹门禁签到 5智能门禁签到', field: 'port', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CheckinRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CheckinRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加签到记录
 */
CheckinRecord.openAddCheckinRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加签到记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/checkinRecord/checkinRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看签到记录详情
 */
CheckinRecord.openCheckinRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '签到记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/checkinRecord/checkinRecord_update/' + CheckinRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除签到记录
 */
CheckinRecord.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/checkinRecord/delete", function (data) {
            Feng.success("删除成功!");
            CheckinRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("checkinRecordId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
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
 * 查询签到记录列表
 */
CheckinRecord.search = function () {
    
    CheckinRecord.table.refresh({query: ClubAdmin.formParams()});
};

$(function () {
    var defaultColunms = CheckinRecord.initColumn();
    var table = new BSTable(CheckinRecord.id, "/checkinRecord/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubAdmin.formParams());
    
    CheckinRecord.table = table.init();
});
