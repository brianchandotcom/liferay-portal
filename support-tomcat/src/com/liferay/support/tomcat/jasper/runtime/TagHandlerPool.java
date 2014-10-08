/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.support.tomcat.jasper.runtime;

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.jasper.Constants;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-19130.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class TagHandlerPool extends org.apache.jasper.runtime.TagHandlerPool {

	@Override
	@SuppressWarnings("rawtypes")
	public Tag get(Class tagClass) throws JspException {
		Tag tag = _tags.poll();

		if (tag == null) {
			try {
				tag = (Tag)tagClass.newInstance();
			}
			catch (Exception e) {
				throw new JspException(e);
			}
		}

		return tag;
	}

	@Override
	public void release() {
		Tag tag = null;

		while ((tag = _tags.poll()) != null) {
			tag.release();
		}
	}

	@Override
	public void reuse(Tag tag) {
		if (_counter.get() < _maxSize) {
			_counter.getAndIncrement();

			_tags.offer(tag);
		}
		else {
			tag.release();
		}
	}

	@Override
	protected void init(ServletConfig config) {
		_maxSize = GetterUtil.getInteger(
			getOption(config, OPTION_MAXSIZE, null), Constants.MAX_POOL_SIZE);
	}

	private final AtomicInteger _counter = new AtomicInteger();
	private int _maxSize;
	private final Queue<Tag> _tags = new ConcurrentLinkedQueue<Tag>();

}