package heqiao2010.com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 登录成功之后的操作
 * @author Administrator
 *
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	/**
	 * Logger
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info(authentication.getName() + "登录成功，重定向至loginSuccess.jsp");
		response.sendRedirect(request.getContextPath() + "/loginSuccess.jsp");
	}

}
