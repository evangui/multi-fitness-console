# multi-fitness-console
java多商户版健身房saas管理系统

含:
- 用户(会员&工作人员)端小程序
- 总后台管理
- 多商户前后端分离的商户端前端 （因涉及隐私数据，商户前端代码暂不放在本代码库，如需可联系我单独发送）
- 多商户前后端分离的后端接口api
- 会员用户与健身房工作人员使用的小程序对接接口api

# 技术栈
- SpringBoot2
- beetl模板引擎
- ehcache
- mybatis plus
- swagger api

# 项目目录
- wxapp微信小程序:   用户(会员&工作人员)端小程序项目代码
- guns-admin:   平台方管理后台
- guns-rest:        多商户与小程序用户(会员&工作人员)端api
    src\main\java\com\stylefeng\guns\modular\mch  商户端管理系统api
    src\main\java\com\stylefeng\guns\modular\usr  小程序用户(会员&工作人员)端api


# 系统功能
1.用户管理 2.角色管理 3.部门管理 4.菜单管理 5.字典管理 6.业务日志 7.登录日志 8.监控管理 9.通知管理 

## 基础模块 	
    会员管理、发卡、电子签课、权限管理
    前台签到、前台查询

## 手机APP	
    约课管理、排课管理、手机端各角色权限

## 会员管理 	
    会员充值、各种卡的发放、会员到期、会员维护、训练情况记录、会员反馈管理	√

## 私教模块	
    私教签课、私教排课、APP排课、APP约课等	√
    
## 微信/APP	
    微信配置、功能开关、预约记录、课程管理


# 商户端详细功能点
详细功能描述，请见 
[doc/《杏运树详细功能点说明-健身管理系统.xlsx》](https://raw.githubusercontent.com/evangui/multi-fitness-console/master/doc/杏运树详细功能点说明-健身管理系统.xlsx "or扩展了Markdown的")

| 平台模块   | 功能名称                    | 功能描述 |
| -------------- | ------------------------------- | -------- |
| 控制台      | 欢迎页面                    |          |
|                | 关于我们                    |          |
|                | 快捷操作                    |          |
| 基本设置   | 俱乐部信息设置           |          |
|                | 管理员权限设置           |          |
|                | 工作人员设置              |          |
| 会员管理   | 会员导入                    |          |
|                | 添加会员                    |          |
|                | 会员充值                    |          |
|                | 前台签到                    |          |
|                | 前台借还手环              |          |
|                | 前台课程播报              |          |
|                | 前台大屏幕                 |          |
|                | 会员维护                    |          |
|                | 潜在客户                    |          |
|                | 会员申请审核              |          |
|                | 私教消次                    |          |
| 小程序功能配置 | 小程序用户                 |          |
|                | 功能开关                    |          |
|                | 健身圈                       |          |
|                | 新闻推送                    |          |
|                | 预约记录                    |          |
|                | 课程管理                    |          |
| 会籍         | 小程序端维护-会员与潜在会员维护 |          |
|                | 小程序端维护-待办事项记录 |          |
|                | 会籍及维护信息显示     |          |
| 教练         | 小程序端维护              |          |
|                | 小程序端-排课管理       |          |
|                | 小程序端-私教确认       |          |
|                | 小程序端-训练情况跟踪 |          |
|                | 小程序端-会员反馈       |          |
| 营销活动   | 营销活动添加              |          |
|                | 营销活动管理              |          |
| 财务报表   | 财务报表管理              |          |

- 管理后台
![管理后台](https://raw.githubusercontent.com/evangui/multi-fitness-console/master/doc/%E7%AE%A1%E7%90%86%E5%90%8E%E5%8F%B0.png "管理后台")

- 商家后台
![商家后台](https://raw.githubusercontent.com/evangui/multi-fitness-console/master/doc/%E5%95%86%E5%AE%B6%E5%90%8E%E5%8F%B0.png "商家后台")
![商家后台](https://raw.githubusercontent.com/evangui/multi-fitness-console/master/doc/%E5%95%86%E5%AE%B6%E5%90%8E%E5%8F%B02.png "商家后台")
