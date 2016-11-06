package cn.ms.frame.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import cn.ms.frame.common.annotation.Component;
import cn.ms.frame.common.annotation.Extension;
import cn.ms.frame.common.annotation.Ignore;
import cn.ms.frame.common.annotation.Plugin;
import cn.ms.frame.common.type.AnnotationType;
import cn.ms.frame.common.type.ProcesserType;
import cn.ms.frame.component.IComponent;
import cn.ms.frame.extension.IExtension;
import cn.ms.frame.plugin.IPlugin;

/**
 * 扫描工具类
 * 
 * @author lry
 */
public class ScanUtils {

	/**
	 * 扫描插件/组件/扩展点
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> scanPCE(final Class<T> clazz) {
		//$NON-NLS-类型$
		final AnnotationType annotationType;
		if (clazz.getName().equals(IPlugin.class.getName())) {
			annotationType = AnnotationType.Plugin;
		} else if (clazz.getName().equals(IComponent.class.getName())) {
			annotationType = AnnotationType.Component;
		} else if (clazz.getName().equals(IExtension.class.getName())) {
			annotationType = AnnotationType.Extension;
		} else {
			throw new RuntimeException("非法类型, clazz == " + clazz);
		}

		//$NON-NLS-扫描筛选$
		Map<String, T> serviceMap = new LinkedHashMap<String, T>();
		ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
		List<T> serviceList = new ArrayList<T>();
		for (T service : serviceLoader) {
			switch (annotationType) {
			case Plugin: {
				if (service.getClass().getAnnotation(Plugin.class) != null) {
					serviceList.add(service);
				}
				break;
			}
			case Component: {
				if (service.getClass().getAnnotation(Component.class) != null) {
					serviceList.add(service);
				}
				break;
			}
			case Extension: {
				if (service.getClass().getAnnotation(Extension.class) != null) {
					serviceList.add(service);
				}
				break;
			}
			}
		}

		//$NON-NLS-排序$
		Collections.sort(serviceList, new Comparator<T>() {
			public int compare(T o1, T o2) {
				switch (annotationType) {
				case Plugin:
					return o1.getClass().getAnnotation(Plugin.class).order()
							- o2.getClass().getAnnotation(Plugin.class).order();
				case Component:
					return o1.getClass().getAnnotation(Component.class).order()
							- o2.getClass().getAnnotation(Component.class)
									.order();
				case Extension:
					return o1.getClass().getAnnotation(Extension.class).order()
							- o2.getClass().getAnnotation(Extension.class)
									.order();
				default:
					throw new RuntimeException("非法类型, clazz == " + clazz);
				}
			}
		});

		//$NON-NLS-回收$
		for (T service : serviceList) {
			serviceMap.put(service.getClass().getName(), service);
		}

		return serviceMap;
	}
	
	
	/**
	 * 执行动作处理
	 * 
	 * @param pluginComponentMap
	 * @param processerType
	 * @throws Exception
	 */
	public static void doProcesser(Map<IPlugin, List<IComponent>> pluginComponentMap, ProcesserType processerType) throws Exception {
		if (!pluginComponentMap.isEmpty()) {
			for (Map.Entry<IPlugin, List<IComponent>> pluginComponent : pluginComponentMap.entrySet()) {
				IPlugin plugin = pluginComponent.getKey();
				List<IComponent> componentList = pluginComponent.getValue();
				//$NON-NLS-初始化指定插件下的所有组件$
				for (IComponent component : componentList) {
					Ignore ignore=component.getClass().getAnnotation(Ignore.class);
					boolean ignoreComponent = false;
					switch (processerType) {
						case INIT:ignoreComponent=ignore.init();break;
						case START:ignoreComponent=ignore.start();break;
						case DESTROY:ignoreComponent=ignore.destroy();break;
						default:new RuntimeException("非法类型, processerType == "+processerType);break;
					}
					
					if(ignore==null || !ignoreComponent){//不忽略
						switch (processerType) {
							case INIT:component.init();break;
							case START:component.start();break;
							case DESTROY:component.destroy();break;
							default:new RuntimeException("非法类型, processerType == "+processerType);break;
						}
					}else{//堆栈并忽略后继续
						try {
							switch (processerType) {
								case INIT:component.init();break;
								case START:component.start();break;
								case DESTROY:component.destroy();break;
								default:new RuntimeException("非法类型, processerType == "+processerType);break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
				
				//$NON-NLS-初始化指定插件下的所有组件$
				Ignore ignore=plugin.getClass().getAnnotation(Ignore.class);
				boolean ignorePlugin = false;
				switch (processerType) {
					case INIT:ignorePlugin=ignore.init();break;
					case START:ignorePlugin=ignore.start();break;
					case DESTROY:ignorePlugin=ignore.destroy();break;
					default:new RuntimeException("非法类型, processerType == "+processerType);break;
				}
				if(ignore==null || !ignorePlugin){//不忽略
					switch (processerType) {
					case INIT:plugin.init();break;
					case START:plugin.start();break;
					case DESTROY:plugin.destroy();break;
					default:new RuntimeException("非法类型, processerType == "+processerType);break;
				}
				}else{//堆栈并忽略后继续
					try {
						switch (processerType) {
						case INIT:plugin.init();break;
						case START:plugin.start();break;
						case DESTROY:plugin.destroy();break;
						default:new RuntimeException("非法类型, processerType == "+processerType);break;
					}
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}
		}
	}
}
