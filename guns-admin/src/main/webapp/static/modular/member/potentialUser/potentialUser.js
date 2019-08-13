/**
 * 潜在客户管理初始化
 */
var PotentialUser = {
    id: "PotentialUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PotentialUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '会员id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '用户id', field: 'userId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '会员头像', field: 'realavatar', visible: true, align: 'center', valign: 'middle'},
            {title: '会员性别（0：女，1：男）', field: 'gender', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'membershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '是否是导入', field: 'isImport', visible: true, align: 'center', valign: 'middle'},
            {title: '最近跟进记录', field: 'lastMaintainRecord', visible: true, align: 'center', valign: 'middle'},
            {title: '上次维护时间', field: 'lastMaintainTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '客户来源id', field: 'sourceId', visible: true, align: 'center', valign: 'middle'},
            {title: '客户来源名', field: 'sourceName', visible: true, align: 'center', valign: 'middle'},
            {title: '维护跟进的关注度：0未设置 1普通 2高关注 3不维护', field: 'labelLevels', visible: true, align: 'center', valign: 'middle'},
            {title: '是否已转化为vip', field: 'isVipUser', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PotentialUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PotentialUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加潜在客户
 */
PotentialUser.openAddPotentialUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加潜在客户',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/potentialUser/potentialUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看潜在客户详情
 */
PotentialUser.openPotentialUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '潜在客户详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/potentialUser/potentialUser_update/' + PotentialUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除潜在客户
 */
PotentialUser.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/potentialUser/delete", function (data) {
				Feng.success("删除成功!");
				PotentialUser.table.refresh();
			}, function (data) {
				Feng.error("删除失败!" + data.responseJSON.message + "!");
			});
			ajax.set("potentialUserId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PotentialUser.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询潜在客户列表
 */
PotentialUser.search = function () {
    
    PotentialUser.table.refresh({query: PotentialUser.formParams()});
};

$(function () {
    var defaultColunms = PotentialUser.initColumn();
    var table = new BSTable(PotentialUser.id, "/potentialUser/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PotentialUser.formParams());
    
    PotentialUser.table = table.init();
});
