package com.joy.app.bean;


import com.android.library.utils.TextUtil;

import java.io.Serializable;

/**
 * 推送消息实体 
 * @author qyer
 *
 */
public class MiPushMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title = TextUtil.TEXT_EMPTY;
	private String message = TextUtil.TEXT_EMPTY;
	private String type = TextUtil.TEXT_EMPTY;
	private String uri = TextUtil.TEXT_EMPTY;
	private String messageId = TextUtil.TEXT_EMPTY;

	public String getMessageId() {
		
		return messageId;
	}

	public void setMessageId(String messageId) {
	
		this.messageId = TextUtil.filterNull(messageId);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
	
		this.title = TextUtil.filterNull(title);
	}
	
	public String getMessage() {
		
		return message;
	}
	
	public void setMessage(String message) {
		
		this.message = TextUtil.filterNull(message);
	}
	
	public String getType() {
		
		return type;
	}
	public void setType(String type) {
		
		this.type = TextUtil.filterNull(type);
	}
	
	public String getUri() {
		
		return uri;
	}
	
	public void setUri(String uri) {
		
		this.uri = TextUtil.filterNull(uri);
	}
	
	@Override
	public String toString() {
		return "QaPushMessage [title=" + title + ", message=" + message
				+ ", type=" + type + ", uri=" + uri + ", messageId="
				+ messageId + "]";
	}
}
