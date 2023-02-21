import { AppProps } from './../../components/DashboardTable/DashboardTable';
import { DashboardListItems } from "../../components/DashboardNavigation/DashboardNavigation";

import salesIcon from '../../assets/icons/sales-icon.svg';
import membersIcon from '../../assets/icons/person-fill.svg';
import accountIcon from '../../assets/icons/account-icon.svg';
import appsIcon from '../../assets/icons/apps-fill.svg';
import appIconTransport from '../../assets/icons/app-icon-transport.svg';
import appIconSales from '../../assets/icons/app-icon-sales.svg';
import appIconPayments from '../../assets/icons/app-icon-payments.svg';

export const appList: AppProps[] = [
    {
        image: appIconTransport,
        name: "A&Co Transport",
        rating: '4.3',
        status: 'Published',
        type: "SaaS",
        updatedBy: 'by Hanna White',
        updatedDate: "Feb 14, 2023",
        updatedResponsible: 'you',
        version: "1.40",
        selected: false
    },
    {
        image: appIconSales,
        name: "A&Co Sales",
        rating: '4.7',
        status: 'Pending',
        type: "OSGI",
        updatedBy: 'by Hanna White',
        updatedDate: "Feb 14, 2023",
        updatedResponsible: 'you',
        version: "2.28",
        selected: false
    },
    {
        image: appIconPayments,
        name: "A&Co Payments",
        rating: '4.1',
        status: 'Hidden',
        type: "OSGI",
        updatedBy: 'by Hanna White',
        updatedDate: "Feb 14, 2023",
        updatedResponsible: 'you',
        version: "1.0",
        selected: false
    },
];

export const initialDashboardNavigationItems: DashboardListItems[] = [
    {
        itemIcon: appsIcon,
        itemTitle: "Apps",
        itemName: 'apps',
        items: appList,
        itemSelected: true
    },
    {
        itemIcon: salesIcon,
        itemTitle: "Sales",
        itemName: 'sales',
        itemSelected: false
    },
    {
        itemIcon: membersIcon,
        itemTitle: "Members",
        itemName: 'members',
        itemSelected: false
    },
    {
        itemIcon: accountIcon,
        itemTitle: "Account",
        itemName: 'account',
        itemSelected: false
    },
];