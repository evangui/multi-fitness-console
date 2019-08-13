/**
 * 俱乐部活动管理初始化
 */
var Activity = {
    id: "ActivityTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Activity.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '活动类型：1 课程预约 2打卡有礼 3大转盘 4 自定义 5 健身体验券', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '活动名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '封面', field: 'cover', visible: true, align: 'center', valign: 'middle'},
            {title: '后台活动编辑密码', field: 'editPwd', visible: true, align: 'center', valign: 'middle'},
            {title: '上课地点', field: 'courseAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '上课时间', field: 'courseTime', visible: true, align: 'center', valign: 'middle'},
            {title: '人数', field: 'number', visible: true, align: 'center', valign: 'middle'},
            {title: '签到密码', field: 'signCode', visible: true, align: 'center', valign: 'middle'},
            {title: '课程介绍', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '授课人信息，多个name avatar goodAt desc', field: 'teachers', visible: true, align: 'center', valign: 'middle'},
            {title: '最多购买次数', field: 'buyTimes', visible: true, align: 'center', valign: 'middle'},
            {title: '总费用', field: 'totalFee', visible: true, align: 'center', valign: 'middle'},
            {title: '活动地点', field: 'fieldAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '联系电话', field: 'fieldPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '报名开始时间', field: 'enrollStartTime', visible: true, align: 'center', valign: 'middle'},
            {title: '报名结束时间', field: 'enrollEndTime', visible: true, align: 'center', valign: 'middle'},
            {title: '活动开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '活动开始时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '自定义字段 category inputType optionInfo required sort', field: 'customFields', visible: true, align: 'center', valign: 'middle'},
            {title: '活动项目 title amount number', field: 'projects', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'sort', visible: true, align: 'center', valign: 'middle'},
            {title: '是否隐藏', field: 'hidden', visible: true, align: 'center', valign: 'middle'},
            {title: '活动状态', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Activity.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Activity.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部活动
 */
Activity.openAddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部活动',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activity/activity_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部活动详情
 */
Activity.openActivityDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部活动详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/activity/activity_update/' + Activity.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部活动
 */
Activity.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/activity/delete", function (data) {
            Feng.success("删除成功!");
            Activity.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("activityId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Activity.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询俱乐部活动列表
 */
Activity.search = function () {
    
    Activity.table.refresh({query: Activity.formParams()});
};

$(function () {
    var defaultColunms = Activity.initColumn();
    var table = new BSTable(Activity.id, "/activity/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Activity.formParams());
    
    Activity.table = table.init();
});
