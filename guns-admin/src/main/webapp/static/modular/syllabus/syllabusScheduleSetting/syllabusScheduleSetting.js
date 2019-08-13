/**
 * 团操课排期设置管理初始化
 */
var SyllabusScheduleSetting = {
    id: "SyllabusScheduleSettingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SyllabusScheduleSetting.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程种类id', field: 'groupId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '删除时间', field: 'delTime', visible: true, align: 'center', valign: 'middle'},
            {title: '总课程数', field: 'lessonTotal', visible: true, align: 'center', valign: 'middle'},
            {title: '操作人', field: 'delManager', visible: true, align: 'center', valign: 'middle'},
            {title: '周每日排期配置', field: 'week', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 1正常 0已删除', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SyllabusScheduleSetting.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SyllabusScheduleSetting.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加团操课排期设置
 */
SyllabusScheduleSetting.openAddSyllabusScheduleSetting = function () {
    var index = layer.open({
        type: 2,
        title: '添加团操课排期设置',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/syllabusScheduleSetting/syllabusScheduleSetting_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看团操课排期设置详情
 */
SyllabusScheduleSetting.openSyllabusScheduleSettingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '团操课排期设置详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/syllabusScheduleSetting/syllabusScheduleSetting_update/' + SyllabusScheduleSetting.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除团操课排期设置
 */
SyllabusScheduleSetting.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/syllabusScheduleSetting/delete", function (data) {
            Feng.success("删除成功!");
            SyllabusScheduleSetting.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("syllabusScheduleSettingId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SyllabusScheduleSetting.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询团操课排期设置列表
 */
SyllabusScheduleSetting.search = function () {
    
    SyllabusScheduleSetting.table.refresh({query: SyllabusScheduleSetting.formParams()});
};

$(function () {
    var defaultColunms = SyllabusScheduleSetting.initColumn();
    var table = new BSTable(SyllabusScheduleSetting.id, "/syllabusScheduleSetting/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SyllabusScheduleSetting.formParams());
    
    SyllabusScheduleSetting.table = table.init();
});
