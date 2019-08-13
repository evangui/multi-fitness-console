/**
 * 私教课程设置管理初始化
 */
var SettingClubPtrain = {
    id: "SettingClubPtrainTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettingClubPtrain.initColumn = function () {
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
SettingClubPtrain.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettingClubPtrain.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加私教课程设置
 */
SettingClubPtrain.openAddSettingClubPtrain = function () {
    var index = layer.open({
        type: 2,
        title: '添加私教课程设置',
        area: ['940px', '540px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settingClubPtrain/settingClubPtrain_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看私教课程设置详情
 */
SettingClubPtrain.openSettingClubPtrainDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '私教课程设置详情',
            area: ['940px', '540px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settingClubPtrain/settingClubPtrain_update/' + SettingClubPtrain.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除私教课程设置
 */
SettingClubPtrain.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/settingClubPtrain/delete", function (data) {
            Feng.success("删除成功!");
            SettingClubPtrain.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settingClubPtrainId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SettingClubPtrain.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询私教课程设置列表
 */
SettingClubPtrain.search = function () {
    
    SettingClubPtrain.table.refresh({query: SettingClubPtrain.formParams()});
};

$(function () {
    var defaultColunms = SettingClubPtrain.initColumn();
    var table = new BSTable(SettingClubPtrain.id, "/settingClubPtrain/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SettingClubPtrain.formParams());
    
    SettingClubPtrain.table = table.init();
});
