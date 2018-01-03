Create build.<username>.properties to override repository path and id.

Run "ant install" to install artifacts to a local Maven repository.

Run "ant deploy" to install artifacts to a remote Maven repository. If  you need
to provide credentials to your repository, add them into
${USER_HOME}/.m2/settings.xml.

Below is a sample settings.xml

<?xml version="1.0"?>

<settings>
    <servers>
        <server>
            <id>liferay</id>
            <username>admin</username>
            <password>password</password>
        </server>
    </servers>
</settings>