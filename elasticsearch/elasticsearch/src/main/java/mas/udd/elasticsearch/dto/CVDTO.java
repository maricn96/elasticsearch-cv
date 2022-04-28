package mas.udd.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CVDTO {

	private String id;
	
	private Integer cvSqlId;
	
	private String applicantName;

	private String applicantSurname;

	private Integer qualificationLevel;
	
	private String address;

	private String email;
	
	private String cvContent;
	
	private String coverLetterContent;

	private Double lat;
	
	private Double lon;
	 
	private String cvFileName;
	
	private String coverLetterFileName;
}
