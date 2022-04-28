package mas.udd.elasticsearch.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadDocumentDto {

	private String title;
	private String keywords;
	private MultipartFile[] files;
	
}
