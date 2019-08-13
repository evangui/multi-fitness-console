/**
 * 卡种管理初始化
 */
var CardCategory = {
    id: "CardCategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardCategory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '卡种名 ', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '到期时间', field: 'expireTime', visible: true, align: 'center', valign: 'middle'},
            {title: '到期天数', field: 'expireDay', visible: true, align: 'center', valign: 'middle'},
            {title: '充值天数 ', field: 'value', visible: true, align: 'center', valign: 'middle'},
            {title: '可请假天数 ', field: 'leaveMaxDay', visible: true, align: 'center', valign: 'middle'},
            {title: '是否可用', field: 'enabled', visible: true, align: 'center', valign: 'middle'},
            {title: '是否限制分享', field: 'shareRestrict', visible: true, align: 'center', valign: 'middle'},
            {title: '卡类型1 时间卡 2私教卡 3次卡4储值卡', field: 'cardType', visible: true, align: 'center', valign: 'middle', sortable:true},
    ];
};

/**
 * 检查是否选中
 */
CardCategory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CardCategory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加卡种
 */
CardCategory.openAddCardCategory = function () {
    var index = layer.open({
        type: 2,
        title: '添加卡种',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardCategory/cardCategory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看卡种详情
 */
CardCategory.openCardCategoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '卡种详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardCategory/cardCategory_update/' + CardCategory.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除卡种
 */
CardCategory.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/cardCategory/delete", function (data) {
            Feng.success("删除成功!");
            CardCategory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cardCategoryId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
CardCategory.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询卡种列表
 */
CardCategory.search = function () {
    
    CardCategory.table.refresh({query: CardCategory.formParams()});
};

$(function () {
    var defaultColunms = CardCategory.initColumn();
    var table = new BSTable(CardCategory.id, "/cardCategory/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CardCategory.formParams());
    
    CardCategory.table = table.init();
});
