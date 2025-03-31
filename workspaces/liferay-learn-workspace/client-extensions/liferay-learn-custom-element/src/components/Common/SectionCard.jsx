/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
*/

import ClayLayout from "@clayui/layout";
import { ClayTooltipProvider } from "@clayui/tooltip";
import { getPersonas, getShortText, getTooltipPersona } from "../../utils/util";
import React from "react";
import "./SectionCard.scss"

const SectionCard = ({ description, expertise, index, key, link, personas, title }) => {
  return (
    <ClayLayout.Col
      xl={4}
      lg={4}
      md={6}
      sm={12}
      xs={12}
      key={index}
      className="d-flex course-layout-col"
    >
      <a
        href={link}
        className="education-home__card-link"
      >
        <div className="education-home-card-container d-flex learn-education__landing-card">
          <div className="course-content-card d-flex flex-column justify-content-between">
            <h4 className="title">{title}</h4>
            <div className="description">
              {getShortText(description, 150)}
            </div>
            <div
              className="d-flex information learn-education"
              key={key}
            >
              <ClayTooltipProvider>
                <div
                  className="card-tag card-tag__persona"
                  data-tool-tip-align="top"
                  title={getTooltipPersona(personas)}
                >
                  <p></p>
                  {getPersonas(personas)}
                </div>
              </ClayTooltipProvider>
              <div
                className={`card-tag card-tag__expertise-${expertise.toLowerCase()}`}
              >
                <p>{expertise}</p>
              </div>
            </div>
          </div>
        </div>
      </a>
    </ClayLayout.Col>
  );
};

export default SectionCard;
