/**
 * 俱乐部主体信息管理初始化
 */
var Club = {
    id: "ClubTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Club.initColumn = function () {
    return [
        	{field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle',sortable:true},
            {title: '俱乐部名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'clubSort', visible: true, align: 'center', valign: 'middle'},
            {title: '短信后缀', field: 'abbreviate', visible: false, align: 'center', valign: 'middle'},
            {title: '电话', field: 'clubPhone', visible: false, align: 'center', valign: 'middle'},
            {title: 'logo', field: 'logo', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.img },
            {title: '背景图', field: 'background', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.img },
            {title: '认证码', field: 'authCode', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部二维码', field: 'qrcodeImg', visible: false, align: 'center', valign: 'middle'},
            {title: '微官网', field: 'microWebsite', visible: false, align: 'center', valign: 'middle'},
            {title: '经度', field: 'lng', visible: false, align: 'center', valign: 'middle'},
            {title: '纬度', field: 'lat', visible: false, align: 'center', valign: 'middle'},
            {title: '省份', field: 'clubProvince', visible: false, align: 'center', valign: 'middle'},
            {title: '城市', field: 'clubCity', visible: false, align: 'center', valign: 'middle'},
            {title: '地区', field: 'clubArea', visible: false, align: 'center', valign: 'middle'},
            {title: '地址', field: 'clubAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '俱乐部类型', field: 'clubStyleName', visible: true, align: 'center', valign: 'middle'},
            {title: '所属', field: 'parentId', visible: true, align: 'center', valign: 'middle'},
            {title: '备注人', field: 'remarkBy', visible: false, align: 'center', valign: 'middle'},
            {title: '用户数', field: 'userCount', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '生效时间', field: 'startTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '过期时间', field: 'expireTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '过期保留时间（天）', field: 'notEnoughCancelTime', visible: false, align: 'center', valign: 'middle'},
            {title: '推广短链接', field: 'shorturl', visible: false, align: 'center', valign: 'middle'},
            {title: '剩余金额', field: 'cashAmount', visible: false, align: 'center', valign: 'middle', sortable:true},
            {title: '会员最近申请时间', field: 'applyTime', visible: false, align: 'center', valign: 'middle', sortable:true},
            {title: '申请数', field: 'applyNum', visible: false, align: 'center', valign: 'middle', sortable:true},
            {title: '是否开启会员间聊天', field: 'chat', visible: false, align: 'center', valign: 'middle'},
            {title: '是否开启积分系统', field: 'pointsStatus', visible: false, align: 'center', valign: 'middle'},
            {title: '试用', field: 'trialClub', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.trialClub },
            {title: '俱乐部管理', field: 'clubnav', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.clubNav },
            {title: '卡管理', field: 'cardNav', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.cardNav },
            {title: '课程管理', field: 'syllaubsNav', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.syllaubsNav },
            {title: '用户管理', field: 'memberNav', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.memberNav },
            {title: '活动管理', field: 'activityNav', visible: true, align: 'center', valign: 'middle', formatter: Club.formatter.activityNav },
    ];
};

/**
 * 格式化方法
 */
Club.formatter = {
	trialClub: function(value,row,index) {
		if (value) {
			return '<small class="label label-default">试用</small>';
		} else {
			return '<small class="label label-warning">正式</small>';
		}
	},
	img: function(value,row,index) {
		return '<img src="' + value + '" width="100"/>';
	},
	
	clubNav: function(value,row,index) {
		var _html; 
		var clubId = row.id;
		_html = ' <button onclick=\'Club.redirect("/clubAdmin", "管理员",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">管理员</button>';
		_html += ' <button onclick=\'Club.redirect("/staffSpecial", "会籍主管前台",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">会籍主管前台</button>';
		_html += ' <button onclick=\'Club.redirect("/clubCoach", "教练",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">教练</button>';
		_html += ' <button onclick=\'Club.redirect("/clubRing", "手环",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">手环</button>';
		_html += ' <button onclick=\'Club.redirect("/clubContract", "合同",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">合同</button>';
		return _html;
	},
	
	cardNav: function(value,row,index) {
		var _html; 
		var clubId = row.id;
		_html = '<button onclick=\'Club.redirect("/settingCard", "卡设置",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">卡设置</button>';
		_html = '<button onclick=\'Club.redirect("/cardCategory", "卡种",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">卡种</button>';
		_html += ' <button onclick=\'Club.redirect("/cardTimecard", "时间卡",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">时间卡</button>';
		_html += ' <button onclick=\'Club.redirect("/cardPtraincard", "私教卡",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">私教卡</button>';
		_html += ' <button onclick=\'Club.redirect("/cardOncecard", "次卡",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">次卡</button>';
		_html += ' <button onclick=\'Club.redirect("/cardStoredvaluecard", "储值卡",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">储值卡</button>';
		return _html;
	},
	syllaubsNav: function(value,row,index) {
		var _html; 
		var clubId = row.id;
		_html = ' <button onclick=\'Club.redirect("/syllabusGroup", "课种类",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">课种类</button>';
		_html += ' <button onclick=\'Club.redirect("/syllabusScheduleSetting", "排期设置",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">排期设置</button>';
		_html += ' <button onclick=\'Club.redirect("/syllabusItem", "排期课程",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">排期课程</button>';
		_html += ' <button onclick=\'Club.redirect("/syllabusSubscribeRecord", "约课记录",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">约课记录</button>';
		_html += ' <button onclick=\'Club.redirect("/ptrainRecord", "私教记录",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">私教记录</button>';
		return _html;
	},
	activityNav: function(value,row,index) {
		var _html; 
		var clubId = row.id;
		_html = ' <button onclick=\'Club.redirect("/activity", "活动",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">活动</button>';
		_html += ' <button onclick=\'Club.redirect("/activityCourseSubscribe", "报名记录",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">报名记录</button>';
		return _html;
	},
	memberNav: function(value,row,index) {
		var _html; 
		var clubId = row.id;
		_html = ' <button onclick=\'Club.redirect("/userCommon", "注册用户",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">注册用户</button>';
		_html += ' <button onclick=\'Club.redirect("/vipUser", "VIP",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">VIP</button>';
		_html += ' <button onclick=\'Club.redirect("/userPotential", "潜在客户",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">潜在客户</button>';
		_html += ' <button onclick=\'Club.redirect("/checkinRecord", "签到记录",' + row.id + ',"' + row.name + '")\' class="btn btn-default btn-outline btn-xs">签到记录</button>';
		return _html;
	},
};

/**
 * 检查是否选中
 */
Club.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Club.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加俱乐部主体信息
 */
Club.openAddClub = function () {
    var index = layer.open({
        type: 2,
        title: '添加俱乐部主体信息',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/club/club_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看俱乐部主体信息详情
 */
Club.openClubDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '俱乐部主体信息详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/club/club_update/' + Club.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除俱乐部主体信息
 */
Club.delete = function () {
    if (this.check()) {
        var operation = function(){
        	var ajax = new $ax(Feng.ctxPath + "/club/delete", function (data) {
                Feng.success("删除成功!");
                Club.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("clubId",Club.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除俱乐部： " + this.seItem.name + "?", operation);
    }
};

/**
 * 打开查看俱乐部主体信息详情
 */
Club.redirect = function(url, title, clubId, clubName) {
    if (clubId != undefined || this.check()) {
    	if (clubId === undefined) {
    		clubId = Club.seItem.id;
    		clubName = Club.seItem.name;
    	}
    	
        var index = layer.open({
            type: 2,
            title: clubName + '：' + title,
            area: ['1150px', '820px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + url + '?clubId=' + clubId
        });
        this.layerIndex = index;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Club.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();

    return queryData;
}

/**
 * 查询俱乐部主体信息列表
 */
Club.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Club.table.refresh({query: Club.formParams()});
};

$(function () {
    var defaultColunms = Club.initColumn();
    var table = new BSTable(Club.id, "/club/list", defaultColunms);
    table.setPaginationType("server");
	table.setQueryParams(Club.formParams());

    Club.table = table.init();
});
