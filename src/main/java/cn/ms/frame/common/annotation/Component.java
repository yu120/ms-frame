package cn.ms.frame.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.ms.frame.plugin.IPlugin;

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
	 * 所属插件
	 * 
	 * @return
	 */
	Class<? extends IPlugin> plugin();
	
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

	/**
	 * 名称
	 * 
	 * @return
	 */
	String name() default "";
	
	
}
