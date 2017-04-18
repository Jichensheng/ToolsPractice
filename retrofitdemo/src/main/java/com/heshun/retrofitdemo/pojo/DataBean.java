package com.heshun.retrofitdemo.pojo;

public class DataBean {
		/**
		 * versionNo : 9
		 * versionName : 1.0.4
		 * loadAddress : /upload/20160718192032_eSuzhou_V9_1.0.4_20160718.apk
		 * content : 二维码识别率、优雅的崩溃以
		 * sign : 6391bbed0d49eefa5bc70f001be532ef
		 */

		private BodyBean body;

		public BodyBean getBody() {
			return body;
		}

		public void setBody(BodyBean body) {
			this.body = body;
		}

		public static class BodyBean {
			private int versionNo;
			private String versionName;
			private String loadAddress;
			private String content;
			private String sign;

			public int getVersionNo() {
				return versionNo;
			}

			public void setVersionNo(int versionNo) {
				this.versionNo = versionNo;
			}

			public String getVersionName() {
				return versionName;
			}

			public void setVersionName(String versionName) {
				this.versionName = versionName;
			}

			public String getLoadAddress() {
				return loadAddress;
			}

			public void setLoadAddress(String loadAddress) {
				this.loadAddress = loadAddress;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getSign() {
				return sign;
			}

			public void setSign(String sign) {
				this.sign = sign;
			}
		}
	}