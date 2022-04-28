package mas.udd.elasticsearch.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import mas.udd.elasticsearch.dto.CVDTO;
import mas.udd.elasticsearch.dto.SearchRequestDTO;
import mas.udd.elasticsearch.handlers.DocumentHandler;
import mas.udd.elasticsearch.handlers.PDFHandler;
import mas.udd.elasticsearch.handlers.TextDocHandler;
import mas.udd.elasticsearch.handlers.Word2007Handler;
import mas.udd.elasticsearch.handlers.WordHandler;
import mas.udd.elasticsearch.helper.Indices;
import mas.udd.elasticsearch.mapper.CvMapper;
import mas.udd.elasticsearch.model.CV;
import mas.udd.elasticsearch.repository.CvRepository;
import mas.udd.elasticsearch.service.CvService;

@Service
public class CvServiceImpl implements CvService {
	
	final private static String[] FETCH_FIELDS =
	    { "id", "applicantName", "applicantSurname", "qualificationLevel", "cvContent", "coverLetterContent", "geoPoint" };

	@Autowired
	private CvRepository cvRepository;
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private CvMapper cvMapper;
	
	@Override
	public CV findById(String id) {
		return cvRepository.findById(id).get();
	}
	
	@Override
	public Iterable<CV> findAll() {
//		Collection<CV> ret = cvRepository.findAll();
		return cvRepository.findAll();
//		return Lists.newArrayList(cvRepository.findAll());
//		List<CV> retVal = new ArrayList<>();
//		cvRepository.findAll().iterator().forEachRemaining(retVal::add);
//		return retVal;
	}

	@Override
	public void save(CV toSave) {
		cvRepository.save(toSave);
	}
	
	@Override
	public void uploadCv(CVDTO dto, MultipartFile cvFile, MultipartFile coverLetterFile) throws IOException {

		CV cv = prepareCV(dto, cvFile, coverLetterFile);
		
		IndexRequest indexRequest = new IndexRequest(Indices.CV_INDEX);
		indexRequest.id(cv.getId());
		
		String json = new Gson().toJson(cv);
		
		indexRequest.source(json, XContentType.JSON);
		
		restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
	}
	
	private CV prepareCV(CVDTO dto, MultipartFile cvFile, MultipartFile coverLetterFile) {
		//ovo popravi kad resis problem sa multipart
		DocumentHandler cvHandler = getHandlerByExtension(cvFile.getOriginalFilename());
		DocumentHandler clHandler = getHandlerByExtension(coverLetterFile.getOriginalFilename());
		
		CV cv = cvMapper.fromDto(dto);
		
		
		File convertedCv = null;
		File convertedCl = null;
		
		try {
			convertedCv = convert(cvFile);
			convertedCl = convert(coverLetterFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cv.setCvContent(cvHandler.getText(convertedCv));
		cv.setCoverLetterContent(clHandler.getText(convertedCl));
		return cv;
	}
	
	private File convert(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename()); //"pdf" + File.separator + 
		convertedFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}
	
	private DocumentHandler getHandlerByExtension(String fileName) {
		if(fileName.endsWith(".txt"))
			return new TextDocHandler();
		else if(fileName.endsWith(".pdf"))
			return new PDFHandler();
		else if(fileName.endsWith(".doc"))
			return new WordHandler();
		else if(fileName.endsWith("docx"))
			return new Word2007Handler();
		else
			return null;
	}
	

	@Override
	public List<CV> search(SearchRequestDTO dto) throws IOException {
		
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		BoolQueryBuilder boolQueryBuilder = createBoolQueryBuilder(dto);
		
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("coverLetterContent");
		highlightContent.highlighterType("unified");
		
		highlightBuilder.field(highlightContent);
		
		searchSourceBuilder.query(boolQueryBuilder).fetchSource(FETCH_FIELDS, null).highlighter(highlightBuilder);
		
		searchRequest.indices(Indices.CV_INDEX);
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		
		List<CV> retVal = new ArrayList<CV>();
		
		search.getHits().forEach(hit -> {
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			HighlightField highlightField = highlightFields.get("coverLetterContent");
			
			CV cvTemp = new Gson().fromJson(hit.getSourceAsString(), CV.class);
			CV cv = cvRepository.findById(cvTemp.getId()).get(); //morao sam ovako jer ne povlaci cvSqlId iz lucene-a..
			
			if(Optional.ofNullable(highlightField).isPresent()) {
				Text[] fragments = highlightField.fragments();
				String contentHighlight = "";
				for(Text text : fragments) {
//					String[] parts = text.toString().split("<em");
//					String s1 = parts[0];
//					String s2 = s1.concat("<em style=\"background-color: yellow; \"");
//					String finalString = s2.concat(parts[1]); 
					contentHighlight = contentHighlight + text.toString() + "\n";
				}
				cv.setCoverLetterContent(contentHighlight);
			} else {
				cv.setCoverLetterContent(cv.getCoverLetterContent());
			}
			retVal.add(cv);
		});

		return retVal;
		
	}

	private BoolQueryBuilder createBoolQueryBuilder(SearchRequestDTO searchFields) {
		if(searchFields.getFields().isEmpty())
			return QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		
		searchFields.getFields().forEach(f -> {
			String field = f.getField();
			String value = f.getValue();
			Boolean phrase = f.getPhraseQuery();
			
			if(searchFields.getLogic().equals("AND")) {
				if(phrase != null && phrase)
					boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery(field, value));
				else
					boolQueryBuilder.must(QueryBuilders.matchQuery(field, value));
			} else {
				if(phrase != null && phrase)
					boolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery(field, value));
				else 
					boolQueryBuilder.should(QueryBuilders.matchQuery(field, value));
			}
		});
		
		return boolQueryBuilder;
		
	}

	@Override
	public List<CV> geolocationSearch(Double lat, Double lon, Integer distance) throws IOException {
		SearchRequest searchRequest = new SearchRequest();
		
		QueryBuilder queryBuilder = QueryBuilders
				.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.must(QueryBuilders.geoDistanceQuery("geoPoint")
						.point(lat, lon)
						.distance(distance + "km"));
	
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder);
		
		searchRequest.indices(Indices.CV_INDEX);
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		
		List<CV> retVal = new ArrayList<>();
		search.getHits().forEach(hit -> {
			retVal.add(new Gson().fromJson(hit.getSourceAsString(), CV.class));
		});
		
		return retVal;
	}


}
