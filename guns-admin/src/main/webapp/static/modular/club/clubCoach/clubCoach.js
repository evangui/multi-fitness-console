/**
 * 教练管理初始化
 */
var ClubCoach = {
    id: "ClubCoachTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ClubCoach.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '绑定用户id', field: 'userId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '绑定用户昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '头像', field: 'avatar', visible: true, align: 'center', valign: 'middle'},
            {title: '介绍', field: 'desc', visible: true, align: 'center', valign: 'middle'},
            {title: '擅长', field: 'goodAt', visible: true, align: 'center', valign: 'middle'},
            {title: 'APP排序', field: 'order', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 0正常 1异常', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '本月私教次数', field: 'monthPtrainCount', visible: true, align: 'center', valign: 'middle'},
            {title: '本月总体评分', field: 'monthAvgScore', visible: true, align: 'center', valign: 'middle'},
            {title: '学员个数', field: 'vipCount', visible: true, align: 'center', valign: 'middle'},
            {title: '权限json串', field: 'auth', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
    ];
};

/**
 * 检查是否选中
 */
ClubCoach.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ClubCoach.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教练
 */
ClubCoach.openAddClubCoach = function () {
    var index = layer.open({
        type: 2,
        title: '添加教练',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/clubCoach/clubCoach_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看教练详情
 */
ClubCoach.openClubCoachDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教练详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/clubCoach/clubCoach_update/' + ClubCoach.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除教练
 */
ClubCoach.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/clubCoach/delete", function (data) {
            Feng.success("删除成功!");
            ClubCoach.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("clubCoachId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ClubCoach.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询教练列表
 */
ClubCoach.search = function () {
    
    ClubCoach.table.refresh({query: ClubCoach.formParams()});
};

$(function () {
    var defaultColunms = ClubCoach.initColumn();
    var table = new BSTable(ClubCoach.id, "/clubCoach/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubCoach.formParams());
    
    ClubCoach.table = table.init();
});
