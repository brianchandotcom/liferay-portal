# Overriding LPKG Files

Liferay provides a way to update modules without modifying the original LPKG
file they're packaged in. You can do this by overriding the LPKG file.

To override a JAR or WAR from any LPKG other than the Static LPKG, first create
a folder named `override` in the Liferay instance's `osgi/marketplace` folder.
Drop the updated JAR into this folder, making sure its name is the same as the
JAR in the original LPKG, minus the version info. For example, if you want to
override the `com.liferay.amazon.rankings.web-1.0.5.jar` from the `Liferay CE
Amazon Rankings.lpkg`, you would insert a JAR named
`com.liferay.amazon.rankings.web.jar` into the `osgi/marketplace/override`
folder.

Overriding JARs from the Static LPKG works the same way, but the updated JARs go
into the `osgi/static` folder instead. For example, if you want to override the
`com.liferay.portal.profile-1.0.3.jar`, you would insert a JAR named
`com.liferay.portal.profile.jar `into the `osgi/static` folder.

To undo these changes, delete the overriding JAR; portal will use the original
LPKG JAR on its next startup. Note that adding and removing JARs must be done
while the portal is shut down. Changes take effect the next time the portal is
started.