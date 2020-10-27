/**
 * Company: Mideas
 * @author toantruyen
 * @date: Apr 14, 2019 6:58:32 AM
 * @des: 
 */
package trung.l5.crawler.model.enums;
/**
 * @author toantruyen
 * @date: Apr 14, 2019 6:58:32 AM
 * @des:
 */
public enum Types {
//	thoi_su, van_hoa, phap_luat;
	thoi_su("1"),
	van_hoa("2"),
	phap_luat("3");

	  private String value;    

	  private Types(String value) {
	    this.value = value;
	  }

	  public String getValue() {
	    return value;
	  }
}