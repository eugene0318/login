package hello.login.web.session;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {

	private static final String SESSION_COOKIE_NAME = "mySessionId";
	private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

	public void createSession(Object value, HttpServletResponse response) {

		// 세션 id를 생성하고 값을 세션에 저장
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, value);

		// 쿠키 생성
		Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		response.addCookie(mySessionCookie);
	}

	public void expire(HttpServletRequest request) {
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			sessionStore.remove(sessionCookie.getValue());
		}
	}

	public Object getSession(HttpServletRequest request) {
//		Cookie[] cookies = request.getCookies();
//		if (cookies == null) {
//			return null;
//		}
//
//		for (Cookie cookie : cookies) {
//			if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
//				return sessionStore.get(cookie.getValue());
//			}
//		}
//
//		return null;

		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if (sessionCookie == null) {
			return null;

		}
		return sessionStore.get(sessionCookie.getValue());
	}

	public Cookie findCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(cookieName)).findAny().orElse(null);
	}
}
