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

package com.mulesoft.tcat.script;

import com.mulesoft.common.agent.file.FileService;
import com.mulesoft.common.remoting.RemoteContext;
import com.mulesoft.tcat.DeployerFactory;
import com.mulesoft.tcat.DeploymentVersion;
import com.mulesoft.tcat.Server;

import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployer.DeployableMonitor;
import org.codehaus.cargo.container.deployer.Deployer;
import org.codehaus.cargo.container.deployer.DeployerType;
import org.codehaus.cargo.util.log.Logger;

import org.springframework.context.ApplicationContext;

/**
 * This script replaces the Tcat Deployer and DeployerFactory with the
 * customized LiferayDeployer and LiferayDeployerFactory.
 *
 * <p>
 * Liferay's WARs require some additional preprocessing before they can be
 * properly placed into Tomcat's webapps directory. This script intercepts
 * Liferay WAR files and places them into Liferay's hot deploy directory
 * located at ${CATALINA_HOME}/deploy. Liferay will then complete the
 * deployment.
 * </p>
 *
 * @author Michael C. Han
 */
public class LiferayDeployer implements Deployer {

	public void deploy(Deployable deployable) {
		String deployableFileName = deployable.getFile();

		if (deployableFileName.contains("-ext") ||
			deployableFileName.contains("-hook") ||
			deployableFileName.contains("-layouttpl") ||
			deployableFileName.contains("-portlet") ||
			deployableFileName.contains("-theme") ||
			deployableFileName.contains("-web") ||
			deployableFileName.contains("ROOT.war")) {

			println("Deploying Liferay module: " + deployableFileName);

			RemoteContext.setServerId(_server.id);

			InputStream inputStream = new FileInputStream(deployableFileName);

			File file = new File(deployableFileName);

			String name = file.name;

			name = name.substring(0, name.lastIndexOf(".war") + 4);

			if (deployableFileName.contains("ROOT.war")) {
				_fileService.upload("/webapps/" + name, inputStream);
			}
			else {
				_fileService.upload("/deploy/" + name, inputStream);
			}

			println(
				"Completed deployment Liferay module: " + deployableFileName);
		}
		else {
			_deployer.deploy(deployable);
		}
	}

	public void deploy(
		Deployable deployable, DeployableMonitor deployableMonitor) {

		InputStream inputStream = new FileInputStream(deployable.getFile());

		RemoteContext.setServerId(_server.id);

		_fileService.upload("/deploy", inputStream);
	}

	public Logger getLogger() {
		return _logger;
	}

	public DeployerType getType() {
		return _deployer.getType();
	}

	public void redeploy(Deployable deployable) {
		_deployer.redeploy(deployable);
	}

	public void setLogger(Logger logger) {
		_logger = logger;

		_deployer.setLogger(logger);
	}

	public void start(Deployable deployable) {
		_deployer.start(deployable);
	}

	public void stop(Deployable deployable) {
		_deployer.stop(deployable);
	}

	public void undeploy(Deployable deployable) {
		_deployer.undeploy(deployable);
	}

	public void undeploy(
		Deployable deployable, DeployableMonitor deployableMonitor) {

		_deployer.undeploy(deployable, deployableMonitor);
	}

	private Deployer _deployer;
	private FileService _fileService;
	private Logger _logger;
	private Server _server;

}

/**
 * Listens for deployments which happen and then adds in a context.xml
 * from /Configuration/Contexts/[server-name].xml.
 *
 * @author Michael C. Han
 */
public class LiferayDeployerFactory implements DeployerFactory {

	public LiferayDeployerFactory(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;

		_deployerFactory = _applicationContext.getBean("deployerFactory");
	}

	public Deployer getDeployer(
		DeploymentVersion deploymentVersion, Server server) {

		Deployer deployer = _deployerFactory.getDeployer(
			deploymentVersion, server);

		return new LiferayDeployer(
			_fileService: _applicationContext.getBean("tomcatFileService"),
			_server: server, _deployer: deployer);
	}

	private ApplicationContext _applicationContext;
	private DeployerFactory _deployerFactory;

}

println("Replacing deployer factory with Liferay deployer factory");

def deployerFactory = new LiferayDeployerFactory(applicationContext);

def deploymentActionManager = applicationContext.getBean(
	"deploymentActionManager");

deploymentActionManager.setDeployerFactory(deployerFactory);