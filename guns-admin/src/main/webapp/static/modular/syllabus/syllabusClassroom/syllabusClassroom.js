/**
 * 团操教室管理初始化
 */
var SyllabusClassroom = {
    id: "SyllabusClassroomTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SyllabusClassroom.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '教室名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '座次数量', field: 'number', visible: true, align: 'center', valign: 'middle'},
            {title: '绑定课程数', field: 'bindSyllabusNum', visible: true, align: 'center', valign: 'middle'},
            {title: '禁用座次', field: 'disNums', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 含义待定', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SyllabusClassroom.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SyllabusClassroom.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加团操教室
 */
SyllabusClassroom.openAddSyllabusClassroom = function () {
    var index = layer.open({
        type: 2,
        title: '添加团操教室',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/syllabusClassroom/syllabusClassroom_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看团操教室详情
 */
SyllabusClassroom.openSyllabusClassroomDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '团操教室详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/syllabusClassroom/syllabusClassroom_update/' + SyllabusClassroom.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除团操教室
 */
SyllabusClassroom.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/syllabusClassroom/delete", function (data) {
            Feng.success("删除成功!");
            SyllabusClassroom.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("syllabusClassroomId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SyllabusClassroom.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询团操教室列表
 */
SyllabusClassroom.search = function () {
    
    SyllabusClassroom.table.refresh({query: SyllabusClassroom.formParams()});
};

$(function () {
    var defaultColunms = SyllabusClassroom.initColumn();
    var table = new BSTable(SyllabusClassroom.id, "/syllabusClassroom/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SyllabusClassroom.formParams());
    
    SyllabusClassroom.table = table.init();
});
