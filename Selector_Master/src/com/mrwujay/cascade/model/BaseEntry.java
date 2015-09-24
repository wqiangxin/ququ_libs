package com.mrwujay.cascade.model;

import java.io.Serializable;

/**
 * ����˷��صĻ���ʵ����
 * @author MinMin
 *
 * @param <T>
 */
public class BaseEntry<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ִ��״̬(0:ʧ�� 1:�ɹ�)
	 */
	private int status;
	/**
	 * ������Ϣ
	 */
	private String msg;
	/**
	 * ��������
	 */
	private T data;
	/**
	 * ����������
	 */
	private int RecordCount;
	/**
	 * ������ҳ��
	 */
	private int PageCount;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getRecordCount() {
		return RecordCount;
	}
	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
	public int getPageCount() {
		return PageCount;
	}
	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}
	@Override
	public String toString() {
		return "BaseEntry [status=" + status + ", msg=" + msg + ", data="
				+ data + ", RecordCount=" + RecordCount + ", PageCount="
				+ PageCount + "]";
	}
	
	
}
