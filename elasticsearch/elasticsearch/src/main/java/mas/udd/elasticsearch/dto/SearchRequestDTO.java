package mas.udd.elasticsearch.dto;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.sort.SortOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDTO {

	private List<FieldTermDTO> fields = new ArrayList<FieldTermDTO>();
	private String logic;
	private String sortBy;
	private SortOrder order;

}
