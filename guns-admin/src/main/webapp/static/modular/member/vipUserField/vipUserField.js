/**
 * VIP用户自定义字段管理初始化
 */
var VipUserField = {
    id: "VipUserFieldTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
VipUserField.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'fieldId', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部id', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '字段提示名称', field: 'filedName', visible: true, align: 'center', valign: 'middle'},
            {title: '是否可选', field: 'filedOption', visible: true, align: 'center', valign: 'middle'},
            {title: '填写方式 1小输入框 2大输入框 3选择框', field: 'filedType', visible: true, align: 'center', valign: 'middle'},
            {title: '是否隐藏', field: 'hidden', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'sort', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
VipUserField.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        VipUserField.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加VIP用户自定义字段
 */
VipUserField.openAddVipUserField = function () {
    var index = layer.open({
        type: 2,
        title: '添加VIP用户自定义字段',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/vipUserField/vipUserField_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看VIP用户自定义字段详情
 */
VipUserField.openVipUserFieldDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'VIP用户自定义字段详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/vipUserField/vipUserField_update/' + VipUserField.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除VIP用户自定义字段
 */
VipUserField.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/vipUserField/delete", function (data) {
            Feng.success("删除成功!");
            VipUserField.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("vipUserFieldId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
VipUserField.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询VIP用户自定义字段列表
 */
VipUserField.search = function () {
    
    VipUserField.table.refresh({query: VipUserField.formParams()});
};

$(function () {
    var defaultColunms = VipUserField.initColumn();
    var table = new BSTable(VipUserField.id, "/vipUserField/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(VipUserField.formParams());
    
    VipUserField.table = table.init();
});
