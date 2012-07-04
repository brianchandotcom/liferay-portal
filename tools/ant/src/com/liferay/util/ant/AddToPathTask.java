/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.util.ant;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Path.PathElement;

/**
 * A use case of this task is to add pathelements from an array. Note it's a
 * good idea to not modify existing or top level path references since changing
 * these may affect the build in unexpected ways. Creating a wrapper as follows
 * is ideal:<br><br>
 *
 * <pre>
 * 		&lt;path id="wrapper.classpath">
 *			&lt;path refid="top.classpath" />
 *		&lt;/path>
 * </pre>
 * <br>
 *
 * Given a comma delimited list of items to be added to the path reference, a
 * for loop can be used to append these to the wrapper:<br><br>
 *
 * <pre>
 * 		&lt;for list="${items}" param="item">
 *			&lt;sequential>
 *				&lt;liferay-addtopath refid="wrapper.classpath">
 *					&lt;pathelement location="@{item}" />
 *				&lt;/liferay-addtopath>
 *			&lt;/sequential>
 *		&lt;/for>
 * </pre>
 *
 * <br><br>
 * @author Raymond Augé
 */
public class AddToPathTask extends Task {

	/**
	 * Set the path reference id of the path instance to be appended. If no path
	 * reference is found with this id, one will be created and added to the
	 * project.
	 */
	public void setRefId(String id) {
		path = (Path)getProject().getReference(id);

		if (path == null) {
			path = new Path(getProject());

			getProject().addReference(id, path);
		}
	}

	/**
	 * This method provides support for nested &lt;path&gt; elements.
	 */
	public void add(Path c) {
		path.add(c);
	}

	/**
	 * This method provides support for nested &lt;fileset&gt; elements.
	 */
	public void add(FileSet c) {
		path.add(c);
	}

	/**
	 * This method provides support for nested &lt;pathelement&gt; elements.
	 */
	public PathElement createPathElement() {
		return path.createPathElement();
	}

	@Override
	public void execute() {
	}

	private Path path;

}