package mas.udd.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import mas.udd.elasticsearch.model.CV;

public interface CvRepository extends ElasticsearchRepository<CV, String> {

	List<CV> findAll();
	
}
