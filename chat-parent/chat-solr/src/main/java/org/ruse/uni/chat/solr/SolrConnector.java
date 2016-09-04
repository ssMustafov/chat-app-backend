package org.ruse.uni.chat.solr;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.ruse.uni.chat.core.configuration.ConfigurationProperty;

@ApplicationScoped
public class SolrConnector {

	@Inject
	@ConfigurationProperty(name = "solr.url")
	private String solrUrl;

	private SolrClient client;

	@PostConstruct
	private void init() {
		client = new HttpSolrClient.Builder(solrUrl).build();
	}

	@PreDestroy
	private void destroy() {
		try {
			client.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public QueryResponse queryWithGet(SolrQuery query) {
		return query(query, METHOD.GET);
	}

	public QueryResponse queryWithPost(SolrQuery query) {
		return query(query, METHOD.POST);
	}

	private QueryResponse query(SolrQuery query, METHOD method) {
		QueryResponse response = null;
		try {
			response = client.query(query, method);
		} catch (SolrServerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return response;
	}

	public void insertOrUpdate(SolrInputDocument doc) {
		try {
			client.add(doc);
			client.commit();
		} catch (SolrServerException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
