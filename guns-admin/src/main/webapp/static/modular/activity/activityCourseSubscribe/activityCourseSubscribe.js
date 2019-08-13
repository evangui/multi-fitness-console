/**
 * 课程活动报名记录管理初始化
 */
var ActivityCourseSubscribe = {
    id: "ActivityCourseSubscribeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ActivityCourseSubscribe.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '会员id', field: 'vipId', visible: true, align: 'center', valign: 'middle'},
            {title: '活动id', field: 'activityId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '会员名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '是否签到入场', field: 'sign', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ActivityCourseSubscribe.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ActivityCourseSubscribe.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加课程活动报名记录
 */
ActivityCourseSubscribe.openAddActivityCourseSubscribe = function () {
    var index = layer.open({
        type: 2,
        title: '添加课程活动报名记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activityCourseSubscribe/activityCourseSubscribe_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看课程活动报名记录详情
 */
ActivityCourseSubscribe.openActivityCourseSubscribeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程活动报名记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/activityCourseSubscribe/activityCourseSubscribe_update/' + ActivityCourseSubscribe.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除课程活动报名记录
 */
ActivityCourseSubscribe.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/activityCourseSubscribe/delete", function (data) {
            Feng.success("删除成功!");
            ActivityCourseSubscribe.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("activityCourseSubscribeId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ActivityCourseSubscribe.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询课程活动报名记录列表
 */
ActivityCourseSubscribe.search = function () {
    
    ActivityCourseSubscribe.table.refresh({query: ActivityCourseSubscribe.formParams()});
};

$(function () {
    var defaultColunms = ActivityCourseSubscribe.initColumn();
    var table = new BSTable(ActivityCourseSubscribe.id, "/activityCourseSubscribe/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ActivityCourseSubscribe.formParams());
    
    ActivityCourseSubscribe.table = table.init();
});
