package mas.udd.elasticsearch.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import mas.udd.elasticsearch.dto.CVDTO;
import mas.udd.elasticsearch.model.CVsql;

public interface CVsqlService {

	CVsql uploadCvsql(CVDTO data, MultipartFile cv, MultipartFile coverLetter) throws IOException;

	CVsql getCv(Long id);

}
