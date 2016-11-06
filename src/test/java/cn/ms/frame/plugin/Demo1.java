package cn.ms.frame.plugin;

import cn.ms.frame.common.annotation.Ignore;
import cn.ms.frame.common.annotation.Plugin;

@Ignore
@Plugin
public class Demo1 implements IPlugin {

	public void init() throws Exception {
		System.out.println(getClass().getSimpleName()+"-IPlugin-init");		
		throw new RuntimeException("插件Demo1初始化异常");
	}

	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
