package cn.ms.frame.component;

import cn.ms.frame.common.annotation.Component;
import cn.ms.frame.common.annotation.Ignore;

@Ignore
@Component(plugin=cn.ms.frame.plugin.Demo2.class)
public class Demo1 implements IComponent {

	public void init() throws Exception {
		System.out.println(getClass().getSimpleName()+"-IComponent-init");
	}

	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
