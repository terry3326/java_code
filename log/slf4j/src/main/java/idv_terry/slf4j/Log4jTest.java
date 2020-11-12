package idv_terry.slf4j;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
	// 定義log4j日誌紀錄器
	public static final Logger LOGGG = Logger.getLogger(Log4jTest.class);

	// 測試橋接器
	@Test
	public void test01() throws Exception {
		LOGGG.info("hello log4j");
	}

}
