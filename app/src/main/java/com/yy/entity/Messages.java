package com.yy.entity;

public class Messages {
	private String msgTitle, msgTime, msgMemo;
	private int msgID, msgType, msgread;

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public String getMsgMemo() {
		return msgMemo;
	}

	public void setMsgMemo(String msgMemo) {
		this.msgMemo = msgMemo;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public int getMsgID() {
		return msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getMsgread() {
		return msgread;
	}

	public void setMsgread(int msgread) {
		this.msgread = msgread;
	}

	@Override
	public String toString() {
		return "Message [msgID=" + msgID + ", msgType=" + msgType
				+ ", msgTitle=" + msgTitle + ", msgTime=" + msgTime
				+ ", msgread=" + msgread + "]";
	}

}
