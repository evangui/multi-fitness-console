/**
 * 会员卡设置管理初始化
 */
var SettingCard = {
    id: "SettingCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettingCard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},,
            {title: '最长请假天数', field: 'leaveDays', visible: true, align: 'center', valign: 'middle'},
            {title: '最短请假天数', field: 'lessLeaveDays', visible: true, align: 'center', valign: 'middle'},
            {title: '最晚开卡期限', field: 'deadline', visible: true, align: 'center', valign: 'middle'},
            {title: '会员自助查询开关', field: 'record', visible: true, align: 'center', valign: 'middle'},
            {title: '剩余额度屏蔽', field: 'leftAmountHide', visible: true, align: 'center', valign: 'middle'},
            {title: '定金卡消费开关', field: 'frontConsumeSwitch', visible: true, align: 'center', valign: 'middle'},
            {title: '次卡消次密码', field: 'onceCardUsedPass', visible: true, align: 'center', valign: 'middle'},
            {title: '是否限制卡的开始使用时间', field: 'cardStartUseTimeSwitch', visible: true, align: 'center', valign: 'middle'},
            {title: '是否开启会员录入密码', field: 'vipPasswd', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SettingCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettingCard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员卡设置
 */
SettingCard.openAddSettingCard = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员卡设置',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settingCard/settingCard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员卡设置详情
 */
SettingCard.openSettingCardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员卡设置详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settingCard/settingCard_update/' + SettingCard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会员卡设置
 */
SettingCard.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/settingCard/delete", function (data) {
            Feng.success("删除成功!");
            SettingCard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settingCardId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SettingCard.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询会员卡设置列表
 */
SettingCard.search = function () {
    
    SettingCard.table.refresh({query: SettingCard.formParams()});
};

$(function () {
    var defaultColunms = SettingCard.initColumn();
    var table = new BSTable(SettingCard.id, "/settingCard/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SettingCard.formParams());
    
    SettingCard.table = table.init();
});
