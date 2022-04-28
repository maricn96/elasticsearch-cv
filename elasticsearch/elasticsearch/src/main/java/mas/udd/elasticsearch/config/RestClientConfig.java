package mas.udd.elasticsearch.config;

import java.awt.print.Book;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import mas.udd.elasticsearch.helper.Indices;

@Configuration
@EnableElasticsearchRepositories( "mas.udd.elasticsearch.repository" ) //zbog koriscenja es repositorija
@ComponentScan( "mas.udd.elasticsearch" )
public class RestClientConfig extends AbstractElasticsearchConfiguration {
	
	//https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.clients.rest
	
	@Value("${elasticsearch.url}")
	public String elasticsearchUrl;

	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {
		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(this.elasticsearchUrl)
				.build();
		
		return RestClients.create(clientConfiguration).rest();
	}
	
	
//	@PostConstruct
    public void initializeIndexes() throws IOException
    {
        RestHighLevelClient elasticsearchClient = elasticsearchClient();

//        GetIndexRequest getIndexRequestBook = new GetIndexRequest( Indices.CV_INDEX );

        CreateIndexRequest createIndexRequest = new CreateIndexRequest( Indices.CV_INDEX );
	
	            createIndexRequest.settings( Settings.builder()
	
	                    .put( "index.number_of_shards", 5 ) //broj sardova
	
	                    .put( "index.number_of_replicas", 5 ) //broj replika
	
	 
	            		);
	            CreateIndexResponse create = elasticsearchClient.indices().create( createIndexRequest, RequestOptions.DEFAULT ); //vracamo neki response koji cemo dobiti prilikom kreiranja indeksa

    }
	
}
