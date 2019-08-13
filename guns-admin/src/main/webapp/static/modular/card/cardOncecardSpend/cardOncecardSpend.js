/**
 * 次卡消次记录管理初始化
 */
var CardOncecardSpend = {
    id: "CardOncecardSpendTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardOncecardSpend.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '私教卡ID', field: 'cardId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle'},
            {title: '课程id', field: 'syllabusId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '消耗次数', field: 'costs', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '单价', field: 'unitPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '私教卡名', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'membershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户ID', field: 'opeUserId', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户名', field: 'opeUsername', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 0正常 1 已删除2 已退次', field: 'cardStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '消次端口 0默认 1手机消次 2分享次卡 3前台代消 4门禁扣次 5转卡扣费 6课程预约 7它店消费 8无记录', field: 'port', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CardOncecardSpend.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardOncecardSpend.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加次卡消次记录
 */
CardOncecardSpend.openAddCardOncecardSpend = function () {
    var index = layer.open({
        type: 2,
        title: '添加次卡消次记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardOncecardSpend/cardOncecardSpend_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看次卡消次记录详情
 */
CardOncecardSpend.openCardOncecardSpendDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '次卡消次记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardOncecardSpend/cardOncecardSpend_update/' + CardOncecardSpend.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除次卡消次记录
 */
CardOncecardSpend.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/cardOncecardSpend/delete", function (data) {
            Feng.success("删除成功!");
            CardOncecardSpend.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardOncecardSpendId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
CardOncecardSpend.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询次卡消次记录列表
 */
CardOncecardSpend.search = function () {
    
    CardOncecardSpend.table.refresh({query: CardOncecardSpend.formParams()});
};

$(function () {
    var defaultColunms = CardOncecardSpend.initColumn();
    var table = new BSTable(CardOncecardSpend.id, "/cardOncecardSpend/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CardOncecardSpend.formParams());
    
    CardOncecardSpend.table = table.init();
});
