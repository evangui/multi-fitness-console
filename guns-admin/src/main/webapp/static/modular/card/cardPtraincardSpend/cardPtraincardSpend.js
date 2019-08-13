/**
 * 私教卡消课记录管理初始化
 */
var CardPtraincardSpend = {
    id: "CardPtraincardSpendTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardPtraincardSpend.initColumn = function () {
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
            {title: '前台ID', field: 'rcepteionId', visible: true, align: 'center', valign: 'middle'},
            {title: '前台名称', field: 'rcepteionName', visible: true, align: 'center', valign: 'middle'},
            {title: '前台备注', field: 'receptionRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'membershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户ID', field: 'opeUserId', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户名', field: 'opeUsername', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 0正常 1 已删除2 已退课', field: 'cardStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '消课端口 0默认 3转卡扣费 4三方消课 5两方消课 6前台代消 7课程预约 8指纹消课 9它店消费 22无记录', field: 'port', visible: true, align: 'center', valign: 'middle'},
            {title: '课程类型 0私教课 1团体课', field: 'isSyllabusSub', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CardPtraincardSpend.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardPtraincardSpend.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加私教卡消课记录
 */
CardPtraincardSpend.openAddCardPtraincardSpend = function () {
    var index = layer.open({
        type: 2,
        title: '添加私教卡消课记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardPtraincardSpend/cardPtraincardSpend_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看私教卡消课记录详情
 */
CardPtraincardSpend.openCardPtraincardSpendDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '私教卡消课记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardPtraincardSpend/cardPtraincardSpend_update/' + CardPtraincardSpend.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除私教卡消课记录
 */
CardPtraincardSpend.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/cardPtraincardSpend/delete", function (data) {
            Feng.success("删除成功!");
            CardPtraincardSpend.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardPtraincardSpendId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
CardPtraincardSpend.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询私教卡消课记录列表
 */
CardPtraincardSpend.search = function () {
    
    CardPtraincardSpend.table.refresh({query: CardPtraincardSpend.formParams()});
};

$(function () {
    var defaultColunms = CardPtraincardSpend.initColumn();
    var table = new BSTable(CardPtraincardSpend.id, "/cardPtraincardSpend/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CardPtraincardSpend.formParams());
    
    CardPtraincardSpend.table = table.init();
});
