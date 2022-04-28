package mas.udd.elasticsearch.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mas.udd.elasticsearch.dto.CVDTO;
import mas.udd.elasticsearch.dto.SearchRequestDTO;
import mas.udd.elasticsearch.model.CV;

public interface CvService {

	CV findById(String id);

	Iterable<CV> findAll();
	
	void save(CV cv);
	
	void uploadCv(CVDTO dto, MultipartFile cvFile, MultipartFile coverLetterFile) throws IOException;
	
	List<CV> search(SearchRequestDTO dto) throws IOException;

	List<CV> geolocationSearch(Double lat, Double lon, Integer distance) throws IOException;

}
