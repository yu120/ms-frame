package cn.ms.frame.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略
 * 
 * @author lry
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Ignore {

	/**
	 * 是否忽略初始化异常
	 * 
	 * @return
	 */
	boolean init() default true;

	/**
	 * 是否忽略启动异常
	 * 
	 * @return
	 */
	boolean start() default true;

	/**
	 * 是否忽略销毁异常
	 * 
	 * @return
	 */
	boolean destroy() default true;

}
