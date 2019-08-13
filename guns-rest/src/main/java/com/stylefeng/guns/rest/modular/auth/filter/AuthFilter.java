package com.stylefeng.guns.rest.modular.auth.filter;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.util.RenderUtil;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:04
 */
public class AuthFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtProperties jwtProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// 如果路径是/autho开头的，则略过jwt校验
		if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
			chain.doFilter(request, response);
			return;
		}
		if (request.getServletPath().startsWith("/wxma")) {
			chain.doFilter(request, response);
			return;
		}
		if (request.getServletPath().startsWith("/general/")
				&& !request.getServletPath().startsWith("/general/tool/image/")
				) {
			chain.doFilter(request, response);
			return;
		}
		
		if (request.getServletPath().equals("/usr/club/clubCoach/coacheTimeTable"))
		 {
		 chain.doFilter(request, response);
		 return;
		 }
		
		// if (request.getServletPath().equals("/general/tool/image/upload")) {
		// chain.doFilter(request, response);
		// return;
		// }
		// if
		// (request.getServletPath().equals("/mch/member/vipUser/downVipUser2Excel"))
		// {
		// chain.doFilter(request, response);
		// return;
		// }

		final String requestHeader = request.getHeader(jwtProperties.getHeader());
		System.out.println(requestHeader);
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);

			// 验证token是否过期,包含了验证jwt是否正确
			try {
				boolean flag = jwtTokenUtil.isTokenExpired(authToken);
				if (flag) {
					RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(),
							BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
					return;
				}
			} catch (JwtException e) {
				// 有异常就是token解析失败
				RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(),
						BizExceptionEnum.TOKEN_ERROR.getMessage()));
				return;
			}
		} else {
			// header没有带Bearer字段
			RenderUtil.renderJson(response,
					new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
			return;
		}

		// 将用户信息放到request中
		String UserStr = jwtTokenUtil.getUserFromToken(authToken);

		request.setAttribute("member", JSON.parseObject(UserStr, ClubAdmin.class));
		request.setAttribute("mapMember", JSON.parseObject(UserStr, HashMap.class));

		chain.doFilter(request, response);
	}
}