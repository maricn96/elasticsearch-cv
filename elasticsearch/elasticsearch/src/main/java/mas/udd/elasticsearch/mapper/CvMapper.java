package mas.udd.elasticsearch.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Component;

import mas.udd.elasticsearch.dto.CVDTO;
import mas.udd.elasticsearch.model.CV;
import mas.udd.elasticsearch.service.CVsqlService;

@Component
public class CvMapper {

	@Autowired
	private CVsqlService cVsqlService;
	
	public CV fromDto(CVDTO x) {
		return CV.builder()
				 .id(x.getId())
				 .cvSqlId(x.getCvSqlId())
				 .applicantName(x.getApplicantName())
				 .applicantSurname(x.getApplicantSurname())
				 .qualificationLevel(x.getQualificationLevel())
				 .address(x.getAddress())
				 .email(x.getAddress())
				 .cvContent(x.getCvContent())
				 .coverLetterContent(x.getCoverLetterContent())
				 .geoPoint(new GeoPoint(x.getLat(), x.getLon()))
				 .build();
	}

	public CVDTO toDto(CV x) {
		return CVDTO.builder()
				.id(x.getId())
				.cvSqlId(x.getCvSqlId())
				.cvFileName(cVsqlService.getCv(Long.valueOf(x.getCvSqlId().longValue())).getCvFileName())
				.coverLetterFileName(cVsqlService.getCv(Long.valueOf(x.getCvSqlId())).getCoverLetterFileName())
				.applicantName(x.getApplicantName())
				.applicantSurname(x.getApplicantSurname())
				.qualificationLevel(x.getQualificationLevel())
				.address(x.getAddress())
				.email(x.getEmail())
				.cvContent(x.getCvContent())
				.coverLetterContent(x.getCoverLetterContent())
				.lat(x.getGeoPoint().getLat())
				.lon(x.getGeoPoint().getLon())
				.build();
	}
	
	public <T, U> List<U> toDtoAll(List<T> list, Function<T, U> f) {
		List<U> retVal = new ArrayList<>();
		for(T t : list) {
			retVal.add(f.apply(t));
		}
		return retVal;
	}
}