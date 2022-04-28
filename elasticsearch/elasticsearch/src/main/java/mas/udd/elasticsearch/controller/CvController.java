package mas.udd.elasticsearch.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import mas.udd.elasticsearch.dto.CVDTO;
import mas.udd.elasticsearch.dto.GeolocationSearchDTO;
import mas.udd.elasticsearch.dto.SearchRequestDTO;
import mas.udd.elasticsearch.mapper.CvMapper;
import mas.udd.elasticsearch.model.CV;
import mas.udd.elasticsearch.model.CVsql;
import mas.udd.elasticsearch.service.CVsqlService;
import mas.udd.elasticsearch.service.CvService;

@RestController
@RequestMapping("/api/cv")
@CrossOrigin(origins = "http://localhost:4200")
public class CvController {
	
	@Autowired
	private CvService cvService;
	
	@Autowired
	private CvMapper cvMapper;
	
	@Autowired
	private CVsqlService cvsqlService;
	
	@GetMapping("/{id}")
	public ResponseEntity<CVDTO> findById(@PathVariable("id") String id) {
		return new ResponseEntity<CVDTO>(cvMapper.toDto(cvService.findById(id)), HttpStatus.OK);
	}

	@Deprecated
	@GetMapping("/all")
	public ResponseEntity<Iterable<CV>> findAll() {
//		return new ResponseEntity<List<CVDTO>>(cvMapper.toDtoAll(cvService.findAll(), 
//				x -> cvMapper.toDto(x)), HttpStatus.OK);
		return new ResponseEntity<Iterable<CV>>(cvService.findAll(), HttpStatus.OK);
	}

	@Deprecated
	@PostMapping
	public ResponseEntity<String> save(@RequestBody CVDTO dto) {
		cvService.save(cvMapper.fromDto(dto));
		return new ResponseEntity<String>("created", HttpStatus.CREATED);
	}

	@RequestMapping(value="/uploadCv", consumes = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.MULTIPART_FORM_DATA})
	public ResponseEntity<String> uploadCv(
			   @RequestParam("cvFile") MultipartFile[] cvFile,
			   @RequestParam("coverLetterFile") MultipartFile[] coverLetterFile,
			   @RequestParam("data") String data) 
				   throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		CVDTO cvJson = mapper.readValue(data, CVDTO.class);
		CVsql cVsqlSaved = cvsqlService.uploadCvsql(cvJson, cvFile[0], coverLetterFile[0]);
		cvJson.setCvSqlId(Integer.valueOf(cVsqlSaved.getId().intValue()));
		cvService.uploadCv(cvJson, cvFile[0], coverLetterFile[0]);
		return new ResponseEntity<String>("UPLOADED", HttpStatus.CREATED);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<CVDTO>> search(@RequestBody SearchRequestDTO dto) throws IOException {
		return new ResponseEntity<List<CVDTO>>(cvMapper.toDtoAll(cvService.search(dto), x -> cvMapper.toDto(x)), HttpStatus.OK);

				//				cvMapper.toDtoAll(cvService.search(dto.stream().filter(x -> 
//				x != null).collect(Collectors.toList())), x -> cvMapper.toDto(x)), HttpStatus.OK);
	}
	
	@PostMapping("/geolocationSearch")
	public ResponseEntity<List<CVDTO>> geolocationSearch(@RequestBody GeolocationSearchDTO dto) throws IOException {
		return new ResponseEntity<List<CVDTO>>(cvMapper.toDtoAll(cvService.geolocationSearch(dto.getLat(), dto.getLon(), dto.getDistance()), x -> cvMapper.toDto(x)), HttpStatus.OK);
	}

	@GetMapping("/getRawCvDocument/{cvSqlid}")
	public ResponseEntity<byte[]> getRawCvDocument(@PathVariable Long cvSqlid) {
		CVsql cv = cvsqlService.getCv(cvSqlid);
		return new ResponseEntity<byte[]>(cv.getCvContent(), HttpStatus.OK);
	}
	
}
