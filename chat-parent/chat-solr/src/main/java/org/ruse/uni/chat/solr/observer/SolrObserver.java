package org.ruse.uni.chat.solr.observer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.solr.common.SolrInputDocument;
import org.ruse.uni.chat.core.events.MessageSentEvent;
import org.ruse.uni.chat.core.message.Message;
import org.ruse.uni.chat.solr.SolrConnector;

/**
 * @author sinan
 */
@ApplicationScoped
public class SolrObserver {

	@Inject
	private SolrConnector solrConnector;

	public void onMessageSent(@Observes MessageSentEvent event) {
		Message message = event.getMessage();
		SolrInputDocument solrDocument = messageToSolrDocument(message);
		solrConnector.insertOrUpdate(solrDocument);
	}

	private static SolrInputDocument messageToSolrDocument(Message message) {
		SolrInputDocument document = new SolrInputDocument();

		document.addField("userId", message.getUserId());
		document.addField("roomId", message.getRoomId());
		document.addField("message", message.getMessage());
		document.addField("sentOn", message.getSentOn().getTime());

		return document;
	}

}
