/**
 * Company: Mideas
 * @author toantruyen
 * @date: Apr 1, 2019 1:59:56 PM
 * @des: 
 */
package trung.l5.crawler.utils;

/**
 * @author toantruyen
 * @date: Apr 1, 2019 1:59:57 PM
 * @des: 
 */
public class HashUtils {
	/**
     * 
     * @param txt, text in plain format
     * @param hashType MD5 OR SHA1
     * @return hash in hashType 
     */
    public static String getHash(String txt, String hashType) {
        try {
                    java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
                    byte[] array = md.digest(txt.getBytes());
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < array.length; ++i) {
                        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
                 }
                    return sb.toString();
            } catch (java.security.NoSuchAlgorithmException e) {
                //error action
            }
            return null;
    }

    public static String md5(String txt) {
        return HashUtils.getHash(txt, "MD5");
    }

    public static String sha1(String txt) {
        return HashUtils.getHash(txt, "SHA1");
    }
    public static void main(String[] args) {
		System.out.println(HashUtils.md5("hana_hana@!@!#$1903280000000"));
	}
}
