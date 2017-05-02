package com.heshun.retrofitrxjava.entity.stable;

/**
 * 原始数据
 * @param <T>
 * @param <E>
 */
public class DefaultResult<E, T> extends Data<E,T>{
	HttpResult<E, T> etHttpResult;

	public HttpResult<E, T> getEtHttpResult() {
		return etHttpResult;
	}

	public void setEtHttpResult(HttpResult<E, T> etHttpResult) {
		this.etHttpResult = etHttpResult;
	}

	@Override
	public String toString() {
		return "DefaultResult{" +
				"etHttpResult=" + etHttpResult +
				'}';
	}
}