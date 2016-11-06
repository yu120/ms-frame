package cn.ms.frame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import cn.ms.frame.annotation.Component;
import cn.ms.frame.annotation.Extension;
import cn.ms.frame.annotation.AnnotationType;
import cn.ms.frame.annotation.Plugin;
import cn.ms.frame.component.IComponent;
import cn.ms.frame.extension.IExtension;
import cn.ms.frame.plugin.IPlugin;

/**
 * 启动
 * 
 * @author lry
 */
public enum Bootstrap implements IAdapter {

	INSTANCE;

	public Map<String, IPlugin> pluginMap = new LinkedHashMap<String, IPlugin>();
	public Map<String, IComponent> componentMap = new LinkedHashMap<String, IComponent>();
	public Map<String, IExtension> extensionMap = new LinkedHashMap<String, IExtension>();

	public static void main(String[] args) throws Exception {
		INSTANCE.init();
	}

	public void init() throws Exception {
		//$NON-NLS-扫描插件$
		Map<String, IPlugin> pluginMapTemp = scanPCE(IPlugin.class);
		if(!pluginMapTemp.isEmpty()){
			pluginMap.putAll(pluginMapTemp);
		}

		//$NON-NLS-扫描组件$
		Map<String, IComponent> componentMapTemp = scanPCE(IComponent.class);
		if(!componentMapTemp.isEmpty()){
			componentMap.putAll(componentMapTemp);
		}

		//$NON-NLS-扫描扩展点$
		Map<String, IExtension> extensionMapTemp = scanPCE(IExtension.class);
		if(!extensionMapTemp.isEmpty()){
			extensionMap.putAll(extensionMapTemp);
		}
	}

	public void start() throws Exception {

	}

	public void destroy() throws Exception {

	}

	/**
	 * 扫描插件/组件/扩展点
	 * 
	 * @param clazz
	 * @return
	 */
	private <T> Map<String, T> scanPCE(final Class<T> clazz) {
		//$NON-NLS-类型$
		final AnnotationType annotationType;
		if (clazz.getName().equals(IPlugin.class.getName())) {
			annotationType = AnnotationType.Plugin;
		} else if (clazz.getName().equals(IComponent.class.getName())) {
			annotationType = AnnotationType.Component;
		} else if (clazz.getName().equals(IExtension.class.getName())) {
			annotationType = AnnotationType.Extension;
		} else {
			throw new RuntimeException("非法类型, clazz == "+clazz);
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
				} case Component: {
					if (service.getClass().getAnnotation(Component.class) != null) {
						serviceList.add(service);
					}
					break;
				} case Extension: {
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
						return o1.getClass().getAnnotation(Plugin.class).order() - o2.getClass().getAnnotation(Plugin.class).order();
					case Component:
						return o1.getClass().getAnnotation(Component.class).order() - o2.getClass().getAnnotation(Component.class).order();
					case Extension: 
						return o1.getClass().getAnnotation(Extension.class).order() - o2.getClass().getAnnotation(Extension.class).order();
					default:
						throw new RuntimeException("非法类型, clazz == "+clazz);
				}
			}
		});
		
		//$NON-NLS-回收$
		for (T service : serviceList) {
			String key = service.getClass().getName();
			serviceMap.put(key, service);
		}
		
		return serviceMap;
	}

}
