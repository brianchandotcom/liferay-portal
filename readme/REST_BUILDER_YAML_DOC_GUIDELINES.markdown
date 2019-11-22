# REST Builder YAML Description Guidelines

Please follow these guidelines as you define descriptions in REST Builder's
`.yaml` files. These guidelines are based on the
[OpenAPI](https://swagger.io/docs/specification/about/) specification's use of
YAML files.

## Description Format

YAML's `description` section should provide extended information about the API.
They should be written in the [CommonMark](https://commonmark.org/help/)
dialect of Markdown for rich text representation. The most popular uses of
CommonMark formatting for REST Builder's YAML files are outlined below:

- Inline code with backticks: \`null\`
- Links: [Github]\(https://github.com)
- Italics with single asterisks: \*Save\*
- Bold with double asterisks: \*\*Parameter\*\*

All descriptions should be on a single line. If you wish to display the
description as multiple lines after it's generated, you must use HTML `<p>`
tags.

## Style

There are two types of styles a description can follow:

- [Action style](#action-style)
- [Declarative style](#declarative-style)

See their respective sections for more information.

The basic structure for an OpenAPI YAML file is outlined below:

- Metadata
- Servers
- Paths ([example](#paths))
    - Request Bodies
    - Responses
- Input/Output Schemas ([example](#input-output-schemas))
- Authentication

Descriptions assigned directly to one of these sections should follow the
*Action* style. Any properties or parameters included within these sections
should follow the *Declarative* style.

For more information on this basic structure, see
[Swagger's Basic Structure specification](https://swagger.io/docs/specification/basic-structure/).

### Action Style

An action style description should follow these rules:

- Starts with an action word (e.g., *Represents*, *Provides*, *Gets*, etc.).
- Capitalize only first word and all proper nouns.
- Uses standard sentence punctuation (including periods).
- The first sentence should describe the entity clearly. Additional sentences
  (if necessary) should provide additional details.
- Examples:
    - Gets the segment's users.
    - Represents the user account who created/authored the content.

### Declarative Style

A declarative style description should follow these rules:

- Starts with *A*, *An*, or *The*.
- Capitalize only first word and all proper nouns.
- Uses standard sentence punctuation (including periods).
- The first sentence should describe the parameter/property clearly. Additional
  sentences (if necessary) should provide additional details.
- Examples:
    - The user account's full name.
    - A generated URL for the page.

## Generating Java Class Descriptions

The descriptions provided in YAML files are often generated in Java classes. For
example, input/output schema property descriptions are generated as `@Schema`
annotations in Java classes.

To generate the necessary logic provided in the YAML file, run the REST Builder.
Navigate to the module where the updated YAML file resides and run

```bash
./gradlew buildREST
```

## Examples

### Paths

```yaml
paths:
    "/segments/{segmentId}/user-accounts":
        get:
            operationId: getSegmentUserAccountsPage
            description: Gets the segment's users.
            parameters:
                - description: The segment's ID.
                  in: path
                  name: segmentId
                  required: true
                  schema:
                      format: int64
                      type: integer
                - description: The segment's page.
                  in: query
                  name: page
                  schema:
                      type: integer
                - description: The segment's page size.
                  in: query
                  name: pageSize
                  schema:
                      type: integer
            responses:
                200:
                    content:
                        application/json:
                            schema:
                                items:
                                    $ref: "#/components/schemas/SegmentUser"
                                type: array
                    description: ""
            tags: ["SegmentUser"]
```

### Input/Output Schemas

```yaml
components:
    schemas:
        Creator:
            description: Represents the user account who created/authored the content.
            properties:
              id:
                  description: The resource's identifier.
                  format: int64
                  readOnly: true
                  type: integer
              image:
                  description: The relative URL for the user account's image profile.
                  format: uri
                  readOnly: true
                  type: string
              name:
                  description: The user account's full name.
                  readOnly: true
                  type: string
              profileURL:
                  description: The relative URL for the user account's profile.
                  format: uri
                  readOnly: true
                  type: string
```