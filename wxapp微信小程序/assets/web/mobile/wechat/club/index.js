define([], function() {
    return {
        startUp: function(data) {
            //var data = data;

            //微信首页
            function Club(data){
                BasisLibrary.call(this,data);   //对象冒充继承
            }
            //通过寄生组合继承 来实现继承
            create(BasisLibrary,Club);

            Club.prototype.page = function(data){
                var slef = this;
                var pageData = data;

                // $('.lb-ttp').html('<div class="banner-webkit">\
                //     <div class="flicker-example" data-block-text="false">\
                //         <ul>\
                //             <li v-for="(value,index) in listImg" class="li-carousel-url" data-value="'+value.url+'" data-background="'+value.url+'"></li>\
                //         </ul>\
                //     <div>\
                // </div>');
                //Vue.component('banner',banner);

                var page = new Vue({
                    el : '.club',
                    data : {
                        headerBg: {  //头部背景
                            backgroundImage:'url('+pageData.pageInfo.background+')'
                        },
                        clubLogo: pageData.pageInfo.logo,   //俱乐部logo
                        clubName: pageData.pageInfo.name,   //俱乐部名称
                        clubDescribe:pageData.pageInfo.describe, //俱乐部简介
                        hykUrl: pageData.pageInfo.uri,  //会员卡链接
                        listImg: pageData.pageInfo.clubThemeList,
                        listHtml: '',
                        listData: {},
                        style: pageData.style,
                        phone: pageData.pageInfo.phone,
                        type:pageData.pageInfo.type,
                        hrefGps: 'https://api.map.baidu.com/marker?location='+pageData.pageInfo.gps[1]+','+pageData.pageInfo.gps[0]+'&title='+pageData.pageInfo.name+'&content='+pageData.pageInfo.clubAddress+'&output=html',
                        googleAddress: pageData.pageInfo.googleAddress,
                        clubAddress: pageData.pageInfo.clubAddress,
                        wechatqrcode: pageData.pageInfo.wechatSubscription.wechatqrcode,
                        parentHref: pageData.pageInfo.parentHref,
                        wechatName: pageData.pageInfo.wechatSubscription.wechatName
                    },
                    created: function(){
                        var html = '';
                        for(var i = 0; this.$data.listImg[i]; i++){
                            var data_s = this.$data.listImg[i];
                            html += '<li class="li-carousel-url" data-value="'+data_s.url+'" data-background="'+data_s.image+'"></li>'
                        };
                        this.$data.listHtml = html;

                        var arr = [];
                        for(var i in pageData.pageInfo.flags){
                            var datas = pageData.pageInfo.flags[i];
                            if(i != 'qr1'){
                                for(var i = 0; datas[i]; i++){
                                    arr.push(datas[i]);
                                }
                            }
                        }
                        this.$data.listData = arr;
                        if (this.clubName.length > 19) {
                            var num = this.clubName.length - 19;
                            var siex = parseFloat($('.club-name').eq(0).css('fontSize').replace("px",""));
                            $('.club-name').css('fontSize', siex - (num - 2) + 'px')
                        }
                    },
                    methods : {
                        sysBut: function(event){ //扫一扫
                            var el = $(event.currentTarget);
                            // wx.ready(function(){
                            //
                            // })
                            wx.scanQRCode({
                                needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                success: function (res) {
                                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                    window.location.href = window.location.href
                                }
                            });
                        },
                        pupWent: function(){
                            $(".pup-eqr-webkit").css('display','block');
                        },
                        pupCircle: function() {
                            $('.pup-circle-webkit').show()
                        },
                        pupClod: function(event) {
                            var el = $(event.currentTarget);
                            el.hide()
                        },
                        pupClodTwo: function(event) {
                            $('.pup-circle-webkit').hide()
                        }
                    },
                    watch : {
                        // title : function(val,oldVal){
                        //     console.log(val,oldVal);
                        // }
                    }
                });

                page.$nextTick(function(){
                    $('.flicker-example').flicker({
                        auto_flick_delay : '3'
                    });
                    $('.li-carousel-url').each(function(){
                        $(this).off("click").on("click",function(){
                            if(!!$(this).data('value')){
                                window.location.href = $(this).data('value') + "?"+Math.random();
                            }
                        });
                    });
                    //二维码
                    // $('.userQrcode-cnvent').qrcode({width: 200,height: 200,text: fitoneData.clubData.data.cardNumber, toImg:'true'});
                    $(".pup-eqr-webkit").off("click").on("click",function(){
                        $(this).css("display","none");
                    });
                    $(".pup-eqr").click(function(event){
                        event.stopPropagation();
                    });
                });

            };

            //页面初始化方法
            Club.prototype.init = function (data) {
                var self = this;
                var data = data;
                self.page(data);
            };

            var club = new Club(data);

            $(window).ready(function(){
                club.init(configData);
            });
        }
    }
});



