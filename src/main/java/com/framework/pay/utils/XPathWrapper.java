/**
 * Copyright (c) 2016-2019 湖南神雀 All rights reserved.
 * <p>
 * https://www.sqqmall.com
 */
package com.framework.pay.utils;

public class XPathWrapper {
	private XPathParser xpath;

	public XPathWrapper(XPathParser xpath) {
		this.xpath = xpath;
	}

	public String get(String key) {
		XNode xNode = this.xpath.evalNode("//" + key);
		if (xNode == null) {
			return null;
		}
		return xNode.body();
	}
}
