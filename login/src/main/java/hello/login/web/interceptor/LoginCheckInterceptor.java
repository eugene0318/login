package hello.login.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
			response.sendRedirect("/login?redirectURL=" + requestURI);
			return false;
		}
		return true;
	}
}
