package com.heshun.retrofitrxjava.entity.stable;

/**
 *  "data": {
		 "head": { },
 		"body": [{},{},{} ]
 	}
 * @param <T> head泛型
 * @param <E> body泛型
 */
public class Data<T, E> {
	private T head;
	private E body;//body字段多数情况是某种entity 的集合

	public void setHead(T head) {
		this.head = head;
	}

	public T getHead() {
		return this.head;
	}

	public void setBody(E body) {
		this.body = body;
	}

	public E getBody() {
		return this.body;
	}

}