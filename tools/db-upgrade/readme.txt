Create a portal-ext.properties with your custom settings so that the upgrade tool
can connect your database. All Liferay servers must be turned off when
performing an upgrade.

You can use the following lines as a template for your portal-ext.properties file

jdbc.default.jndi.name=

jdbc.default.driverClassName=com.mysql.jdbc.Driver
jdbc.default.url=jdbc:mysql://localhost/lportal?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false
jdbc.default.username=
jdbc.default.password=

You have to different options in order to run the upgrade process:

Using the shell script (in a UNIX environment) and execute "run.sh". If you want
to run it in debug mode you should run "run.sh debug"

Using Ant. To run the upgrade tool in a Windows or UNIX environment execute the
command "ant upgrade". Please refer to the Ant documentation on how to setup Ant
for your environment.

Both options will use all the jar files available in the portal libraries.
Put any required additional jar file into the lib directory. JAR files in that
directory will be available to the upgrade tool.
