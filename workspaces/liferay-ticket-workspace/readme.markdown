# Liferay Ticket Workspace

## Deployment

First we have to enable the below feature flag:

    # Enabling attachments for object entries
    feature.flag.LPS-174455 = true

Then install the below client extensions in the exact order below :

    1.liferay-ticket-batch-list-type-definition
    2.liferay-ticket-batch-object-definition
    3.liferay-ticket-batch-object-relationship
    4.liferay-ticket-batch-object-entry
    5.liferay-ticket-custom-element

For the custom element to work correctly, we have to deploy the related stylebook which can be found in **Workspaces** -> **liferay-tryitnow-workspace** -> **client-extensions** -> **liferay-tryitnow-site-initializer** -> **site-initializer** -> **style-books** -> **tryitnow**

After deploying the stylebook, we have to apply it to the page which is hosting the custom element and make sure we are using the **Dialect** theme.

## Generating Tickets

To generate random tickets quickly, we can use the **liferay-ticket-custom-element** by navigating to the **Tickets App** page, we can also use the default object widget under **Control Panel** -> **Objects** -> **J3Y7 Tickets**.