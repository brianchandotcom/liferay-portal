# Liferay Ticket Workspace

## Deployment

First you have to enable the below feature flag:

    # Enabling attachments for object entries
    feature.flag.LPS-174455 = true

Then install the below client extensions in the exact order below :

    1.liferay-ticket-batch-list-type-definition
    2.liferay-ticket-batch-object-definition
    3.liferay-ticket-batch-object-relationship
    4.liferay-ticket-batch-object-entry
    5.liferay-ticket-custom-element

## Generating Tickets

To generate random tickets quickly, you can use the **liferay-ticket-custom-element** by navigating to the **Tickets App** page, you can also use the default object widget under **Control Panel** -> **Objects** -> **J3Y7 Tickets**.