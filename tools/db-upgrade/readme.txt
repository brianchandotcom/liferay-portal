Modify portal-ext.properties with your custom settings so that the upgrade tool
can connect your database. All Liferay servers must be turned off when
performing an upgrade.

Put any required JDBC drivers into the lib directory. JAR files in that
directory will be available to the upgrade tool.

To run the upgrade tool in a UNIX environment, execute "run.sh".

To run the upgrade tool in a Windows environment, use Ant and execute the
command "ant upgrade". Please refer to the Ant documentation on how to setup Ant
for your environment.