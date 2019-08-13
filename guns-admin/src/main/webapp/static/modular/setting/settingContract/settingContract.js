/**
 * 合同设置管理初始化
 */
var SettingContract = {
    id: "SettingContractTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettingContract.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '合同自动签发', field: 'contractStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '会员信息是否显示', field: 'contractVip', visible: true, align: 'center', valign: 'middle'},
            {title: '充值信息是否显示', field: 'contractRecharge', visible: true, align: 'center', valign: 'middle'},
            {title: '合同信息是否显示', field: 'contractContract', visible: true, align: 'center', valign: 'middle'},
            {title: '签署信息是否显示', field: 'contractSign', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SettingContract.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettingContract.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加合同设置
 */
SettingContract.openAddSettingContract = function () {
    var index = layer.open({
        type: 2,
        title: '添加合同设置',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settingContract/settingContract_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看合同设置详情
 */
SettingContract.openSettingContractDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '合同设置详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settingContract/settingContract_update/' + SettingContract.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除合同设置
 */
SettingContract.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/settingContract/delete", function (data) {
            Feng.success("删除成功!");
            SettingContract.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settingContractId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SettingContract.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询合同设置列表
 */
SettingContract.search = function () {
    
    SettingContract.table.refresh({query: SettingContract.formParams()});
};

$(function () {
    var defaultColunms = SettingContract.initColumn();
    var table = new BSTable(SettingContract.id, "/settingContract/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SettingContract.formParams());
    
    SettingContract.table = table.init();
});
