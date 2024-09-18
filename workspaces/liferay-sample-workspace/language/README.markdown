To add or override translations, place your entries inside the `lang/Language.properties` file. After making changes, run the command `../gradlew buildLang` to generate the default translation files. This command will create copies of all keys and values from your `Language.properties` file into the generated files.

Every time you modify the `Language.properties` file, you should run `../gradlew buildLang` to update the translation files accordingly. Any changes you make directly in the generated translation files will be preserved.

### Important

The translations import will fail if the developer feature flag `LPD-27222` is not enabled.