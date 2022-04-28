package mas.udd.elasticsearch.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mas.udd.elasticsearch.dto.CVDTO;
import mas.udd.elasticsearch.model.CVsql;
import mas.udd.elasticsearch.repository.CVsqlRepository;
import mas.udd.elasticsearch.service.CVsqlService;

@Service
public class CVsqlServiceImpl implements CVsqlService {

	@Autowired
	private CVsqlRepository repository;

	@Override
	public CVsql uploadCvsql(CVDTO data, MultipartFile cv, MultipartFile coverLetter) throws IOException {
		CVsql cVsql = new CVsql(null, data.getAddress(), data.getEmail(), data.getCvFileName(), data.getCoverLetterFileName(), cv.getBytes());
		return repository.save(cVsql);
	}

	@Override
	public CVsql getCv(Long id) {
		return repository.findById(id).get();
	}
	
}
