package com.heqiao2010.aopcacheutil.core.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.heqiao2010.aopcacheutil.core.Config;
import com.heqiao2010.aopcacheutil.core.annotation.Cache;
import com.heqiao2010.aopcacheutil.core.annotation.Evict;
import com.heqiao2010.aopcacheutil.core.annotation.Key;
import com.heqiao2010.aopcacheutil.core.annotation.Param;
import com.heqiao2010.aopcacheutil.core.annotation.Update;

/**
 * 从接入点解析参数配置
 * 
 * @author heqiao
 *
 */
@Component
public class ConfigParser implements IConfigParser {

	private Log log = LogFactory.getLog(getClass());

	private final static PropertyUtilsBean propertyUtil = new PropertyUtilsBean();

	/**
	 * 获取上下文
	 * 
	 * @param method
	 * @param args
	 * @return
	 */
	private Map<String, Object> getContext(Method method, Object[] args) {
		Map<String, Object> context = new HashMap<String, Object>();
		Param[] params = getParams(method);
		for (int i = 0; null != params && i < params.length; i++) {
			if (null != params[i]) {
				context.put(params[i].value(), args[i]);
			}
		}
		for (int i = 0; null != args && i < args.length; i++) {
			if (null != args[i]) {
				context.put(String.valueOf(i), args[i]);
			}
		}
		return context;
	}

	/**
	 * 获取param信息
	 * 
	 * @param method
	 * @return
	 */
	private Param[] getParams(Method method) {
		Annotation[][] annos = method.getParameterAnnotations();
		Param[] params = new Param[annos.length];
		for (int i = 0; null != annos && i < annos.length; i++) {
			for (int j = 0; null != annos[i] && j < annos[i].length; j++) {
				if (Param.class.isInstance(annos[i][j])) {
					params[i] = (Param) annos[i][j];
					break;
				}
			}
		}
		return params;
	}

	/**
	 * 构建缓存Key
	 * 
	 * @param name
	 * @param keys
	 * @param context
	 * @return
	 */
	private String buildRawKey(String name, Key[] keys, Map<String, Object> context) {
		try {
			StringBuilder rawKeyBuilder = new StringBuilder(name);
			for (Key k : keys) {
				Object keyPart = getValue(context, k.value());
				rawKeyBuilder.append(":" + keyPart);
			}
			return rawKeyBuilder.toString();
		} catch (Exception e) {
			log.warn("buildRawKey failed!", e);
			return "";
		}
	}

	private Object getValue(Map<String, Object> context, String opath) throws Exception {
		boolean isDynamic = opath.startsWith("#{") && opath.endsWith("}");
		if (isDynamic) {
			String rp = opath.substring(2, opath.length() - 1);
			return propertyUtil.getProperty(context, rp);
		}
		return opath;
	}

	/**
	 * 从JoinPoint获取Mehtod
	 * 
	 * @param joinPoint
	 * @return
	 */
	private Method getMethod(ProceedingJoinPoint joinPoint) {
		Method m = null;
		if (joinPoint.getTarget() instanceof Method) {
			m = (Method) joinPoint.getTarget();
		} else {
			if (joinPoint.getSignature() instanceof MethodSignature) {
				try {
					// 获取实现类上的信息
					String methodName=joinPoint.getSignature().getName();
			        Class<?> classTarget=joinPoint.getTarget().getClass();
			        Class<?>[] par=((MethodSignature) joinPoint.getSignature()).getParameterTypes();
			        m =classTarget.getMethod(methodName, par);
				} catch (Exception e) {
					log.warn("", e);
					MethodSignature signature = (MethodSignature) joinPoint.getSignature();
					m = signature.getMethod();
				}
			} else {
				log.warn("Target is not a method!");
			}
		}
		return m;
	}

	@Override
	public Config parseCacheConfig(ProceedingJoinPoint joinPoint, Cache cache) {
		Method m = getMethod(joinPoint);
		if (null == m) {
			log.warn("No method found from joinPoint: " + joinPoint);
			return null;
		}
		Map<String, Object> context = getContext(m, joinPoint.getArgs());
		String rawKey = buildRawKey(cache.name(), cache.keys(), context);
		if (!StringUtils.hasText(rawKey)) {
			return null;
		}
		Config conf = new Config();
		conf.setRawKey(rawKey);
		conf.setTtl(cache.ttl());
		conf.setUnit(cache.timeUnit());
		return conf;
	}

	@Override
	public Config parseUpdateConfig(ProceedingJoinPoint joinPoint, Update update) {
		Method m = getMethod(joinPoint);
		if (null == m) {
			log.warn("No method found from joinPoint: " + joinPoint);
			return null;
		}
		Map<String, Object> context = getContext(m, joinPoint.getArgs());
		String rawKey = buildRawKey(update.name(), update.keys(), context);
		if (!StringUtils.hasText(rawKey)) {
			return null;
		}
		Config conf = new Config();
		conf.setRawKey(rawKey);
		conf.setTtl(update.ttl());
		conf.setUnit(update.timeUnit());
		return conf;
	}

	@Override
	public Config parseEvictConfig(ProceedingJoinPoint joinPoint, Evict evict) {
		Method m = getMethod(joinPoint);
		if (null == m) {
			log.warn("No method found from joinPoint: " + joinPoint);
			return null;
		}
		Map<String, Object> context = getContext(m, joinPoint.getArgs());
		String rawKey = buildRawKey(evict.name(), evict.keys(), context);
		if (!StringUtils.hasText(rawKey)) {
			return null;
		}
		Config conf = new Config();
		conf.setRawKey(rawKey);
		return conf;
	}
}
