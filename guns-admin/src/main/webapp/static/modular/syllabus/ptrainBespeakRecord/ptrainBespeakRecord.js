/**
 * 私教预约记录管理初始化
 */
var PtrainBespeakRecord = {
    id: "PtrainBespeakRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PtrainBespeakRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '预约开始时间', field: 'fromTime', visible: true, align: 'center', valign: 'middle'},
            {title: '预约结束时间', field: 'toTime', visible: true, align: 'center', valign: 'middle'},
            {title: '预约日期，格式YY-mm-dd', field: 'date', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'MembershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'MembershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '预约时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '卡类型', field: 'cardType', visible: true, align: 'center', valign: 'middle'},
            {title: '卡id', field: 'cardId', visible: true, align: 'center', valign: 'middle'},
            {title: '扣除次数', field: 'cardDeductNum', visible: true, align: 'center', valign: 'middle'},
            {title: '预约记录类型 0未知 1学生约教练 2教练约学生 5教练休息-非预约 ', field: 'recordType', visible: true, align: 'center', valign: 'middle'},
            {title: '预约人类型 0非会员 1vip会员 2私教会员 ', field: 'userType', visible: true, align: 'center', valign: 'middle'},
            {title: '当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PtrainBespeakRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PtrainBespeakRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加私教预约记录
 */
PtrainBespeakRecord.openAddPtrainBespeakRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加私教预约记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ptrainBespeakRecord/ptrainBespeakRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看私教预约记录详情
 */
PtrainBespeakRecord.openPtrainBespeakRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '私教预约记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ptrainBespeakRecord/ptrainBespeakRecord_update/' + PtrainBespeakRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除私教预约记录
 */
PtrainBespeakRecord.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/ptrainBespeakRecord/delete", function (data) {
            Feng.success("删除成功!");
            PtrainBespeakRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ptrainBespeakRecordId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PtrainBespeakRecord.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');
    return queryData;
}

/**
 * 查询私教预约记录列表
 */
PtrainBespeakRecord.search = function () {
	PtrainBespeakRecord.table.refresh({query: PtrainBespeakRecord.formParams()});
};

$(function () {
    var defaultColunms = PtrainBespeakRecord.initColumn();
    var table = new BSTable(PtrainBespeakRecord.id, "/ptrainBespeakRecord/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PtrainBespeakRecord.formParams());
    PtrainBespeakRecord.table = table.init();
});
