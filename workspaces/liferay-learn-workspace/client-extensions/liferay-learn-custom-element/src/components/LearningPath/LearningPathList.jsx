/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import "../../index.scss";
import ClayLayout from "@clayui/layout";
import { useEffect, useState } from "react";
import { getLearningPaths } from "../../services/learning-path";
import SectionCard from "../Common/SectionCard";

const LearningPathsList = () => {
    const [learningPaths, setLearningPaths] = useState(null);

    useEffect(() => {
        getLearningPaths(3)
            .then(data => setLearningPaths(data))
            .catch(error =>
                console.error('Error fetching learning paths:', error)
            );
    }, []);

    return (
        <ClayLayout.ContainerFluid view>
            <ClayLayout.Row justify="start">
                {learningPaths && learningPaths.length > 0 && (
                    <>
                        {learningPaths.map((learningPath, index) => {
                            return (
                                <SectionCard
                                    description={
                                        !learningPath.description ? '' : learningPath.description
                                    }
                                    expertise={learningPath.level.name}
                                    index={index}
                                    key={learningPath.id}
                                    link={`/l/${learningPath.id}`}
                                    personas={learningPath.persona}
                                    title={!learningPath.title ? '' : learningPath.title}
                                />
                            );
                        })}
                    </>
                )}
            </ClayLayout.Row>
        </ClayLayout.ContainerFluid>
    );
};

export default LearningPathsList;