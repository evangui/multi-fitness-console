/**
 * VIP用户管理初始化
 */
var VipUser = {
    id: "VipUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
VipUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '会员id', field: 'id', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '用户id', field: 'userId', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '俱乐部', field: 'clubName', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'realname', visible: true, align: 'center', valign: 'middle'},
            {title: '昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
            {title: '会员头像', field: 'realavatar', visible: true, align: 'center', valign: 'middle'},
            {title: '支付密码', field: 'payPasswd', visible: false, align: 'center', valign: 'middle'},
            {title: '会员性别（0：女，1：男）', field: 'gender', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '生日', field: 'birthday', visible: false, align: 'center', valign: 'middle'},
            {title: '身份证号', field: 'idCardNumber', visible: false, align: 'center', valign: 'middle'},
            {title: '会员卡ID', field: 'cardId', visible: true, align: 'center', valign: 'middle'},
            {title: '会员卡号', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '会员卡类型文字', field: 'cardType', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练ID', field: 'coachId', visible: true, align: 'center', valign: 'middle'},
            {title: '所属教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '介绍会员数', field: 'introducePersonCount', visible: true, align: 'center', valign: 'middle'},
            {title: '介绍人ID', field: 'introducePersonId', visible: true, align: 'center', valign: 'middle'},
            {title: '介绍人名称', field: 'introducePersonName', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍ID', field: 'membershipId', visible: true, align: 'center', valign: 'middle'},
            {title: '会籍名称', field: 'membershipName', visible: true, align: 'center', valign: 'middle'},
            {title: '国家号码编号', field: 'country', visible: false, align: 'center', valign: 'middle'},
            {title: '家庭住址', field: 'address', visible: false, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '积分', field: 'points', visible: true, align: 'center', valign: 'middle'},
            {title: '已用积分', field: 'usedPoints', visible: true, align: 'center', valign: 'middle'},
            {title: '是否是导入', field: 'isImport', visible: true, align: 'center', valign: 'middle'},
            {title: '是否通用vip', field: 'isUniverseVip', visible: true, align: 'center', valign: 'middle'},
            {title: '最近跟进记录', field: 'lastMaintainRecord', visible: true, align: 'center', valign: 'middle'},
            {title: '上次维护时间', field: 'lastMaintainTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '上次签到时间', field: 'lastSignTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '入会时间', field: 'admissionTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '入会备注', field: 'admissionRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'insertTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '客户来源', field: 'sourceId', visible: true, align: 'center', valign: 'middle'},
            {title: '私教卡已用次数', field: 'personalTrainerCardUsedCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '私教卡总次数', field: 'personalTrainerCardCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '私教卡数量', field: 'personalTrainerCardNum', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '次卡已用次数', field: 'onceCardUsedCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '次卡总次数', field: 'onceCardCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '次卡数量', field: 'onceCardNum', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '储值卡已用次数', field: 'storedValueCardUsedCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '储值卡总次数', field: 'storedValueCardCounts', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '储值卡数量', field: 'storedValueCardNum', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '时间卡过期时间', field: 'expireTime', visible: true, align: 'center', valign: 'middle', sortable:true},
            {title: '时间卡状态（0无卡 1未开卡 2正常 3已过期 ）', field: 'expireStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '维护跟进的关注度：0未设置 1普通 2高关注 3不维护', field: 'labelLevels', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
VipUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        VipUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加VIP用户
 */
VipUser.openAddVipUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加VIP用户',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/vipUser/vipUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看VIP用户详情
 */
VipUser.openVipUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'VIP用户详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/vipUser/vipUser_update/' + VipUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除VIP用户
 */
VipUser.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Feng.ctxPath + "/vipUser/delete", function (data) {
            Feng.success("删除成功!");
            VipUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("vipUserId",this.seItem.id);
            ajax.start();
    	}
    	
    	Feng.confirm("是否删除： " + "" + "?", operation);
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
VipUser.formParams = function() {
	var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['clubId'] = $("#clubSelBtn").attr('data-id');

    return queryData;
}

/**
 * 查询VIP用户列表
 */
VipUser.search = function () {
    
    VipUser.table.refresh({query: VipUser.formParams()});
};

$(function () {
    var defaultColunms = VipUser.initColumn();
    var table = new BSTable(VipUser.id, "/vipUser/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(VipUser.formParams());
    
    VipUser.table = table.init();
});
