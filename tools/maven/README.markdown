# Maven

Some quick tips are listed below for using Maven with Liferay products:

Create a `build.<username>.properties` to override the repository path and ID.

Run `ant install` to install artifacts to a local Maven repository.

Run `ant deploy` to install artifacts to a remote Maven repository. If you need
to provide credentials to your repository, add them in the
`${USER_HOME}/.m2/settings.xml` file.

Below is a sample `settings.xml` file:

```xml
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
```

For more information on using Maven with Liferay, see Liferay's
[Maven documentation](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-0/maven).