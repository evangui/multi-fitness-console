/**
 * 会籍-主管-前台管理初始化
 */
var StaffSpecial = {
    id: "StaffSpecialTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
StaffSpecial.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '绑定用户id', field: 'userId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '绑定用户昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '头像', field: 'avatar', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 0正常 1异常', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '截屏提示开关', field: 'screenshotsAlarm', visible: true, align: 'center', valign: 'middle'},
            {title: '本月新增会员数量', field: 'newMemberCounts', visible: true, align: 'center', valign: 'middle'},
            {title: '会员数量', field: 'memberCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '权限json串', field: 'auth', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '手机端工作人员类型 0会籍 1前台 1主管', field: 'type', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
StaffSpecial.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        StaffSpecial.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会籍-主管-前台
 */
StaffSpecial.openAddStaffSpecial = function () {
    var index = layer.open({
        type: 2,
        title: '添加会籍-主管-前台',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/staffSpecial/staffSpecial_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会籍-主管-前台详情
 */
StaffSpecial.openStaffSpecialDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会籍-主管-前台详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/staffSpecial/staffSpecial_update/' + StaffSpecial.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会籍-主管-前台
 */
StaffSpecial.delete = function () {
    if (this.check()) {
    	var operation = function(){
    		var ajax = new $ax(Feng.ctxPath + "/staffSpecial/delete", function (data) {
                Feng.success("删除成功!");
                StaffSpecial.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("staffSpecialId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};
/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
StaffSpecial.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}
/**
 * 查询会籍-主管-前台列表
 */
StaffSpecial.search = function () {
	StaffSpecial.table.refresh({query: StaffSpecial.formParams()});
};

$(function () {
	var defaultColunms = StaffSpecial.initColumn();
    var table = new BSTable(StaffSpecial.id, "/staffSpecial/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(StaffSpecial.formParams());
    
    StaffSpecial.table = table.init();
});
