package com.heshun.retrofitrxjava.entity.pojo;

public class CommonUser {
	public String address = "";
	public int area;
	public int city;
	public String decInfo;
	public String headImage = "";
	public String mobile = "";
	public int money;
	public String nickName = "";
	public int powerPackage;
	public int province;
	public String pwd;
	public String regIp;
	public String sex = "男";
	public String token;
	public String status;
	public boolean isRealName;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getDecInfo() {
		return decInfo;
	}

	public void setDecInfo(String decInfo) {
		this.decInfo = decInfo;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getPowerPackage() {
		return powerPackage;
	}

	public void setPowerPackage(int powerPackage) {
		this.powerPackage = powerPackage;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRealName() {
		return isRealName;
	}

	public void setRealName(boolean realName) {
		isRealName = realName;
	}

	@Override
	public boolean equals(Object o) {

		CommonUser u = (CommonUser) o;
		return this.address.equals(u.address) && this.sex.equals(u.sex) && this.nickName.equals(u.nickName);
	}

	@Override
	public String toString() {
		return "CommonUser{" +
				"address='" + address + '\'' +
				", area=" + area +
				", city=" + city +
				", decInfo='" + decInfo + '\'' +
				", headImage='" + headImage + '\'' +
				", mobile='" + mobile + '\'' +
				", money=" + money +
				", nickName='" + nickName + '\'' +
				", powerPackage=" + powerPackage +
				", province=" + province +
				", pwd='" + pwd + '\'' +
				", regIp='" + regIp + '\'' +
				", sex='" + sex + '\'' +
				", token='" + token + '\'' +
				", status='" + status + '\'' +
				", isRealName=" + isRealName +
				'}';
	}
}