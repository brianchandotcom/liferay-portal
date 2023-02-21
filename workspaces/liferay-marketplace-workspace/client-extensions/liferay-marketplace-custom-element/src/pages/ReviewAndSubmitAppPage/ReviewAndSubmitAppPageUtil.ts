export type File = {
  image: string;
  fileName: string;
  fileDescription: string;
};

export type CardInfo = {
  icon: string;
  title: string;
  link: string;
};

export type CardTag = {
  icon: string;
  title: string;
  tags: string[];
};

export type ReviewAndSubmitAppPageUtilProps = {
  cardInfos?: CardInfo[];
  cardTags?: CardTag[];
  description?: string;
  icon?: string;
  fileName?: string;
  files?: File[];
  section: string;
  tags?: string[];
  title?: string;
  version?: string;
};

import brightnessEmptyIcon from "../../assets/icons/brightness-empty.svg";

import scheduleIcon from "../../assets/icons/schedule-icon.svg";
import file1 from "../../assets/images/file1.svg";
import file2 from "../../assets/images/file2.svg";


export const initialReviewAndSubmitAppPageItems: ReviewAndSubmitAppPageUtilProps[] =
  [
    {
      section: "Categories",
      tags: ["Experience Management", "Collaboration and Knowledge Sharing"],
    },
    {
      section: "Tags",
      tags: [
        "CRM",
        "Employee Experience",
        "Employee Portal",
        "Knowledge Management",
      ],
    },
    {
      section: "Build",
      fileName: "a-co-libraries-01.lpkg",
    },
    {
      icon: brightnessEmptyIcon,
      section: "Pricing",
      title: "Free",
    },
    {
      description: "License never expires.",
      icon: scheduleIcon,
      section: "Licensing",
      title: "Perpetual License",
    },
    {
      section: "Storefront",
    },
    {
      description:
        "Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec sed odio dui. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Cras mattison purus sit amet fermentum.",
      section: "Version",
      title: "Release Notes",
      version: "0.0.1",
    },
    {
      section: "Support & Help",
    },
  ];
