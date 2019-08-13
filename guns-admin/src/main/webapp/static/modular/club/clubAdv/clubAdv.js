/**
 * 俱乐部广告管理初始化
 */
var ClubAdv = {
    id: "ClubAdvTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ClubAdv.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '广告自增标识编号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部ID', field: 'clubId', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位id', field: 'posId', visible: true, align: 'center', valign: 'middle'},
            {title: '广告位名称', field: 'posName', visible: true, align: 'center', valign: 'middle'},
            {title: '0：(跳转到链接)，1:(跳转到详情),2(跳转到列表),3(跳转到文章)', field: 'advUrlType', visible: true, align: 'center', valign: 'middle'},
            {title: '广告标题', field: 'advTitle', visible: true, align: 'center', valign: 'middle'},
            {title: '广告类别：0图片1文字', field: 'class', visible: true, align: 'center', valign: 'middle'},
            {title: '广告内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '广告开始时间', field: 'starttime', visible: true, align: 'center', valign: 'middle'},
            {title: '广告结束时间', field: 'endtime', visible: true, align: 'center', valign: 'middle'},
            {title: '广告排序', field: 'sort', visible: true, align: 'center', valign: 'middle'},
            {title: '广告点击数', field: 'clickNum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ClubAdv.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ClubAdv.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部广告
 */
ClubAdv.openAddClubAdv = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部广告',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/clubAdv/clubAdv_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部广告详情
 */
ClubAdv.openClubAdvDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部广告详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/clubAdv/clubAdv_update/' + ClubAdv.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部广告
 */
ClubAdv.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/clubAdv/delete", function (data) {
            Feng.success("删除成功!");
            ClubAdv.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("clubAdvId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ClubAdv.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');
    return queryData;
}

/**
 * 查询俱乐部广告列表
 */
ClubAdv.search = function () {
	ClubAdv.table.refresh({query: ClubAdv.formParams()});
};

$(function () {
    var defaultColunms = ClubAdv.initColumn();
    var table = new BSTable(ClubAdv.id, "/clubAdv/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ClubAdv.formParams());
    ClubAdv.table = table.init();
});
