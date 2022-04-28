package mas.udd.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldTermDTO {

	private String field;
	private String value;
	private Boolean phraseQuery;
	
}
