package com.heshun.retrofitrxjava.entity.pojo;

/**
 * author：Jics
 * 2017/5/2 10:17
 */
public class UpdataVersion {
	private int versionNo;

	private String versionName;

	private String loadAddress;

	private String sign;

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public int getVersionNo() {
		return this.versionNo;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setLoadAddress(String loadAddress) {
		this.loadAddress = loadAddress;
	}

	public String getLoadAddress() {
		return this.loadAddress;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return this.sign;
	}

	@Override
	public String toString() {
		return "UpdataVersion{" +
				"versionNo=" + versionNo +
				", versionName='" + versionName + '\'' +
				", loadAddress='" + loadAddress + '\'' +
				", sign='" + sign + '\'' +
				'}';
	}
}
