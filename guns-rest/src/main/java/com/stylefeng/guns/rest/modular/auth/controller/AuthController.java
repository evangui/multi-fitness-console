package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "mchValidator")
    private IReqValidator reqValidator;

    @RequestMapping(value = "${jwt.mchauth-path}")
    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {

        ClubAdmin clubAdmin = reqValidator.validate(authRequest);

        if (clubAdmin == null) {
//        	throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        	return ResponseEntity.ok(new ReturnTip(500, "账号密码错误"));
        }
        
        //将管理员信息存储
        
        final String randomKey = jwtTokenUtil.getRandomKey();
        
//        final String token = jwtTokenUtil.generateToken(authRequest.getUsername(), randomKey);
        
        String jwtSubject = JSON.toJSONString(clubAdmin);
        final String token = jwtTokenUtil.generateToken(jwtSubject, randomKey);
        
        //节点权限授权
//        NodeService::applyAuthNode();
        
        return ResponseEntity.ok(new ReturnTip(0, "成功", new AuthResponse(token, randomKey, clubAdmin)));
    }
}
