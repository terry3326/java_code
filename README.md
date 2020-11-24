//獲取Appender所有子類:ConsoleAppender、RollingFileAppender、AsyncAppender 來取得其配置訊息
org.apache.logging.log4j.core.Logger impl=(org.apache.logging.log4j.core.Logger)LogManager.getLogger();
Collection<Appender> values = impl.getContext().getConfiguration().getAppenders().values();
//遍歷取得指定的Appender
 static <T extends Appender> List<T> getAppender(Class<T> clazz){
		List<T> list=null;
		org.apache.logging.log4j.core.Logger impl=(org.apache.logging.log4j.core.Logger)LogManager.getLogger();
		//獲取Appender所有子類:ConsoleAppender、RollingFileAppender、AsyncAppender 來取得其配置訊息
		for(Appender a:impl.getContext().getConfiguration().getAppenders().values()){
			if(clazz.isAssignableFrom(a.getClass())==true){
				if(list==null){
					list=new ArrayList<T>();
				}
				list.add((T)a);
			}
		}
		return list;
	}
