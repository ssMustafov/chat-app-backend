package org.ruse.uni.chat.solr.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.ruse.uni.chat.core.message.Message;
import org.ruse.uni.chat.core.services.MessageService;
import org.ruse.uni.chat.solr.SolrConnector;
import org.ruse.uni.chat.solr.util.SolrUtil;

/**
 * @author sinan
 */
@ApplicationScoped
public class SolrMessageService implements MessageService {

	private static final int DEFAULT_START = 0;
	private static final int DEFAULT_ROWS = 10;

	@Inject
	private SolrConnector solrConnector;

	@Override
	public List<Message> getMessages(Long roomId, int start, int rows) {
		SolrQuery query = new SolrQuery();
		query.setFields("*");
		query.set(CommonParams.Q, "roomId:" + roomId);
		query.set(CommonParams.START, getDefaultStart(start));
		query.set(CommonParams.ROWS, getDefaultRows(rows));
		query.addOrUpdateSort("sentOn", ORDER.asc);

		QueryResponse queryResponse = solrConnector.queryWithGet(query);
		SolrDocumentList results = queryResponse.getResults();
		List<Message> messages = new ArrayList<>();

		for (SolrDocument solrDocument : results) {
			messages.add(SolrUtil.solrDocumentToMessage(solrDocument));
		}

		return messages;
	}

	private static int getDefaultStart(int start) {
		if (start <= 0) {
			return DEFAULT_START;
		}
		return start;
	}

	private static int getDefaultRows(int rows) {
		if (rows <= 0) {
			return DEFAULT_ROWS;
		}
		return rows;
	}

}
