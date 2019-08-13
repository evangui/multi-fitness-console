/**
 * 储值卡消费记录管理初始化
 */
var CardStoredvaluecardSpend = {
    id: "CardStoredvaluecardSpendTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardStoredvaluecardSpend.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '卡ID', field: 'cardId', visible: true, align: 'center', valign: 'middle'},
            {title: '卡号码', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '私教卡名', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '消费金额', field: 'spendMoney', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle'},
            {title: '前台ID', field: 'rcepteionId', visible: true, align: 'center', valign: 'middle'},
            {title: '前台名称', field: 'rcepteionName', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'membershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户ID', field: 'opeUserId', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户名', field: 'opeUsername', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '财务备注', field: 'bankremark', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 0正常 1 已删除2 退款', field: 'cardStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '消次端口 0默认 1收银台 2前台代消 3APP收银 4APP商城 5转卡扣费 6课程预约 7它店消费 8门禁扣费 9场地预订 11活动报名', field: 'port', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CardStoredvaluecardSpend.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardStoredvaluecardSpend.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加储值卡消费记录
 */
CardStoredvaluecardSpend.openAddCardStoredvaluecardSpend = function () {
    var index = layer.open({
        type: 2,
        title: '添加储值卡消费记录',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardStoredvaluecardSpend/cardStoredvaluecardSpend_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看储值卡消费记录详情
 */
CardStoredvaluecardSpend.openCardStoredvaluecardSpendDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '储值卡消费记录详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardStoredvaluecardSpend/cardStoredvaluecardSpend_update/' + CardStoredvaluecardSpend.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除储值卡消费记录
 */
CardStoredvaluecardSpend.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecardSpend/delete", function (data) {
            Feng.success("删除成功!");
            CardStoredvaluecardSpend.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardStoredvaluecardSpendId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
CardStoredvaluecardSpend.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询储值卡消费记录列表
 */
CardStoredvaluecardSpend.search = function () {
    
    CardStoredvaluecardSpend.table.refresh({query: CardStoredvaluecardSpend.formParams()});
};

$(function () {
    var defaultColunms = CardStoredvaluecardSpend.initColumn();
    var table = new BSTable(CardStoredvaluecardSpend.id, "/cardStoredvaluecardSpend/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CardStoredvaluecardSpend.formParams());
    
    CardStoredvaluecardSpend.table = table.init();
});
