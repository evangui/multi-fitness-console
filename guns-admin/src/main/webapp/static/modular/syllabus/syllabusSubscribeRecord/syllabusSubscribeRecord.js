/**
 * 团操课预约记录管理初始化
 */
var SyllabusSubscribeRecord = {
    id: "SyllabusSubscribeRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SyllabusSubscribeRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程id', field: 'syllabusId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程名称', field: 'syllabusName', visible: true, align: 'center', valign: 'middle'},
            {title: '课程种类id', field: 'groupId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '教室id', field: 'classroomId', visible: true, align: 'center', valign: 'middle'},
            {title: '座位号', field: 'seatNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '上课开始时间', field: 'classStartTime', visible: true, align: 'center', valign: 'middle'},
            {title: '上课结束时间', field: 'classEndTime', visible: true, align: 'center', valign: 'middle'},
            {title: '卡类型', field: 'cardType', visible: true, align: 'center', valign: 'middle'},
            {title: '卡id', field: 'cardId', visible: true, align: 'center', valign: 'middle'},
            {title: '扣除次数', field: 'cardDeductNum', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'MembershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'MembershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '预约时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '预约人类型 0非会员 1vip会员 2私教会员 ', field: 'userType', visible: true, align: 'center', valign: 'middle'},
            {title: '当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SyllabusSubscribeRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SyllabusSubscribeRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加团操课预约记录
 */
SyllabusSubscribeRecord.openAddSyllabusSubscribeRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加团操课预约记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/syllabusSubscribeRecord/syllabusSubscribeRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看团操课预约记录详情
 */
SyllabusSubscribeRecord.openSyllabusSubscribeRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '团操课预约记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/syllabusSubscribeRecord/syllabusSubscribeRecord_update/' + SyllabusSubscribeRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除团操课预约记录
 */
SyllabusSubscribeRecord.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/syllabusSubscribeRecord/delete", function (data) {
            Feng.success("删除成功!");
            SyllabusSubscribeRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("syllabusSubscribeRecordId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SyllabusSubscribeRecord.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询团操课预约记录列表
 */
SyllabusSubscribeRecord.search = function () {
    
    SyllabusSubscribeRecord.table.refresh({query: SyllabusSubscribeRecord.formParams()});
};

$(function () {
    var defaultColunms = SyllabusSubscribeRecord.initColumn();
    var table = new BSTable(SyllabusSubscribeRecord.id, "/syllabusSubscribeRecord/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SyllabusSubscribeRecord.formParams());
    
    SyllabusSubscribeRecord.table = table.init();
});
