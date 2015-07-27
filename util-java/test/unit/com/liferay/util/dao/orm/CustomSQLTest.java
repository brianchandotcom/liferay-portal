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

package com.liferay.util.dao.orm;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
public class CustomSQLTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Field props = ReflectionUtil.getDeclaredField(
			PropsUtil.class, "_props");

		_props = (Props)props.get(null);

		props.set(
			null,
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {Props.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						return "test";
					}

				}));

		Field portal = ReflectionUtil.getDeclaredField(
			PortalUtil.class, "_portal");

		_portal = (Portal)portal.get(null);

		portal.set(
			null,
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {Portal.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						return "test";
					}

				}));

		Field pacl = ReflectionUtil.getDeclaredField(DataAccess.class, "_pacl");

		_pacl = (DataAccess.PACL)pacl.get(null);

		pacl.set(
			null,
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {DataAccess.PACL.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						String name = method.getName();

						if (name.equals("getDataSource")) {
							return ProxyUtil.newProxyInstance(
								ClassLoader.getSystemClassLoader(),
								new Class<?>[] {DataSource.class},
								new InvocationHandler() {

									@Override
									public Object invoke(
											Object proxy, Method method,
											Object[] args)
										throws Throwable {

										return null;
									}

								});
						}

						return "test";
					}

				}));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		Field props = ReflectionUtil.getDeclaredField(
			PropsUtil.class, "_props");

		props.set(null, _props);

		Field portal = ReflectionUtil.getDeclaredField(
			PortalUtil.class, "_portal");

		portal.set(null, _portal);

		Field pacl = ReflectionUtil.getDeclaredField(DataAccess.class, "_pacl");

		pacl.set(null, _pacl);
	}

	@Before
	public void setUp() throws Exception {
		_queryDefinition = new QueryDefinition<>();
		_customSQL = new TestCustomSQL();
	}

	@Test
	public void testGetAnyStatus() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testSQL("(  -1 = ?) ");
	}

	@Test
	public void testGetAnyStatusIncludeOwner() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(true);

		testSQL("((userId = ? AND status != ?)  OR  -1 = ?) ");
	}

	@Test
	public void testGetAnyStatusNotIncludeOwner() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(false);

		testSQL("(userId = ?  AND  -1 = ?) ");
	}

	@Test
	public void testGetExcludeStatus() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testSQL("(  status != ?) ");
	}

	@Test
	public void testGetExcludeStatusIncludeOwner() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(true);

		testSQL("((userId = ? AND status != ?)  OR  status != ?) ");
	}

	@Test
	public void testGetExcludeStatusNotIncludeOwner() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(false);

		testSQL("(userId = ?  AND  status != ?) ");
	}

	@Test
	public void testGetIncludeOwner() {
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(true);

		testSQL("((userId = ? AND status != ?)  OR  -1 = ?) ");
	}

	@Test
	public void testGetIncludeStatus() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		testSQL("(  status = ?) ");
	}

	@Test
	public void testGetIncludeStatusIncludeOwner() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(true);

		testSQL("((userId = ? AND status != ?)  OR  status = ?) ");
	}

	@Test
	public void testGetIncludeStatusNotIncludeOwner() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(false);

		testSQL("(userId = ?  AND  status = ?) ");
	}

	@Test
	public void testGetNotIncludeOwner() {
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(false);

		testSQL("(userId = ?  AND  -1 = ?) ");
	}

	@Test
	public void testGetTableName() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setIncludeOwner(true);

		String expected =
			"((TestModel.userId = ? AND TestModel.status != ?)  OR  " +
				"TestModel.status = ?) ";

		Assert.assertEquals(
			expected, _customSQL.get("test", _queryDefinition, _TABLE_NAME));
	}

	@Test
	public void testGetWithEmptyQueryDefinition() {
		testSQL("(  -1 = ?) ");
	}

	protected void testSQL(String expected) {
		Assert.assertEquals(expected, _customSQL.get("test", _queryDefinition));
	}

	private static final String _SQL =
		"([$OWNER_USER_ID$] [$OWNER_USER_ID_AND_OR_CONNECTOR$] [$STATUS$]) ";

	private static final String _TABLE_NAME = "TestModel";

	private static final long _USER_ID = 1234L;

	private static DataAccess.PACL _pacl;
	private static Portal _portal;
	private static Props _props;

	private CustomSQL _customSQL;
	private QueryDefinition<Object> _queryDefinition;

	private class TestCustomSQL extends CustomSQL {

		public TestCustomSQL() throws SQLException {
		}

		@Override
		public String get(String id) {
			return _SQL;
		}

	}

}