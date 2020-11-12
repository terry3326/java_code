package idv_terry.Log4j2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);

	@Test
	public void test01() throws Exception {
		// 日誌輸出:級別由上到下
		LOGGER.error("error");
		LOGGER.warn("warning");
		LOGGER.info("info");// 默認級別
		LOGGER.debug("debug");
		LOGGER.trace("trace");

//		// 使用佔位符輸出日誌訊息
//		String name = "terry";
//		String age = "30";
//		LOGGER.info("用戶: {},{}", name, age);
//
//		// 將系統的異常訊息輸出
//		try {
//			int i = 1 / 0;
//		} catch (Exception e) {
//			LOGGER.error("出現異常:", e);
//		}

	}

}
