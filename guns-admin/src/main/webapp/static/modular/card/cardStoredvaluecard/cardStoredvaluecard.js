/**
 * 会员储值卡管理初始化
 */
var CardStoredvaluecard = {
    id: "CardStoredvaluecardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardStoredvaluecard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: false, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '卡名', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '卡种类型ID', field: 'cardTypeId', visible: true, align: 'center', valign: 'middle'},
            {title: '卡种类型名', field: 'cardTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '实际添加时间', field: 'actualInsertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '开卡时间', field: 'openCardTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '开始使用时间', field: 'startUseTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '到期时间', field: 'expireTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '总面额', field: 'sumMoney', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '已用面额', field: 'leftMoney', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '实收金额', field: 'actualMoney', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '尾款金额', field: 'retainage', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '是否自动开卡', field: 'autoOpenCardStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
            {title: '财务是否确认', field: 'banksure', visible: true, align: 'center', valign: 'middle'},
            {title: '合同编号', field: 'contractNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '是否定金方式', field: 'frontMoneyStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '是否续卡', field: 'isRenewCard', visible: true, align: 'center', valign: 'middle'},
            {title: '是否通店卡', field: 'isUnitedCard', visible: true, align: 'center', valign: 'middle'},
            {title: '前台ID', field: 'rcepteionId', visible: true, align: 'center', valign: 'middle'},
            {title: '前台名称', field: 'rcepteionName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'membershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户ID', field: 'opeUserId', visible: true, align: 'center', valign: 'middle'},
            {title: '经办人用户名', field: 'opeUsername', visible: true, align: 'center', valign: 'middle'},
            {title: '支付方式', field: 'payMethod', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '客户来源id', field: 'sourceId', visible: true, align: 'center', valign: 'middle'},
            {title: '客户来源名', field: 'sourceName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '端口 1 充值新卡 2 续费', field: 'port', visible: true, align: 'center', valign: 'middle'},
            {title: '充值记录ID', field: 'rechargeId', visible: true, align: 'center', valign: 'middle'},
            {title: '是否启用', field: 'enabled', visible: true, align: 'center', valign: 'middle'},
            {title: '禁用备注', field: 'enableRemark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CardStoredvaluecard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardStoredvaluecard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员储值卡
 */
CardStoredvaluecard.openAddCardStoredvaluecard = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员储值卡',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardStoredvaluecard/cardStoredvaluecard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员储值卡详情
 */
CardStoredvaluecard.openCardStoredvaluecardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员储值卡详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardStoredvaluecard/cardStoredvaluecard_update/' + CardStoredvaluecard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会员储值卡
 */
CardStoredvaluecard.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/cardStoredvaluecard/delete", function (data) {
            Feng.success("删除成功!");
            CardStoredvaluecard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardStoredvaluecardId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
CardStoredvaluecard.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询会员储值卡列表
 */
CardStoredvaluecard.search = function () {
    
    CardStoredvaluecard.table.refresh({query: CardStoredvaluecard.formParams()});
};

$(function () {
    var defaultColunms = CardStoredvaluecard.initColumn();
    var table = new BSTable(CardStoredvaluecard.id, "/cardStoredvaluecard/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CardStoredvaluecard.formParams());
    
    CardStoredvaluecard.table = table.init();
});
