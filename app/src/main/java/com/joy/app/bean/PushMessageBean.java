package com.joy.app.bean;

import com.joy.library.utils.TextUtil;

import java.io.Serializable;

/**
 * 小米push的解析对象
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-19
 */
public class PushMessageBean implements Serializable {

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
}
