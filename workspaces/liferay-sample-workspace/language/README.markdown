To import translations into Liferay's Language Override tool, place them into a `[Workspace Root]/language/Language.properties` file using `key=value` syntax and run `../gradlew buildLang` from the `language` folder.

Deploy the translations by running `../gradlew deploy`. Translations with _(Automatic Copy)_ at the end are not imported into Liferay.