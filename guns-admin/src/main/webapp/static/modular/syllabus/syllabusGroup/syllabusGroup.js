/**
 * 课程种类管理初始化
 */
var SyllabusGroup = {
    id: "SyllabusGroupTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SyllabusGroup.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程类型，是否需要预约', field: 'courseType', visible: true, align: 'center', valign: 'middle'},
            {title: '类型 0团课 1操课', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '课程名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '上课地点', field: 'place', visible: true, align: 'center', valign: 'middle'},
            {title: '教室ID', field: 'classroomId', visible: true, align: 'center', valign: 'middle'},
            {title: '预约上限', field: 'peopleCount', visible: true, align: 'center', valign: 'middle'},
            {title: '开课人数', field: 'lowerLimit', visible: true, align: 'center', valign: 'middle'},
            {title: '预约时是否要选座号', field: 'isSelectNum', visible: true, align: 'center', valign: 'middle'},
            {title: '预约时是否先扣费', field: 'isSubDeductNum', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '临时教练', field: 'temporaryCoach', visible: true, align: 'center', valign: 'middle'},
            {title: '关键词', field: 'tags', visible: true, align: 'center', valign: 'middle'},
            {title: '简介', field: 'description', visible: true, align: 'center', valign: 'middle'},
            {title: '内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '非会员能否预约', field: 'isVipUserSub', visible: true, align: 'center', valign: 'middle'},
            {title: '课程图片，单张', field: 'image', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '开课说明', field: 'openGroupDesc', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '用户能否取消', field: 'selfCancel', visible: true, align: 'center', valign: 'middle'},
            {title: '是否隐藏', field: 'hidden', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SyllabusGroup.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SyllabusGroup.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加课程种类
 */
SyllabusGroup.openAddSyllabusGroup = function () {
    var index = layer.open({
        type: 2,
        title: '添加课程种类',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/syllabusGroup/syllabusGroup_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看课程种类详情
 */
SyllabusGroup.openSyllabusGroupDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程种类详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/syllabusGroup/syllabusGroup_update/' + SyllabusGroup.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除课程种类
 */
SyllabusGroup.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/syllabusGroup/delete", function (data) {
            Feng.success("删除成功!");
            SyllabusGroup.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("syllabusGroupId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SyllabusGroup.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询课程种类列表
 */
SyllabusGroup.search = function () {
    
    SyllabusGroup.table.refresh({query: SyllabusGroup.formParams()});
};

$(function () {
    var defaultColunms = SyllabusGroup.initColumn();
    var table = new BSTable(SyllabusGroup.id, "/syllabusGroup/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SyllabusGroup.formParams());
    
    SyllabusGroup.table = table.init();
});
