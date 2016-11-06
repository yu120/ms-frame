package cn.ms.frame.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件注解
 * 
 * @author lry
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Component {

	/**
	 * 名称
	 * 
	 * @return
	 */
	String name() default "";
	
	/**
	 * ID
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * 权值
	 * 
	 * @return
	 */
	int order() default 0;
	
}
