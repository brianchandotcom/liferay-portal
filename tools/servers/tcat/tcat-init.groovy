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

import com.mulesoft.tcat.ServerProfileManager;
import com.mulesoft.tcat.util.InstallBuilder;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.mule.galaxy.Item;
import org.mule.galaxy.NotFoundException;
import org.mule.galaxy.Registry;
import org.mule.galaxy.impl.jcr.JcrUtil;
import org.mule.galaxy.script.Script;
import org.mule.galaxy.script.ScriptManager;
import org.mule.galaxy.type.TypeManager;
import org.mule.galaxy.util.IOUtils;

import org.springframework.context.ApplicationContext;

import org.springmodules.jcr.JcrCallback;

/**
 * This tcat-init.groovy script initializes the Tcat admin server by loading
 * server profiles, console scripts, and web applications.
 *
 * <p>
 * This script must be placed into ${CATALINA_HOME}. The Tcat admin server
 * will execute the script on first startup and after successful execution,
 * delete it.
 * </p>
 *
 * <p>
 * The script loads console groovy scripts from
 * ${CATALINA_HOME}/tcat_init/scripts. The scripts will be available in the
 * Tcat console Admin Shell.
 * </p>
 *
 * <p>
 * The script will load profiles from ${CATALINA_HOME}/tcat_init/profiles.
 * Profiles zip files must be named liferay-portal-tcat-profile-${VERSION}.zip.
 * For instance, liferay-portal-tcat-profile-6.1.0.zip. On startup, this script
 * will load the profile into the repository:
 * /Profiles/liferay-portal-${VERSION}.
 * </p>
 *
 * <p>
 * The script will also load web applications from
 * ${CATALINA_HOME}/tcat_init/webapps/${VERSION} where ${VERSION} should be the
 * version of Liferay Portal (e.g. 6_0_10).
 * </p>
 *
 * @author Michael C. Han
 */
class InitializeLiferayDeployment implements JcrCallback {

	public InitializeLiferayDeployment(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;

		_registry = (Registry)_applicationContext.getBean("registry");
		_typeManager = (TypeManager)_applicationContext.getBean("typeManager");
	}

	public Object doInJcr(Session session)
		throws IOException, RepositoryException {

		InstallBuilder installBuilder = new InstallBuilder(_applicationContext);

		// Register the local Tcat agent

		//installBuilder.registerConsoleAgent("TcatServer")

		// Import the Liferay server profile into the Tcat repository

		_loadServerProfiles(installBuilder);

		// Loop through all WAR files and add them to the Tcat repository

		_loadWebapps(installBuilder);

		// Loop through all scripts and add them to Tcat console

		_loadScripts(installBuilder);

		return "Completed Initialization";
	}

	public List<Script> getScripts() {
		return _scripts;
	}

	private Item _getWorkspace(String workspaceName) {
		String[] workspaceNameComponents = workspaceName.split("/");

		String path = "";

		for (String workspaceNameComponent : workspaceNameComponents) {
			if (workspaceNameComponent.equals("")) {
				continue;
			}

			String tempPath = path + "/" + workspaceNameComponent;

			try {
				_registry.getItemByPath(tempPath);
			}
			catch (NotFoundException nfe) {
				def parentItem = _registry.getItemByPath(path);

				parentItem.newItem(
					workspaceNameComponent,
					_typeManager.getTypeByName("Workspace"));
			};

			path = tempPath;
		}

		return _registry.getItemByPath(path);
	}

	private void _loadScripts(InstallBuilder installBuilder) {
		println("Loading Tcat scripts");

		File scriptsDir = new File("tcat_init/scripts");

		ScriptManager scriptManager =
			(ScriptManager)_applicationContext.getBean("scriptManager");

		for (File file : scriptsDir.listFiles()) {
			String scriptName = file.name;

			if (!scriptName.contains(".groovy")) {
				continue;
			}

			println("Loading script " + scriptName);

			String friendlyName = scriptName.substring(
				0, scriptName.indexOf(".groovy"));

			List<Script> scripts = scriptManager.find("name", friendlyName);

			InputStream inputStream = new FileInputStream(file);

			Script script = null;

			if (!scripts.isEmpty()) {
				script = scripts.get(0);
			}
			else {
				script = new Script();

				script.setName(friendlyName);
			}

			script.setRunOnStartup(true);

			script.setScript(IOUtils.toString(inputStream));

			_scripts.add(script);

			scriptManager.save(script);
		}
	}

	private void _loadServerProfiles(InstallBuilder installBuilder) {
		println("Loading Liferay server profiles");

		File profileDir = new File("tcat_init/profiles");

		def serverProfileManager =
			(ServerProfileManager)_applicationContext.getBean(
				"serverProfileManager");

		for (File file : profileDir.listFiles()) {
			String fileName = file.name;

			if (!fileName.contains("tcat-profile-")) {
				continue;
			}

			println("Loading Server Profile: " + file.name);

			int x = fileName.indexOf("tcat-profile-");
			int y = x + "tcat-profile-".length();

			String profileName =
				fileName.substring(0, x) +
					fileName.substring(y, fileName.indexOf(".zip"));

			Item profileWorkspaceItem = _getWorkspace(
				"/Profiles/" + profileName);

			serverProfileManager.importProfile(
				new FileInputStream(file), profileWorkspaceItem);
		}
	}

	private void _loadWebapps(InstallBuilder installBuilder) {
		println("Loading Liferay web applications into repository");

		File webappsDir = new File("tcat_init/webapps");

		for (File versionDir : webappsDir.listFiles()) {
			if (!versionDir.isDirectory()) {
				continue;
			}

			String workspaceName = versionDir.name;
			String workspaceFullPath = "/Applications/Liferay/" + workspaceName;

			println("Loading web applications in " + workspaceName);

			_getWorkspace(workspaceFullPath);

			for (File file : versionDir.listFiles()) {
				String fileName = file.name;

				if (!fileName.contains(".war")) {
					continue;
				}

				println("Loading web application " + fileName);

				String fileVersion = workspaceName;

				int x = fileName.lastIndexOf("-") + 1;
				int y = fileName.indexOf(".war");

				if (x > 1) {
					fileVersion = fileName.substring(x, y)
				}

				installBuilder.addRepositoryFile(
					file.toString(), workspaceFullPath, fileName, fileVersion);
			}
		}
	}

	private ApplicationContext _applicationContext;
	private Registry _registry;
	private List<Script> _scripts = new ArrayList<>();
	private TypeManager _typeManager;

}

def repositoryImport = new InitializeLiferayDeployment(applicationContext);

def sessionFactory = applicationContext.getBean("sessionFactory");

JcrUtil.doInTransaction(sessionFactory, repositoryImport);

def scriptManager = (ScriptManager)applicationContext.getBean("scriptManager");

List<Script> scripts = repositoryImport.getScripts();

for (Script script : scripts) {
	println("Executing script " + script.name);

	scriptManager.execute(script);
}