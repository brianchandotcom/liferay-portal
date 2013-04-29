/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficehome.tasks.task;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtoconnection.AddTasksTaskAssignedToConnectionTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtofollower.AddTasksTaskAssignedToFollowerTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtome.AddTasksTaskAssignedToMeTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtomecomment.AddTasksTaskAssignedToMeCommentTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtomeduedate.AddTasksTaskAssignedToMeDueDateTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtomemultiple.AddTasksTaskAssignedToMeMultipleTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtometag.AddTasksTaskAssignedToMeTagTests;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtometaginvalid.AddTasksTaskAssignedToMeTagInvalidTests;
import com.liferay.portalweb.socialofficehome.tasks.task.clicktasksprogressbar100percentcomplete.ClickTasksProgressBar100PercentCompleteTests;
import com.liferay.portalweb.socialofficehome.tasks.task.clicktasksprogressbar60percentcomplete.ClickTasksProgressBar60PercentCompleteTests;
import com.liferay.portalweb.socialofficehome.tasks.task.deletetaskstaskassignedtome.DeleteTasksTaskAssignedToMeTests;
import com.liferay.portalweb.socialofficehome.tasks.task.deletetaskstaskassignedtomecomment.DeleteTasksTaskAssignedToMeCommentTests;
import com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtoconnection.EditTasksTaskAssignedToConnectionTests;
import com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtomecomment.EditTasksTaskAssignedToMeCommentTests;
import com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtomedescription.EditTasksTaskAssignedToMeDescriptionTests;
import com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtomeduedate.EditTasksTaskAssignedToMeDueDateTests;
import com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbyplace.FilterTasksFilterByPlaceTests;
import com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbytags.FilterTasksFilterByTagsTests;
import com.liferay.portalweb.socialofficehome.tasks.task.resolvetaskstaskassignedtome.ResolveTasksTaskAssignedToMeTests;
import com.liferay.portalweb.socialofficehome.tasks.task.sousresolvetaskstaskassignedtoconnection.SOUs_ResolveTasksTaskAssignedToConnectionTests;
import com.liferay.portalweb.socialofficehome.tasks.task.viewtasksassignedtomelink.ViewTasksAssignedToMeLinkTests;
import com.liferay.portalweb.socialofficehome.tasks.task.viewtasksihavecreatedlink.ViewTasksIHaveCreatedLinkTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class TaskTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddTasksTaskAssignedToConnectionTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToFollowerTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToMeTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToMeCommentTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToMeDueDateTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToMeMultipleTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToMeTagTests.suite());
		testSuite.addTest(AddTasksTaskAssignedToMeTagInvalidTests.suite());
		testSuite.addTest(ClickTasksProgressBar60PercentCompleteTests.suite());
		testSuite.addTest(ClickTasksProgressBar100PercentCompleteTests.suite());
		testSuite.addTest(DeleteTasksTaskAssignedToMeTests.suite());
		testSuite.addTest(DeleteTasksTaskAssignedToMeCommentTests.suite());
		testSuite.addTest(EditTasksTaskAssignedToConnectionTests.suite());
		testSuite.addTest(EditTasksTaskAssignedToMeCommentTests.suite());
		testSuite.addTest(EditTasksTaskAssignedToMeDescriptionTests.suite());
		testSuite.addTest(EditTasksTaskAssignedToMeDueDateTests.suite());
		testSuite.addTest(FilterTasksFilterByPlaceTests.suite());
		testSuite.addTest(FilterTasksFilterByTagsTests.suite());
		testSuite.addTest(ResolveTasksTaskAssignedToMeTests.suite());
		testSuite.addTest(
			SOUs_ResolveTasksTaskAssignedToConnectionTests.suite());
		testSuite.addTest(ViewTasksAssignedToMeLinkTests.suite());
		testSuite.addTest(ViewTasksIHaveCreatedLinkTests.suite());

		return testSuite;
	}

}