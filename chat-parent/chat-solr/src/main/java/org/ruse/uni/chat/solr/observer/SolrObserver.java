package org.ruse.uni.chat.solr.observer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.solr.common.SolrInputDocument;
import org.ruse.uni.chat.core.events.MessageSentEvent;
import org.ruse.uni.chat.core.message.Message;
import org.ruse.uni.chat.solr.SolrConnector;
import org.ruse.uni.chat.solr.util.SolrUtil;

/**
 * @author sinan
 */
@ApplicationScoped
public class SolrObserver {

	@Inject
	private SolrConnector solrConnector;

	public void onMessageSent(@Observes MessageSentEvent event) {
		Message message = event.getMessage();
		SolrInputDocument solrDocument = SolrUtil.messageToSolrDocument(message);
		solrConnector.insertOrUpdate(solrDocument);
	}

}
