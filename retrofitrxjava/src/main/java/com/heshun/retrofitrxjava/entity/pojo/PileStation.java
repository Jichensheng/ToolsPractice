package com.heshun.retrofitrxjava.entity.pojo;

/**
 * author：Jics
 * 2017/5/2 14:24
 */
public class PileStation {
	private int orgId;

	private String name;

	private int id;

	private String stationCode;

	private double lon;

	private double lat;

	private String photoPath;

	private String address;

	private String contactTel;

	private int starCount;

	private int dcSum;

	private int dcUseSum;

	private int dcNouseSum;

	private int acSum;

	private int acUseSum;

	private int acNouseSum;

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getOrgId() {
		return this.orgId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLon() {
		return this.lon;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return this.lat;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactTel() {
		return this.contactTel;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public int getStarCount() {
		return this.starCount;
	}

	public void setDcSum(int dcSum) {
		this.dcSum = dcSum;
	}

	public int getDcSum() {
		return this.dcSum;
	}

	public void setDcUseSum(int dcUseSum) {
		this.dcUseSum = dcUseSum;
	}

	public int getDcUseSum() {
		return this.dcUseSum;
	}

	public void setDcNouseSum(int dcNouseSum) {
		this.dcNouseSum = dcNouseSum;
	}

	public int getDcNouseSum() {
		return this.dcNouseSum;
	}

	public void setAcSum(int acSum) {
		this.acSum = acSum;
	}

	public int getAcSum() {
		return this.acSum;
	}

	public void setAcUseSum(int acUseSum) {
		this.acUseSum = acUseSum;
	}

	public int getAcUseSum() {
		return this.acUseSum;
	}

	public void setAcNouseSum(int acNouseSum) {
		this.acNouseSum = acNouseSum;
	}

	public int getAcNouseSum() {
		return this.acNouseSum;
	}

	@Override
	public String toString() {
		return "PileStation{" +
				"orgId=" + orgId +
				", name='" + name + '\'' +
				", id=" + id +
				", stationCode='" + stationCode + '\'' +
				", lon=" + lon +
				", lat=" + lat +
				", photoPath='" + photoPath + '\'' +
				", address='" + address + '\'' +
				", contactTel='" + contactTel + '\'' +
				", starCount=" + starCount +
				", dcSum=" + dcSum +
				", dcUseSum=" + dcUseSum +
				", dcNouseSum=" + dcNouseSum +
				", acSum=" + acSum +
				", acUseSum=" + acUseSum +
				", acNouseSum=" + acNouseSum +
				'}';
	}
}
