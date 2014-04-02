package com.dianping.ba.finance.exchange.biz.utils;

import com.dianping.finance.common.util.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-1-26
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class BizUtils {

	/**
	 * 创建sequence
	 *
	 * @param clientNo 客户no
	 * @param bizId    id
	 * @return
	 */
	public static String createSequence(String clientNo, String bizId) {
		Date today = Calendar.getInstance().getTime();
		String todayHour;
		try {
			todayHour = DateUtils.format("yyyyMMddHH", today);
		} catch (ParseException e) {
			todayHour ="";
		}
		return clientNo + "|" + bizId + "|" + todayHour;
	}


}
