/**
 * Company: Mideas
 *
 * @author toantruyen
 * @date: Apr 14, 2019 12:11:40 AM
 * @des:
 */
package trung.l5.crawler.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.model.enums.Types;
import trung.l5.crawler.repo.AppModelRepo;

/**
 * @author toantruyen
 * @date: Apr 14, 2019 12:11:40 AM
 * @des:
 */
@Service
public class AppModelServiceImpl implements AppModelService {

    @Autowired
    AppModelRepo appModelRepo;

    /* (non-Javadoc)
     * @see trung.l5.crawler.services.AppModelService#add(trung.l5.crawler.model.AppModel)
     */
    @Override
    public AppModel add(AppModel appModel) {
        return appModelRepo.save(appModel);
    }

    /* (non-Javadoc)
     * @see trung.l5.crawler.services.AppModelService#addAll(java.util.List)
     */
    @Override
    public List<AppModel> addAll(List<AppModel> appModels) {
        return appModelRepo.saveAll(appModels);
    }

    /* (non-Javadoc)
     * @see trung.l5.crawler.services.AppModelService#findByCateId(trung.l5.crawler.model.enums.Types)
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<AppModel> findByCateId(String types, int page) {
        Pageable pageable = new PageRequest(page, 50, Sort.by("createdDate").descending());
        Page<AppModel> appModels = appModelRepo.findByCatid(types, pageable);
        return appModels.getContent();
    }

    @Override
    public List<AppModel> findByCatIdAndIsEn(String types, int page, Boolean isEn) {
        Pageable pageable = new PageRequest(page, 50, Sort.by("createdDate").descending());
        Page<AppModel> appModels = appModelRepo.findByCatidAndIsEn(types, pageable, isEn);
        return appModels.getContent();
    }

    @Override
    public List<AppModel> findByCatIdAndIsVideoAndIsEn(String types, int page, Boolean isVideo , Boolean isEn) {
        Pageable pageable = new PageRequest(page, 50, Sort.by("createdDate").descending());
        Page<AppModel> appModels = appModelRepo.findByCatidAndIsVideoAndIsEn(types, pageable, isVideo, isEn);
        return appModels.getContent();
    }

    /* (non-Javadoc)
     * @see trung.l5.crawler.services.AppModelService#getById(java.lang.String)
     */
    @Override
    public AppModel getById(int id) {
        return appModelRepo.findById(id).orElse(null);
    }

	/* (non-Javadoc)
	 * @see trung.l5.crawler.services.AppModelService#deleteByCreatedDateBefore(java.util.Date)
	 */
	@Override
	public void deleteByCreatedDateBefore(Date date) {
		appModelRepo.deleteByCreatedDateBefore(date);
	}

}
