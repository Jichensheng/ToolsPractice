package com.heshun.retrofitrxjava.entity;

/**
 * author：Jics
 * 2017/4/28 16:38
 */
public class Order {
	private int orderId;

	private String orderNumber;

	private String createDate;

	private int orderMoney;

	private String stationName;

	private String photoPath;

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setOrderMoney(int orderMoney) {
		this.orderMoney = orderMoney;
	}

	public int getOrderMoney() {
		return this.orderMoney;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", orderNumber='" + orderNumber + '\'' +
				", createDate='" + createDate + '\'' +
				", orderMoney=" + orderMoney +
				", stationName='" + stationName + '\'' +
				", photoPath='" + photoPath + '\'' +
				'}';
	}
}
