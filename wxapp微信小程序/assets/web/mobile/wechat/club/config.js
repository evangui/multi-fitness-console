require.config({
    paths : {
        BasisLibrary : '/assets/web/mobile/basis/script/BasisLibrary',
        index : '/assets/web/mobile/wechat/club/index.js?263'
    },
    shim : {
        index : {
            deps : ["BasisLibrary"],
            exports : '/assets/web/mobile/wechat/club/index'
        }
    }
});

//加载页面方法
require(['index'],function(index){
    index.startUp({});
});


