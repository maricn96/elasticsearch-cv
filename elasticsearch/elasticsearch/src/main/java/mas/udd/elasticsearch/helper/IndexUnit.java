package mas.udd.elasticsearch.helper;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexUnit {
	
	private String text;
	private String title;
	private List<String> keywords = new ArrayList<String>();
	private String filename;
	private String filedate;
}
