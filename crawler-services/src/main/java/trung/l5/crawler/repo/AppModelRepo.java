/**
 * Company: Mideas
 * @author toantruyen
 * @date: Apr 13, 2019 10:40:05 PM
 * @des: 
 */
package trung.l5.crawler.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import trung.l5.crawler.model.AppModel;

/**
 * @author toantruyen
 * @date: Apr 13, 2019 10:40:05 PM
 * @des: 
 */
public interface AppModelRepo extends MongoRepository<AppModel, String>{
	Page<AppModel> findByCatid(String catId, Pageable pageable);
	Page<AppModel> findByCatidAndIsEn(String catId, Pageable pageable, Boolean isEn);
	Page<AppModel> findByCatidAndIsVideoAndIsEn(String catId, Pageable pageable, Boolean isVideo, Boolean isEn);
	Optional<AppModel> findById(int id);
	// Xoa data truoc ngay
	void deleteByCreatedDateBefore(Date date);
	List<AppModel> findByCreatedDateBefore(Date date);
}
