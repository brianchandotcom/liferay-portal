# Overwriting LPKG Files

To overwrite a JAR or WAR from any LPKG other than the Static LPKG, first create a folder named "overwritten" in the `osgi/marketplace` folder if it doesn't already exist. Drop the the JAR into this folder, making sure its name is the same as a JAR in the original LPKG minus the version info (e.g., if you want to overwrite `com.liferay.amazon.rankings.web-1.0.5.jar` from "Liferay CE Amazon Rankings.lpkg", you would put a JAR named `com.liferay.amazon.rankings.web.jar` into the `osgi/marketplace/overwritten` folder).

Overwriting JARs from the Static LPKG works the same way, but overwriting JARs go into the `osgi/static` folder instead (e.g., if you want to overwrite `com.liferay.portal.profile-1.0.3.jar`, you would put a JAR named `com.liferay.portal.profile.jar into the osgi/static` folder).

To undo these changes, simply delete the overwriting JAR and the portal will use the original LPKG JAR on next startup.

Note that adding and removing JARs must be done while the portal is shut down. Changes will take place the next time the portal is started.