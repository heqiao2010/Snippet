package excel.test.hq;

public class FuncUtil {
	/**
	 * 将MAC地址转换为整数字<br>
	 *
	 */
	public static long convertMacToLong(String mac, String separator) {
		String[] parts = mac.split(separator);
		if (parts.length != 6) {
			return 0L;
		}

		long macInLong = 0L;
		for (String part : parts) {
			try {
				int m = Integer.parseInt(part, 16);
				if ((m < 0) || (m > 255)) {
					return 0L;
				}
				macInLong = macInLong << 8 | m;
			} catch (NumberFormatException nfe) {
				return 0L;
			}
		}

		return macInLong;
	}

	/**
	 * 将整数转化为16两位进制数
	 * 
	 * @param macPart
	 * @return
	 */
	private static String long2Hex4Mac(long macPart, boolean upcase) {
		if (macPart < 0L) {
			throw new IllegalArgumentException("Wrong macPart: " + macPart);
		}
		String macHex = upcase ? Long.toHexString(macPart).toUpperCase() : Long.toHexString(macPart).toLowerCase();
		if (macHex.length() == 1) {
			return "0" + macHex;
		} else {
			return macHex;
		}
	}

	/**
	 * 将整数转换为MAC地址<br>
	 * 注：MAC地址格式为减号分割字母大写的格式<br>
	 *
	 */
	public static String convertLongToMac(long longMac, String separator, boolean upcase) {
		if (longMac < 0L) {
			throw new IllegalArgumentException("Wrong mac in long: " + longMac);
		}
		return long2Hex4Mac(longMac >> 40 & 0xFF, upcase) + separator + long2Hex4Mac(longMac >> 32 & 0xFF, upcase)
				+ separator + long2Hex4Mac(longMac >> 24 & 0xFF, upcase) + separator
				+ long2Hex4Mac(longMac >> 16 & 0xFF, upcase) + separator + long2Hex4Mac(longMac >> 8 & 0xFF, upcase)
				+ separator + long2Hex4Mac(longMac & 0xFF, upcase);
	}
	
	/**
	 * 将ip地址转换为整数字
	 *
	 */
	public static long convertHostIpToLong(String hostIp) {
		if (null == hostIp) {
			throw new NullPointerException("Host IP can not be null");
		}

		String[] parts = hostIp.split("\\x2e");
		if (parts.length != 4) {
			return 0L;
		}

		long ip = 0L;
		for (String part : parts) {
			try {
				int p = Integer.parseInt(part);
				if ((p < 0) || (p > 255)) {
					return 0L;
				}
				ip = ip << 8 | p;
			} catch (NumberFormatException nfe) {
				return 0L;
			}
		}

		return ip;
	}
}
