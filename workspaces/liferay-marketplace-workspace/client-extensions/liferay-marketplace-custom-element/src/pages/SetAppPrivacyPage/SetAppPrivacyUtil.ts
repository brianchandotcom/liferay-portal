import { BaseCheckboxDataCard } from "../../components/CheckboxDataCard/CheckboxDataCard";

import personIcon from '../../assets/icons/person-fill.svg';
import creditCardIcon from '../../assets/icons/credit-card.svg';
import locationIcon from '../../assets/icons/location-on-icon.svg';
import visibilityIcon from '../../assets/icons/visibility-icon.svg';
import personGroupIcon from '../../assets/icons/person-group-icon.svg';
import documentIcon from '../../assets/icons/document-icon.svg';
import scheduleIcon from '../../assets/icons/schedule-icon.svg';
import manageSearchIcon from '../../assets/icons/manage-search-icon.svg';
import accountCircleIcon from '../../assets/icons/account-circle-icon.svg';
import shoppingCartIcon from '../../assets/icons/shopping-cart-icon.svg';
import monitoringIcon from '../../assets/icons/monitoring-icon.svg';
import settingsIcon from '../../assets/icons/settings-icon.svg';
import circleInfoIcon from '../../assets/icons/info-circle-icon.svg';

export const initialCheckboxDataCardsValues: BaseCheckboxDataCard[] = [
    {
        title: "Contact info",
        name: "contactInfo",
        icon: personIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Including first and last name",
                label: "Name",
                name: "name"
            },
            {
                checked: false,
                description: "Including but not limited to a hashed email address",
                label: "Email address",
                name: "emailAddress"
            },
            {
                checked: false,
                description: "Including but not limited to a hashed phone number",
                label: "Phone number",
                name: "phoneNumber"
            },
            {
                checked: false,
                description: "Such as home address, physical address, or mailing address",
                label: "Physical address",
                name: "physicalAddress"
            },
            {
                checked: false,
                description: "Any other info that can be used to contact the user outside the app",
                label: "Other user contact info",
                name: "otherUserContactInfo"
            },
        ]
    },
    {
        title: "Financial Info",
        name: "financialInfo",
        icon: creditCardIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Such as form of payment, payment card number, or bank account number. If your app uses a payment service, the payment information is entered outside your app, and you as the developer never have access to the payment information, it is not collected and does not need to be declared",
                label: "Payment info",
                name: "paymentInfo"
            },
            {
                checked: false,
                description: "Such as credit scrore",
                label: "Credit info",
                name: "creditInfo"
            },
            {
                checked: false,
                description: "Such as salary, income, assets, debts, or any other financial information",
                label: "Other financial info",
                name: "otherFinancialInfo"
            },
        ]
    },
    {
        title: "Location",
        name: "location",
        icon: locationIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Information that describes the location of a user or device with the same or greater resolution as a latitude and longitude with three or more decimal places",
                label: "Precise location",
                name: "preciseLocation"
            },
            {
                checked: false,
                description: "Information that describes the location of a user or device with lower resolution than a latituted and longitude with three or more decimal places, such as location services",
                label: "Coarse location",
                name: "coarseLocation"
            },
        ]
    },
    {
        title: "Sensitive info",
        name: "sensitiveInfo",
        icon: visibilityIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Such as racial or ethnic data, sexual orientation, pregnancy or childbirth information, disability, religious or philosophical beliefs, trade union membership, political opinion, genetic information, or biometric data",
                label: "Sensitive data",
                name: "sensitiveData"
            },
        ]
    },
    {
        title: "Contacts",
        name: "contacts",
        icon: personGroupIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Such as a list of contacts in the user’s phone, address book, or social graph",
                label: "Contacts data",
                name: "contactsData"
            },
        ]
    },
    {
        title: "User Content",
        name: "userContent",
        icon: documentIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Including subject line, sender, recipients, and contentsof the email or message",
                label: "Emails or text messages",
                name: "emailsOrTextMessages"
            },
            {
                checked: false,
                description: "The user’s photos or videos",
                label: "Photos or videos",
                name: "photosOrVideos"
            },
            {
                checked: false,
                description: "The user’s voice or sound recordings",
                label: "Audio data",
                name: "audioData"
            },
            {
                checked: false,
                description: "Such as user-generated content in-game",
                label: "Gameplay content",
                name: "gameplayContent"
            },
            {
                checked: false,
                description: "Data generated by the user during a customer support request",
                label: "Customer support",
                name: "customerSupport"
            },
            {
                checked: false,
                description: "Any other user-generated content",
                label: "Other user content",
                name: "otherUserContent"
            },
        ]
    },
    {
        title: "Browsing History",
        name: "browsingHistory",
        icon: scheduleIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Information about the content the user has viewed that is not part of the app, such as web sites",
                label: "Browsing History",
                name: "browsingHistory"
            },
        ]
    },
    {
        title: "Search History",
        name: "searchHistory",
        icon: manageSearchIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Information about searches perfomed in the app",
                label: "Search history",
                name: "searchHistory"
            },
        ]
    },
    {
        title: "Identifiers",
        name: "identifiers",
        icon: accountCircleIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Such as screen name handle, account ID, assigned user ID, customer number, probalistic identifier, or other user-or account-level ID that can be used to identify a particular user or account",
                label: "User ID",
                name: "userID"
            },
            {
                checked: false,
                description: "Such as the device’s advertising identifier, or other device-level ID",
                label: "Device ID",
                name: "deviceID"
            },
        ]
    },
    {
        title: "Purchases",
        name: "purchases",
        icon: shoppingCartIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "An account’s or individual’s purchases or purchase tendencies",
                label: "User ID",
                name: "userID"
            },
        ]
    },
    {
        title: "Usage Data",
        name: "usageData",
        icon: monitoringIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Such as app launches, taps, clicks information, music listing data, video views, saved place in a game, video, or song, or other information about how the user interface with the app",
                label: "Product Interaction",
                name: "productInteraction"
            },
            {
                checked: false,
                description: "Sucha as information about the advertisements the user has seen",
                label: "Advertising Data",
                name: "advertisingData"
            },
            {
                checked: false,
                description: "Any other data about user activity in the app",
                label: "Other Usage Data",
                name: "otherUsageData"
            },
        ]
    },
    {
        title: "Diagnostics",
        name: "diagnostics",
        icon: settingsIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Such as crash logs",
                label: "Crash data",
                name: "crashData"
            },
            {
                checked: false,
                description: "Such as launch time, hang rate, or energy use",
                label: "Performance Data",
                name: "performanceData"
            },
            {
                checked: false,
                description: "Any other data collected for the purpose of measuring technical diagnostics related to the app",
                label: "Other Diagnostic Data",
                name: "otherDiagnosticData"
            },
        ]
    },
    {
        title: "Other Data",
        name: "otherData",
        icon: circleInfoIcon,
        tooltip: "More Info",
        tooltipText: "More Info",
        checkboxItems: [
            {
                checked: false,
                description: "Any other data types not mentioned",
                label: "Other data",
                name: "otherData"
            },
        ]
    },
];