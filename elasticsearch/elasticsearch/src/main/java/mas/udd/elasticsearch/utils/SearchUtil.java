package mas.udd.elasticsearch.utils;

import java.util.Date;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

//ovde cemo stavljati sve metode vezane za pretragu
public class SearchUtil {
	
	private SearchUtil() { 
		
	}
	
	//ovo ispod zakomentarisano jer sam promenio DTO objekat SearchRequestDTO, bila je prvobitno lista stringova
	
//	public static SearchRequest buildSearchRequest(String indexName, SearchRequestDTO dto) {
//		
//		try {
//			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(dto)); //sa ovim bilderom samo ce da pokupi podatke iz queryBuilder-a koji smo napravili
//			
//			if(dto.getSortBy() != null) {
//				builder = builder.sort(
//						dto.getSortBy(),
//						dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
//			}
//			
//			
//			SearchRequest request = new SearchRequest(indexName);
//			request.source(builder);
//			
//			return request;
//		} catch(final Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
//	public static QueryBuilder getQueryBuilder(SearchRequestDTO dto) {
//		if(dto == null) {
//			return null;
//		}
//		
//		List<String> fields();
//		if(CollectionUtils.isEmpty(fields)) {
//			return null;
//		}
//		
//		if(fields.size() > 1) {
//			MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm()) //multimatch - znaci da radimo sa vise polja, ne sa jednim, ovde prosledjujemo term koji trazimo
//					.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS) //tip ovog upita je cross_fields, dakle pretrazivace npr i u name, i u surname itd..
//					.operator(Operator.AND); //po difoltu je OR, npr trazimo john wick on ce pretrazivati ili john ili wick, dakle ovde je da mora da ima john wick kao takav, ne odvojeno
//			
//			fields.forEach(queryBuilder::field); //primenjujemo querybuilder na svako polje u listi
//			
//			return queryBuilder;
//		}
//		
//		//ovo je slucaj sta ako imamo samo jedno polje
//		return fields.stream()
//				.findFirst() //uzimamo prvi element (tacnije taj jedan jedini)
//				.map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm()) //mapiramo polje, isto kao ono gore samo za jedno polje, ne treba nam cross_fields
//										   .operator(Operator.AND))
//				.orElse(null);
//		
//		
//	}
	
	
	//range query
	public static SearchRequest buildSearchRequest(String indexName, String field, Date date) {
		try {
			
			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));
			
			SearchRequest request = new SearchRequest(indexName);
			request.source(builder);
			
			return request;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static QueryBuilder getQueryBuilder(String field, Date date) {
		return QueryBuilders.rangeQuery(field).gte(date);
	}

}
