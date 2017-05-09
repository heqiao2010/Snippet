package heqiao2010.com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 登录失败之后的操作
 * @author Administrator
 *
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

	/**
	 * Logger
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		@SuppressWarnings("deprecation")
		String username = null == exception.getAuthentication() ? "" : exception.getAuthentication().getName();
		logger.info(username + "登录失败，重定向至loginFailed.jsp");
		response.sendRedirect(request.getContextPath() + "/loginFailed.jsp");
	}
}
