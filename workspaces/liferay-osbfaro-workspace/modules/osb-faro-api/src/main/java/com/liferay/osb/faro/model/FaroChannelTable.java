/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_FaroChannel&quot; database table.
 *
 * @author Matthew Kong
 * @see FaroChannel
 * @generated
 */
public class FaroChannelTable extends BaseTable<FaroChannelTable> {

	public static final FaroChannelTable INSTANCE = new FaroChannelTable();

	public final Column<FaroChannelTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FaroChannelTable, Long> faroChannelId = createColumn(
		"faroChannelId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FaroChannelTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> createTime = createColumn(
		"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> modifiedTime = createColumn(
		"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, String> channelId = createColumn(
		"channelId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Integer> permissionType =
		createColumn(
			"permissionType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> workspaceGroupId = createColumn(
		"workspaceGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private FaroChannelTable() {
		super("OSBFaro_FaroChannel", FaroChannelTable::new);
	}

}