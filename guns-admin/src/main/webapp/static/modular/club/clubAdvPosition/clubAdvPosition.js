/**
 * 俱乐部广告位管理初始化
 */
var ClubAdvPosition = {
    id: "ClubAdvPositionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ClubAdvPosition.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '广告位置id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部ID', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位置名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '广告类别：0图片1文字2幻灯3Flash', field: 'class', visible: true, align: 'center', valign: 'middle'},
            {title: '广告展示方式：0幻灯片1多广告展示2单广告展示', field: 'display', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位是否启用：0不启用1启用', field: 'enable', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位宽度', field: 'width', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位高度', field: 'height', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位点击数', field: 'clickNum', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位默认内容', field: 'defaultContent', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ClubAdvPosition.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ClubAdvPosition.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部广告位
 */
ClubAdvPosition.openAddClubAdvPosition = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部广告位',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/clubAdvPosition/clubAdvPosition_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部广告位详情
 */
ClubAdvPosition.openClubAdvPositionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部广告位详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/clubAdvPosition/clubAdvPosition_update/' + ClubAdvPosition.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部广告位
 */
ClubAdvPosition.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/clubAdvPosition/delete", function (data) {
            Feng.success("删除成功!");
            ClubAdvPosition.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("clubAdvPositionId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ClubAdvPosition.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');
    return queryData;
}

/**
 * 查询俱乐部广告位列表
 */
ClubAdvPosition.search = function () {
	ClubAdvPosition.table.refresh({query: ClubAdvPosition.formParams()});
};

$(function () {
    var defaultColunms = ClubAdvPosition.initColumn();
    var table = new BSTable(ClubAdvPosition.id, "/clubAdvPosition/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubAdvPosition.formParams());
    ClubAdvPosition.table = table.init();
});
