package cn.ms.frame;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.ms.frame.common.FrameSupport;
import cn.ms.frame.common.annotation.Component;
import cn.ms.frame.common.type.ProcesserType;
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

	public Map<String, IExtension> extensionMap = new LinkedHashMap<String, IExtension>();
	public Map<IPlugin, List<IComponent>> pluginComponentMap = new LinkedHashMap<IPlugin, List<IComponent>>();

	Bootstrap() {
		//$NON-NLS-扫描插件$
		Map<String, IPlugin> pluginMap = FrameSupport.scanPCE(IPlugin.class);
		if (!pluginMap.isEmpty()) {
			for (IPlugin plugin : pluginMap.values()) {
				pluginComponentMap.put(plugin, new ArrayList<IComponent>());
			}

			//$NON-NLS-扫描组件$
			Map<String, IComponent> componentMap = FrameSupport.scanPCE(IComponent.class);
			if (!componentMap.isEmpty()) {
				for (IComponent component : componentMap.values()) {
					Component componentAnnotation = component.getClass().getAnnotation(Component.class);
					if (componentAnnotation != null) {
						String pluginName = componentAnnotation.plugin().getName();
						IPlugin plugin = pluginMap.get(pluginName);
						if (plugin != null) {
							List<IComponent> componentList = pluginComponentMap.get(plugin);
							if (componentList != null) {
								componentList.add(component);
								pluginComponentMap.put(plugin, componentList);
							}
						}
					}
				}
			}
		}

		//$NON-NLS-扫描扩展点$
		Map<String, IExtension> extensionMapTemp = FrameSupport.scanPCE(IExtension.class);
		if (!extensionMapTemp.isEmpty()) {
			extensionMap.putAll(extensionMapTemp);
		}
	}

	public void init() throws Exception {
		//$NON-NLS-初始化组件、插件$
		FrameSupport.doProcesser(pluginComponentMap, ProcesserType.INIT);
	}

	public void start() throws Exception {
		//$NON-NLS-启动组件、插件$
		FrameSupport.doProcesser(pluginComponentMap, ProcesserType.START);
	}

	public void destroy() throws Exception {
		//$NON-NLS-销毁组件、插件$
		FrameSupport.doProcesser(pluginComponentMap, ProcesserType.DESTROY);
	}

}
