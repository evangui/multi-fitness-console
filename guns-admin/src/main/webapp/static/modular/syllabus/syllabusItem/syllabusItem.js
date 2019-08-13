/**
 * 团操课排期表课程管理初始化
 */
var SyllabusItem = {
    id: "SyllabusItemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SyllabusItem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程种类id', field: 'groupId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程排期设置id', field: 'scheduleSettingId', visible: true, align: 'center', valign: 'middle'},
            {title: '日', field: 'day', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '已预约人数', field: 'hasSubscribe', visible: true, align: 'center', valign: 'middle'},
            {title: '课程状态  1未上课  2已上课 3已取消', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '课程名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '临时教练', field: 'temporaryCoach', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SyllabusItem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SyllabusItem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加团操课排期表课程
 */
SyllabusItem.openAddSyllabusItem = function () {
    var index = layer.open({
        type: 2,
        title: '添加团操课排期表课程',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/syllabusItem/syllabusItem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看团操课排期表课程详情
 */
SyllabusItem.openSyllabusItemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '团操课排期表课程详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/syllabusItem/syllabusItem_update/' + SyllabusItem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除团操课排期表课程
 */
SyllabusItem.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/syllabusItem/delete", function (data) {
            Feng.success("删除成功!");
            SyllabusItem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("syllabusItemId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SyllabusItem.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询团操课排期表课程列表
 */
SyllabusItem.search = function () {
    
    SyllabusItem.table.refresh({query: SyllabusItem.formParams()});
};

$(function () {
    var defaultColunms = SyllabusItem.initColumn();
    var table = new BSTable(SyllabusItem.id, "/syllabusItem/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SyllabusItem.formParams());
    
    SyllabusItem.table = table.init();
});
