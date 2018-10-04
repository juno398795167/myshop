package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {
	@Autowired
	private SolrClient solrClient;


	@Test
	public void addSolr(){
		SolrInputDocument solrInputFields = new SolrInputDocument();
		solrInputFields.addField("id",9);
		solrInputFields.addField("gtitle","玩家国度2");
		solrInputFields.addField("gprice",199999);
		solrInputFields.addField("ginfo","败家之眼，笔记本中的战斗机");
		solrInputFields.addField("gimage","www.baidu.com");
		try {
			solrClient.add(solrInputFields);
			solrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		@Test
	public void deleteByid() throws IOException, SolrServerException {
			solrClient.deleteByQuery("gtitle:玩家");
			solrClient.commit();
		}
		@Test
	public void  query() throws IOException, SolrServerException {
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			QueryResponse query = solrClient.query(solrQuery);
			SolrDocumentList results = query.getResults();
			for(SolrDocument s:results){
				String id =(String)s.getFieldValue("id");
				String gtitle =(String)s.getFieldValue("gtitle");
				String ginfo = (String)s.getFieldValue("ginfo");
				Float gprice = (float)s.getFieldValue("gprice");
				System.out.println(id);
				System.out.println(gtitle);
				System.out.println(ginfo);
				System.out.println(gprice);
			}
		}
}
