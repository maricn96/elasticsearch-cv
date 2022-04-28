package mas.udd.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResultDto {

	private String title;
	private String keywords;
	private String location;
	private String highlight;
	
}
