package org.ruse.uni.chat.solr.util;

import java.util.Date;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.ruse.uni.chat.core.message.Message;

/**
 * @author sinan
 */
public class SolrUtil {

	public static SolrInputDocument messageToSolrDocument(Message message) {
		SolrInputDocument document = new SolrInputDocument();

		document.addField("userId", message.getUserId());
		document.addField("roomId", message.getRoomId());
		document.addField("message", message.getMessage());
		document.addField("sentOn", message.getSentOn().getTime());

		return document;
	}

	public static Message solrDocumentToMessage(SolrDocument solrDocument) {
		String messageId = solrDocument.get("id").toString();
		String documentRoomId = solrDocument.get("roomId").toString();
		String userId = solrDocument.get("userId").toString();
		String chatMessage = solrDocument.get("message").toString();
		String dateTime = solrDocument.get("sentOn").toString();

		Message message = new Message();
		message.setId(messageId);
		message.setRoomId(Long.valueOf(documentRoomId));
		message.setUserId(Long.valueOf(userId));
		message.setMessage(chatMessage);
		Date date = new Date(Long.valueOf(dateTime));
		message.setSentOn(date);

		return message;
	}

}
