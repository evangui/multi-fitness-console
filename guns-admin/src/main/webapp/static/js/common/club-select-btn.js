/**
 * 俱乐部下拉选择按钮控件
 */
 var ClubSelectBtn = {
	clubId: 0,
	btnSelector: '#clubSelBtn',

	init: function(clubId, btnSelector) {
		this.clubId = clubId;
		this.btnSelector = btnSelector;
		var _clubData = {};
		var ajax = new $ax(Feng.ctxPath + "/club/searchList", function (data) {
			_clubData = data;
			if (clubId) {
				for (var i=0; i<data.length; i++) {
					if (data[i].id == clubId) {
						$(btnSelector).val(data[i].name);
					}
				}
			}
			
		}, function (data) {
			Feng.error("出现错误 !" + data.responseJSON.message + "!");
		});
		ajax.setType('get').start();

		/**
		 * 测试(首次从 URL 获取数据，显示 header，不显示按钮，忽略大小写，可清除)
		 */
		$(btnSelector).bsSuggest({
			data: {             
				value: _clubData            
			},    
			//url: "static/data.json",
			effectiveFields: ["id", "name"],
		   /*  searchFields: [ "shortAccount"], */
			effectiveFieldsAlias:{name: "名称"},
			ignorecase: true,
			showHeader: true,
			showBtn: true,     //不显示下拉按钮
			delayUntilKeyup: true, //获取数据的方式为 firstByUrl 时，延迟到有输入/获取到焦点时才请求数据
			idField: "id",
			keyField: "name",
			clearable: true,
			getDataMethod: 'firstByUrl'
		}).on('onDataRequestSuccess', function (e, result) {
			console.log('onDataRequestSuccess: ', result);
		}).on('onSetSelectValue', function (e, keyword, data) {
			console.log('onSetSelectValue: ', keyword, data);
		}).on('onUnsetSelectValue', function () {
			console.log("onUnsetSelectValue");
		});
	},
 
	
 };	

