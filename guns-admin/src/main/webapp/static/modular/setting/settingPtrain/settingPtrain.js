/**
 * 私教课程设置管理初始化
 */
var SettingPtrain = {
    id: "SettingPtrainTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettingPtrain.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '私教每日开始时间秒', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '私教每日结束时间秒', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '私教卡确认机制', field: 'checkType', visible: true, align: 'center', valign: 'middle'},
            {title: '延迟评价分钟数', field: 'delay', visible: true, align: 'center', valign: 'middle'},
            {title: '私教类型:1一对一 0一对多', field: 'coachType', visible: true, align: 'center', valign: 'middle'},
            {title: '非会员是否能预约', field: 'noVipSub', visible: true, align: 'center', valign: 'middle'},
            {title: '显示已预约会员昵称', field: 'isShowName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SettingPtrain.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettingPtrain.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加私教课程设置
 */
SettingPtrain.openAddSettingPtrain = function () {
    var index = layer.open({
        type: 2,
        title: '添加私教课程设置',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settingPtrain/settingPtrain_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看私教课程设置详情
 */
SettingPtrain.openSettingPtrainDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '私教课程设置详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settingPtrain/settingPtrain_update/' + SettingPtrain.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除私教课程设置
 */
SettingPtrain.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/settingPtrain/delete", function (data) {
            Feng.success("删除成功!");
            SettingPtrain.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settingPtrainId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SettingPtrain.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询私教课程设置列表
 */
SettingPtrain.search = function () {
    
    SettingPtrain.table.refresh({query: SettingPtrain.formParams()});
};

$(function () {
    var defaultColunms = SettingPtrain.initColumn();
    var table = new BSTable(SettingPtrain.id, "/settingPtrain/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SettingPtrain.formParams());
    
    SettingPtrain.table = table.init();
});
