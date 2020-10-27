
package trung.l5.crawler.services;

import java.util.Date;
import java.util.List;

import trung.l5.crawler.model.AppModel;

/**
 * @author toantruyen
 * @date: Apr 14, 2019 12:11:28 AM
 * @des: 
 */
public interface AppModelService {
	AppModel add(AppModel appModel);
	
	AppModel getById(int id);
	
	List<AppModel> addAll(List<AppModel> appModels);
	
	List<AppModel> findByCateId(String types, int page);

	List<AppModel> findByCatIdAndIsEn(String types, int page, Boolean isEn);

	List<AppModel> findByCatIdAndIsVideoAndIsEn(String types, int page,  Boolean isVideo, Boolean isEn);
	
	void deleteByCreatedDateBefore(Date date);
}
