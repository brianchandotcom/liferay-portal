Create build.<username>.properties to override repository path and id.

run ant install to install artifacts to local maven repository.

run ant deploy to install artifacts to a remote maven repository. If 
you need to provide credentials to your repository add them into 
<USER_HOME>/.m2/settings.xml

Below is a sample settings.xml

<?xml version="1.0" encoding="UTF-8"?>
<settings>
    <servers>
        <server>
            <id>liferay</id>
            <username>admin</username>
            <password>admin123</password>
        </server>
    </servers>
</settings>