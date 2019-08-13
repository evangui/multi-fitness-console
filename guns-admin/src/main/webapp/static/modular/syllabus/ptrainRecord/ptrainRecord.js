/**
 * 私教记录管理初始化
 */
var PtrainRecord = {
    id: "PtrainRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PtrainRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '会员id', field: 'vipId', visible: true, align: 'center', valign: 'middle'},
            {title: '次卡id', field: 'onceCardId', visible: true, align: 'center', valign: 'middle'},
            {title: '教练id', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍id', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '前台id', field: 'receptionId', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '前台备注', field: 'receptionRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '单价', field: 'unitPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '扣除次数', field: 'costs', visible: true, align: 'center', valign: 'middle'},
            {title: '类型，含义待定', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '课程id or 私教预约记录id', field: 'syllabusId', visible: true, align: 'center', valign: 'middle'},
            {title: '是否是团操课预约-用以区分团操课or私教课', field: 'isSyllabusSub', visible: true, align: 'center', valign: 'middle'},
            {title: '用户姓名', field: 'userRealname', visible: true, align: 'center', valign: 'middle'},
            {title: '用户ip', field: 'ipAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '用户手机号', field: 'userPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '用户评价', field: 'commentContent', visible: true, align: 'center', valign: 'middle'},
            {title: '评分', field: 'commentRank', visible: true, align: 'center', valign: 'middle'},
            {title: '评论标签，多个用,分隔', field: 'commentTags', visible: true, align: 'center', valign: 'middle'},
            {title: '确认码-标志记录的唯一性-通常在记录id不明确时由客户端生成', field: 'conformCode', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PtrainRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PtrainRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加私教记录
 */
PtrainRecord.openAddPtrainRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加私教记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ptrainRecord/ptrainRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看私教记录详情
 */
PtrainRecord.openPtrainRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '私教记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ptrainRecord/ptrainRecord_update/' + PtrainRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除私教记录
 */
PtrainRecord.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/ptrainRecord/delete", function (data) {
            Feng.success("删除成功!");
            PtrainRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ptrainRecordId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PtrainRecord.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');
    return queryData;
}

/**
 * 查询私教记录列表
 */
PtrainRecord.search = function () {
	PtrainRecord.table.refresh({query: PtrainRecord.formParams()});
};

$(function () {
    var defaultColunms = PtrainRecord.initColumn();
    var table = new BSTable(PtrainRecord.id, "/ptrainRecord/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PtrainRecord.formParams());
    PtrainRecord.table = table.init();
});
