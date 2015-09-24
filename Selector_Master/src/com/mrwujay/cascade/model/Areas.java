package com.mrwujay.cascade.model;

import java.io.Serializable;
import java.util.List;

public class Areas implements Serializable{

	private int ChildCnt;
	private List<Areas> childAreas;
	private int id;
	private String area;
	private String postcode;
	private String areacode;
	private int parentID;
	private String parentStr;
	private int Depth;
	private int OrderNum;
	private int Lock4;
	private String TaobaoId;
	private String TaobaoParentId;
	
	
	public int getChildCnt() {
		return ChildCnt;
	}
	public void setChildCnt(int childCnt) {
		ChildCnt = childCnt;
	}
	public List<Areas> getChildAreas() {
		return childAreas;
	}
	public void setChildAreas(List<Areas> childAreas) {
		this.childAreas = childAreas;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getParentStr() {
		return parentStr;
	}
	public void setParentStr(String parentStr) {
		this.parentStr = parentStr;
	}
	public int getDepth() {
		return Depth;
	}
	public void setDepth(int depth) {
		Depth = depth;
	}
	public int getOrderNum() {
		return OrderNum;
	}
	public void setOrderNum(int orderNum) {
		OrderNum = orderNum;
	}
	public int getLock4() {
		return Lock4;
	}
	public void setLock4(int lock4) {
		Lock4 = lock4;
	}
	public String getTaobaoId() {
		return TaobaoId;
	}
	public void setTaobaoId(String taobaoId) {
		TaobaoId = taobaoId;
	}
	public String getTaobaoParentId() {
		return TaobaoParentId;
	}
	public void setTaobaoParentId(String taobaoParentId) {
		TaobaoParentId = taobaoParentId;
	}
	
	
}
