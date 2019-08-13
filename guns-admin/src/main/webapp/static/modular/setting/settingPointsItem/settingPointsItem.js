/**
 * 积分项目设置管理初始化
 */
var SettingPointsItem = {
    id: "SettingPointsItemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettingPointsItem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '积分项目英文名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '积分项目中文名', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '每日可赠送次数', field: 'dayTimes', visible: true, align: 'center', valign: 'middle'},
            {title: ' 赠送方式,0赠送固定数 1按消费额赠送', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '赠送数量', field: 'num', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SettingPointsItem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettingPointsItem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加积分项目设置
 */
SettingPointsItem.openAddSettingPointsItem = function () {
    var index = layer.open({
        type: 2,
        title: '添加积分项目设置',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settingPointsItem/settingPointsItem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看积分项目设置详情
 */
SettingPointsItem.openSettingPointsItemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '积分项目设置详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settingPointsItem/settingPointsItem_update/' + SettingPointsItem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除积分项目设置
 */
SettingPointsItem.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/settingPointsItem/delete", function (data) {
            Feng.success("删除成功!");
            SettingPointsItem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settingPointsItemId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SettingPointsItem.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询积分项目设置列表
 */
SettingPointsItem.search = function () {
    
    SettingPointsItem.table.refresh({query: SettingPointsItem.formParams()});
};

$(function () {
    var defaultColunms = SettingPointsItem.initColumn();
    var table = new BSTable(SettingPointsItem.id, "/settingPointsItem/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SettingPointsItem.formParams());
    
    SettingPointsItem.table = table.init();
});
