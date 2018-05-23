package daewook.assignment.urlshortener.utils;

public class Base52Encoder extends Thread {
    private static final String CODES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * shorten number를 52진수로 변환하여, 해당하는 shorten code를 반환
     * @param i shorten number
     * @return shorten code
     */
    public static String encode(long i) {
        StringBuilder result = new StringBuilder();
        if (i == 0) {
            return String.valueOf(CODES.charAt(0));
        }

        while(i>0) {
            result.append(CODES.charAt((int)i % 52));
            i /= 52;
        }
        return result.toString();
    }

    /**
     * shorten code를 shorten number로 변환
     * @param a shorten code
     * @return shorten number
     */
    public static long decode(String a) {
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result += CODES.indexOf(a.charAt(i)) * Math.pow(52, i);
        }

        return result;
    }
}
