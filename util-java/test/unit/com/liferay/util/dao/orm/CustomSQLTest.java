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
		Field paclField = ReflectionUtil.getDeclaredField(
			DataAccess.class, "_pacl");

		_pacl = (DataAccess.PACL)paclField.get(null);

		paclField.set(
			null,
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {DataAccess.PACL.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						String methodName = method.getName();

						if (!methodName.equals("getDataSource")) {
							return "test";
						}

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

				}));

		Field portalField = ReflectionUtil.getDeclaredField(
			PortalUtil.class, "_portal");

		_portal = (Portal)portalField.get(null);

		portalField.set(
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

		Field propsField = ReflectionUtil.getDeclaredField(
			PropsUtil.class, "_props");

		_props = (Props)propsField.get(null);

		propsField.set(
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
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		Field paclField = ReflectionUtil.getDeclaredField(
			DataAccess.class, "_pacl");

		paclField.set(null, _pacl);

		Field portalField = ReflectionUtil.getDeclaredField(
			PortalUtil.class, "_portal");

		portalField.set(null, _portal);

		Field propsField = ReflectionUtil.getDeclaredField(
			PropsUtil.class, "_props");

		propsField.set(null, _props);
	}

	@Before
	public void setUp() throws Exception {
		_customSQL = new TestCustomSQL();
	}

	@Test
	public void testGetAnyStatus() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testSQL("(  -1 = ?) ");
	}

	@Test
	public void testGetAnyStatusIncludeOwner() {
		_queryDefinition.setIncludeOwner(true);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testSQL("((userId = ? AND status != ?)  OR  -1 = ?) ");
	}

	@Test
	public void testGetAnyStatusNotIncludeOwner() {
		_queryDefinition.setIncludeOwner(false);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testSQL("(userId = ?  AND  -1 = ?) ");
	}

	@Test
	public void testGetExcludeStatus() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testSQL("(  status != ?) ");
	}

	@Test
	public void testGetExcludeStatusIncludeOwner() {
		_queryDefinition.setIncludeOwner(true);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testSQL("((userId = ? AND status != ?)  OR  status != ?) ");
	}

	@Test
	public void testGetExcludeStatusNotIncludeOwner() {
		_queryDefinition.setIncludeOwner(false);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testSQL("(userId = ?  AND  status != ?) ");
	}

	@Test
	public void testGetIncludeOwner() {
		_queryDefinition.setIncludeOwner(true);
		_queryDefinition.setOwnerUserId(_USER_ID);

		testSQL("((userId = ? AND status != ?)  OR  -1 = ?) ");
	}

	@Test
	public void testGetIncludeStatus() {
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		testSQL("(  status = ?) ");
	}

	@Test
	public void testGetIncludeStatusIncludeOwner() {
		_queryDefinition.setIncludeOwner(true);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		testSQL("((userId = ? AND status != ?)  OR  status = ?) ");
	}

	@Test
	public void testGetIncludeStatusNotIncludeOwner() {
		_queryDefinition.setIncludeOwner(false);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		testSQL("(userId = ?  AND  status = ?) ");
	}

	@Test
	public void testGetNotIncludeOwner() {
		_queryDefinition.setIncludeOwner(false);
		_queryDefinition.setOwnerUserId(_USER_ID);

		testSQL("(userId = ?  AND  -1 = ?) ");
	}

	@Test
	public void testGetTableName() {
		_queryDefinition.setIncludeOwner(true);
		_queryDefinition.setOwnerUserId(_USER_ID);
		_queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"((Test.userId = ? AND Test.status != ?)  OR  Test.status = ?) ",
			_customSQL.get("test", _queryDefinition, "Test"));
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

	private static final long _USER_ID = 1234L;

	private static DataAccess.PACL _pacl;
	private static Portal _portal;
	private static Props _props;

	private CustomSQL _customSQL;
	private final QueryDefinition<Object> _queryDefinition =
		new QueryDefinition<>();

	private static class TestCustomSQL extends CustomSQL {

		public TestCustomSQL() throws SQLException {
		}

		@Override
		public String get(String id) {
			return _SQL;
		}

	}

}