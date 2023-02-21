export type AppFlowListItemProps = {
    label: string;
    name: string;
    checked: boolean;
    selected: boolean;
}

export const initialFLowListItems: AppFlowListItemProps[] = [
    {
        label: "Create",
        name: "create",
        checked: false,
        selected: true,
    },
    {
        label: "Profile",
        name: "profile",
        checked: false,
        selected: false,
    },
    {
        label: "Build",
        name: "build",
        checked: false,
        selected: false,
    },
    {
        label: "Storefront",
        name: "storefront",
        checked: false,
        selected: false,
    },
    {
        label: "Version",
        name: "version",
        checked: false,
        selected: false,
    },
    {
        label: "Pricing",
        name: "pricing",
        checked: false,
        selected: false,
    },
    {
        label: "Licensing",
        name: "licensing",
        checked: false,
        selected: false,
    },
    {
        label: "Support",
        name: "support",
        checked: false,
        selected: false,
    },
    {
        label: "Submit",
        name: "submit",
        checked: false,
        selected: false,
    },
]