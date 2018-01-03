# DB Upgrade

Some quick tips are listed below for the database upgrade process:

- Modify the `portal-ext.properties` file with your custom settings so that the
upgrade tool can connect your database. All Liferay servers must be turned off
when performing an upgrade.

- Put any required JDBC drivers into the `lib` directory. JAR files in that
directory are available to the upgrade tool.

- To run the upgrade tool in a UNIX environment, execute `run.sh`.

- To run the upgrade tool in a Windows environment, use Ant and execute the
command `ant upgrade`. Please refer to Ant's documentation on how to setup Ant
for your environment.

For more information on upgrades, see Liferay's
[Upgrade documentation](https://dev.liferay.com/discover/deployment/-/knowledge_base/7-0/upgrading-to-liferay-7).