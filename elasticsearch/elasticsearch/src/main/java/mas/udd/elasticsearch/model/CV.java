package mas.udd.elasticsearch.model;

import javax.persistence.Entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mas.udd.elasticsearch.helper.Indices;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = Indices.CV_INDEX)
@Setting(settingPath = "static/es-settings.json")
@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
public class CV {
	
	@Id
	@Field(type = FieldType.Keyword)
	private String id;
	
	@Field(type = FieldType.Integer, store = true)
	private Integer cvSqlId;

	@Field(type = FieldType.Text, store = true, analyzer = "serbian")
	private String applicantName;

	@Field(type = FieldType.Text, store = true, analyzer = "serbian")
	private String applicantSurname;

	@Field(type = FieldType.Integer, store = true)
	private Integer qualificationLevel; //1-8
	
	@Field(type = FieldType.Text, store = false)
	private String address;
	
	@Field(type = FieldType.Text, store = false)
	private String email;
	
	@Field(type = FieldType.Text, store = true, analyzer = "serbian")
	private String cvContent;
	
	@Field(type = FieldType.Text, store = true, analyzer = "serbian")
	private String coverLetterContent;

	@Field(type = FieldType.Object, store = true)
	@GeoPointField
	private GeoPoint geoPoint;

}
