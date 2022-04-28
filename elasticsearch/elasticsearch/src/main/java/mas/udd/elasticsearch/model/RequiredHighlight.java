package mas.udd.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class RequiredHighlight {

	private String fieldName;
	private String value;
	
}
