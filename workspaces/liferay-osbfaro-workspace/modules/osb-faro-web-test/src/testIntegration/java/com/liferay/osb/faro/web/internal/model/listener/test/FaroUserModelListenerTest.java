/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.constants.FaroUserConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.service.FaroUserLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-114293"))
@RunWith(Arquillian.class)
public class FaroUserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnAfterCreate() throws Exception {
		FaroProject faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY, StringPool.BLANK,
			StringPool.UTC, null);

		Assert.assertTrue(
			_hasWorkspaceAdministratorRole(
				_addFaroUser(
					faroProject.getGroupId(), RoleConstants.SITE_ADMINISTRATOR,
					FaroUserConstants.STATUS_APPROVED)));
		Assert.assertFalse(
			_hasWorkspaceAdministratorRole(
				_addFaroUser(
					faroProject.getGroupId(), RoleConstants.SITE_MEMBER,
					FaroUserConstants.STATUS_APPROVED)));
		Assert.assertTrue(
			_hasWorkspaceAdministratorRole(
				_addFaroUser(
					faroProject.getGroupId(), RoleConstants.SITE_OWNER,
					FaroUserConstants.STATUS_APPROVED)));
		Assert.assertFalse(
			_hasWorkspaceAdministratorRole(
				_addFaroUser(
					faroProject.getGroupId(), RoleConstants.SITE_OWNER,
					FaroUserConstants.STATUS_PENDING)));
	}

	@FeatureFlags(
		featureFlags = @FeatureFlag(enable = false, value = "LPD-114293")
	)
	@Test
	public void testOnAfterCreateWithoutFeatureFlag() throws Exception {
		FaroProject faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY, StringPool.BLANK,
			StringPool.UTC, null);

		Assert.assertFalse(
			_hasWorkspaceAdministratorRole(
				_addFaroUser(
					faroProject.getGroupId(), RoleConstants.SITE_OWNER,
					FaroUserConstants.STATUS_APPROVED)));
	}

	private FaroUser _addFaroUser(long groupId, String roleName, int status)
		throws Exception {

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), roleName);
		User user = UserTestUtil.addUser();

		return _faroUserLocalService.addFaroUser(
			TestPropsValues.getUserId(), groupId, user.getUserId(),
			role.getRoleId(), user.getEmailAddress(), status, false);
	}

	private boolean _hasWorkspaceAdministratorRole(FaroUser faroUser)
		throws Exception {

		return _userGroupRoleLocalService.hasUserGroupRole(
			faroUser.getLiveUserId(), faroUser.getGroupId(),
			"Workspace Administrator");
	}

	@Inject
	private FaroProjectLocalService _faroProjectLocalService;

	@Inject
	private FaroUserLocalService _faroUserLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}