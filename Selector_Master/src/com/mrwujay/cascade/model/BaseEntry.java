package com.mrwujay.cascade.model;

import java.io.Serializable;

/**
 * 服务端返回的基本实体类
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
	 * 执行状态(0:失败 1:成功)
	 */
	private int status;
	/**
	 * 错误信息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private T data;
	/**
	 * 数据总条数
	 */
	private int RecordCount;
	/**
	 * 数据总页数
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
