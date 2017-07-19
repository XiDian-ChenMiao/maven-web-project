var seckill = {
    //封装秒杀相关AJAX的URL
    URL : {
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution : function (seckillId,md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    //验证手机号函数
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone))
            return true;
        return false;
    },
    //处理秒杀逻辑
    handleSeckill : function (seckillId,seckillBox) {
        seckillBox.hide().html('<button type="button" class="btn btn-success btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            //在回调函数中执行交互流程
            if(result && result['success']) {
                var exposer = result['data'];
                if(exposer['exposed']) {
                    //开始秒杀
                    var killUrl = seckill.URL.execution(seckillId,exposer['md5']);
                    console.log('killUrl' + killUrl);
                    $('#killBtn').on('click',function () {//绑定一次点击事件
                        $(this).addClass('disabled');//禁用按钮
                        $.post(killUrl,{},function (result) {
                            console.log(result);
                            if(result && result['success']) {

                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                seckillBox.html('<span class="label label-success">' + stateInfo + '</span>');//显示秒杀结果
                            } else {
                                if(result) {
                                    var killResult = result['data'];
                                    seckillBox.html('<span class="label label-success">' + killResult['stateInfo'] + '</span>');
                                }
                            }
                        });
                    });
                    seckillBox.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            } else {
                console.log('result' + result);
            }
        });
    },
    countdown : function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        //时间判断
        if(nowTime > endTime)
            seckillBox.html('秒杀结束！');
        else if (nowTime < startTime) {
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('秒杀倒计时：%D 天 %H 时 %M 分 %S 秒');//控制时间的格式
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                //时间完成后的回调事件，获取秒杀地址控制显示逻辑，执行秒杀
                seckill.handleSeckill(seckillId,seckillBox);
            });//计时事件绑定
        } else {
            seckill.handleSeckill(seckillId,seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail : {
        //详情页初始化
        init : function (params) {
            //用户手机验证和登录，并且做计时交互
            //规划交互流程
            //在Cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            if(!seckill.validatePhone(killPhone)) {
                //绑定手机号码
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show : true,
                    backdrop : 'static',//禁止位置关闭
                    keyboard : false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log(inputPhone);
                    if(seckill.validatePhone(inputPhone)) {
                        $.cookie('killPhone',inputPhone,{
                            expires : 7,
                            path : '/seckill'
                        });//电话写入Cookie
                        window.location.reload();//刷新页面
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
            //如果已经登录，启用计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function (result) {
                if(result && result['success']) {
                    var nowTime = result['data'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);//时间判断，计时交互
                } else {
                    console.log('result' + result);
                }
            });
        }
    }
}