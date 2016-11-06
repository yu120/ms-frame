package cn.ms.frame;

/**
 * 适配器
 * 
 * @author lry
 */
public interface IAdapter {

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * 启动
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;
	
	/**
	 * 销毁
	 * 
	 * @throws Exception
	 */
	void destroy() throws Exception;
	
}
