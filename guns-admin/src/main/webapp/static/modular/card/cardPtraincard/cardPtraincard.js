/**
 * 会员私教卡管理初始化
 */
var CardPtraincard = {
    id: "CardPtraincardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardPtraincard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'vipId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '卡名', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '卡种类型ID', field: 'cardTypeId', visible: true, align: 'center', valign: 'middle'},
            {title: '卡种类型名', field: 'cardTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '总次数', field: 'counts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '剩余次数', field: 'leftCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '实际添加时间', field: 'actualInsertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '开卡时间', field: 'openCardTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '开始使用时间', field: 'startUseTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '到期时间', field: 'expireTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '录入时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '单价', field: 'unitPrice', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '实收金额', field: 'actualMoney', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '尾款金额', field: 'retainage', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '是否自动开卡', field: 'autoOpenCardStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
            {title: '财务是否确认', field: 'banksure', visible: true, align: 'center', valign: 'middle'},
            {title: '合同编号', field: 'contractNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '是否定金方式', field: 'frontMoneyStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '是否续卡', field: 'isRenewCard', visible: true, align: 'center', valign: 'middle'},
            {title: '是否通店卡', field: 'isUnitedCard', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
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
            {title: '是否启用', field: 'enabled', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CardPtraincard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardPtraincard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员私教卡
 */
CardPtraincard.openAddCardPtraincard = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员私教卡',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardPtraincard/cardPtraincard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员私教卡详情
 */
CardPtraincard.openCardPtraincardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员私教卡详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardPtraincard/cardPtraincard_update/' + CardPtraincard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会员私教卡
 */
CardPtraincard.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/cardPtraincard/delete", function (data) {
            Feng.success("删除成功!");
            CardPtraincard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardPtraincardId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
CardPtraincard.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询会员私教卡列表
 */
CardPtraincard.search = function () {
    
    CardPtraincard.table.refresh({query: CardPtraincard.formParams()});
};

$(function () {
    var defaultColunms = CardPtraincard.initColumn();
    var table = new BSTable(CardPtraincard.id, "/cardPtraincard/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CardPtraincard.formParams());
    
    CardPtraincard.table = table.init();
});
