To import translations into Liferay's Language Override tool, place them into a `[Workspace Root]/language/lang/Language.properties` file using `key=value` syntax and run `../gradlew deploy` from the `language` folder.

To generate translations for other locales, use Liferay's Language Builder tool. Run `../gradlew buildLang` from the `language` directory. This task creates locale-specific files (e.g., `Language_pt_BR.properties`) with automatic copies of the keys and values from the `Language.properties` file. You can then translate the values and deploy the translations. The `buildLang` task runs automatically when you deploy the translations, but values with _(Automatic Copy)_ at the end are not used in Liferay. You must include translated properties in the generated files for them to be effective.

Related documentation:

* [Generating Translations Automatically](https://learn.liferay.com/w/dxp/liferay-development/liferay-internals/extending-liferay/customizing-localization/generating-translations-automatically)
* [Changing Translations with Language Override](https://learn.liferay.com/w/dxp/system-administration/configuring-liferay/changing-translations-with-language-override)