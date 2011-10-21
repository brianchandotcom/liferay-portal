/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.maven.plugins;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenCommandLineBuilder;

/**
 * Builds Liferay Service Builder services.
 *
 * @author Mika Koivisto
 * @author Thiago Moreira
 * @goal   build-service
 * @phase  generate-sources
 */
public class ServiceBuilderMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException {
		try {
			initClassLoader();

			doExecute();
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	protected void copyServicePropertiesFile() throws Exception {
		File servicePropertiesFile = new File(
			resourcesDir, "service.properties");

		if (servicePropertiesFile.exists()) {
			FileUtil.copyFile(
				servicePropertiesFile, new File(implDir, "service.properties"));
		}
	}

	protected void doExecute() throws Exception {
		File inputFile = new File(serviceFileName);

		if (!inputFile.exists()) {
			getLog().warn(inputFile.getAbsolutePath() + " does not exist");

			return;
		}

		getLog().info("Building from " + serviceFileName);

		PropsUtil.set("spring.configs", "META-INF/service-builder-spring.xml");
		PropsUtil.set(
			PropsKeys.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES, "false");

		InitUtil.initWithSpring();

		copyServicePropertiesFile();

		FileUtil.mkdirs(sqlDir);

		File tempServiceFile = null;

		if (pluginType.equals("ext")) {
			pluginName = null;
			springBaseFileName = null;
			springDynamicDataSourceFileName = null;
			springHibernateFileName = null;
			springInfrastructureFileName = null;
			springShardDataSourceFileName = null;

			if (serviceFileName.contains("/main/resources/")) {
				File serviceFile = new File(serviceFileName);

				tempServiceFile = new File(
					StringUtil.replace(
						serviceFileName, "/main/resources/", "/main/java/"));

				FileUtil.copyFile(serviceFile, tempServiceFile);
			}
		}

		new ServiceBuilder(
			serviceFileName, hbmFileName, ormFileName, modelHintsFileName,
			springFileName, springBaseFileName, null,
			springDynamicDataSourceFileName, springHibernateFileName,
			springInfrastructureFileName, springShardDataSourceFileName,
			apiDir, implDir, jsonFileName, remotingFileName, sqlDir,
			sqlFileName, sqlIndexesFileName, sqlIndexesPropertiesFileName,
			sqlSequencesFileName, autoNamespaceTables, beanLocatorUtil,
			propsUtil, pluginName, null);

		if (tempServiceFile != null) {
			FileUtil.delete(tempServiceFile);
		}

		moveServicePropertiesFile();

		invokeDependencyBuild();
	}

	protected void initClassLoader() throws Exception {
		synchronized (ServiceBuilderMojo.class) {
			Class<?> clazz = getClass();

			URLClassLoader classLoader = (URLClassLoader)clazz.getClassLoader();

			Method method = URLClassLoader.class.getDeclaredMethod(
				"addURL", URL.class);

			method.setAccessible(true);

			for (Object object : project.getCompileClasspathElements()) {
				String path = (String)object;

				File file = new File(path);

				URI uri = file.toURI();

				method.invoke(classLoader, uri.toURL());
			}
		}
	}

	protected void invokeDependencyBuild() throws Exception {
		if (!postBuildDependencyModules) {
			return;
		}

		List<Dependency> dependencies = new ArrayList<Dependency>();

		MavenProject parentProject = project.getParent();

		if (parentProject == null) {
			return;
		}

		String groupId = project.getGroupId();

		List<String> modules = parentProject.getModules();

		List<String> reactorIncludes = new ArrayList<String>();

		for (Object dependencyObj : project.getDependencies()) {
			Dependency dependency = (Dependency)dependencyObj;

			if (groupId.equals(dependency.getGroupId()) &&
				modules.contains(dependency.getArtifactId())) {

				reactorIncludes.add(dependency.getArtifactId() + "/pom.xml");
			}
		}

		if (reactorIncludes.isEmpty()) {
			return;
		}

		InvocationRequest invocationRequest = new DefaultInvocationRequest();

		invocationRequest.activateReactor(
			reactorIncludes.toArray(new String[0]), null);

		invocationRequest.setBaseDirectory(parentProject.getBasedir());

		if (postBuildGoals == null) {
			postBuildGoals = new ArrayList<String>();

			postBuildGoals.add("install");
		}

		invocationRequest.setGoals(postBuildGoals);
		invocationRequest.setRecursive(false);

		MavenCommandLineBuilder mavenCommandLineBuilder =
			new MavenCommandLineBuilder();

		getLog().info(
			"Executing " + mavenCommandLineBuilder.build(invocationRequest));

		InvocationResult invocationResult = invoker.execute(invocationRequest);

		if (invocationResult.getExecutionException() != null) {
			throw invocationResult.getExecutionException();
		}
		else if (invocationResult.getExitCode() != 0) {
			throw new MojoExecutionException(
				"Exit code " + invocationResult.getExitCode());
		}
	}

	protected void moveServicePropertiesFile() {
		File servicePropertiesFile = new File(implDir, "service.properties");

		if (servicePropertiesFile.exists()) {
			FileUtil.move(
				servicePropertiesFile,
				new File(resourcesDir, "service.properties"));
		}
	}

	/**
	 * @parameter default-value="${basedir}/src/main/java"
	 * @required
	 */
	private String apiDir;

	/**
	 * @parameter default-value="true"
	 * @required
	 */
	private boolean autoNamespaceTables;

	/**
	 * @parameter default-value="com.liferay.util.bean.PortletBeanLocatorUtil"
	 * @required
	 */
	private String beanLocatorUtil;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-hbm.xml"
	 * @required
	 */
	private String hbmFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/java"
	 * @required
	 */
	private String implDir;

	/**
	 * @component
	 */
	private Invoker invoker;

	/**
	 * @parameter default-value="${basedir}/src/main/webapp/html/js/liferay/service.js"
	 * @required
	 */
	private String jsonFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-model-hints.xml"
	 * @required
	 */
	private String modelHintsFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-orm.xml"
	 * @required
	 */
	private String ormFileName;

	/**
	 * @parameter expression="${project.artifactId}"
	 * @required
	 */
	private String pluginName;

	/**
	 * @parameter default-value="portlet" expression="${pluginType}"
	 * @required
	 */
	private String pluginType;

	/**
	 * @parameter default-value="true" expression="${postBuildDependencyModules}"
	 * @required
	 */
	private boolean postBuildDependencyModules;

	/**
	 * @parameter
	 */
	private List<String> postBuildGoals;

	/**
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * @parameter default-value="com.liferay.util.service.ServiceProps"
	 * @required
	 */
	private String propsUtil;

	/**
	 * @parameter default-value=""
	 */
	private String remotingFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources"
	 * @required
	 */
	private String resourcesDir;

	/**
	 * @parameter default-value="${basedir}/src/main/webapp/WEB-INF/service.xml" expression="${serviceFileName}"
	 * @required
	 */
	private String serviceFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/base-spring.xml"
	 * @required
	 */
	private String springBaseFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/dynamic-data-source-spring.xml"
	 * @required
	 */
	private String springDynamicDataSourceFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/portlet-spring.xml"
	 * @required
	 */
	private String springFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/hibernate-spring.xml"
	 * @required
	 */
	private String springHibernateFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/infrastructure-spring.xml"
	 * @required
	 */
	private String springInfrastructureFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/resources/META-INF/shard-data-source-spring.xml"
	 * @required
	 */
	private String springShardDataSourceFileName;

	/**
	 * @parameter default-value="${basedir}/src/main/webapp/WEB-INF/sql"
	 * @required
	 */
	private String sqlDir;

	/**
	 * @parameter default-value="tables.sql"
	 * @required
	 */
	private String sqlFileName;

	/**
	 * @parameter default-value="indexes.sql"
	 * @required
	 */
	private String sqlIndexesFileName;

	/**
	 * @parameter default-value="indexes.properties"
	 * @required
	 */
	private String sqlIndexesPropertiesFileName;

	/**
	 * @parameter default-value="sequences.sql"
	 * @required
	 */
	private String sqlSequencesFileName;

}