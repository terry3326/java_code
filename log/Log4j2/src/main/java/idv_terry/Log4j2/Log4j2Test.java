package idv_terry.Log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4j2Test {

	// 定義日誌紀錄器對象
	public static final Logger LOGGER = LogManager.getLogger(Log4j2Test.class);

	// 快速入門
	@Test
	public void test01() throws Exception {
		// 日誌消息輸出 級別由上到下
		LOGGER.fatal("fatal");
		LOGGER.error("error");// 默認級別
		LOGGER.warn("warning");
		LOGGER.info("info");
		LOGGER.debug("debug");
		LOGGER.trace("trace");
	}
}
