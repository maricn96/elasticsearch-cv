package mas.udd.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeolocationSearchDTO {

	private Double lat;
	private Double lon;
	private Integer distance;
	
}
