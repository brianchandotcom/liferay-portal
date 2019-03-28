/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.talend.source;

import static java.util.Collections.singletonList;

import java.io.Serializable;
import java.util.List;

import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Assessor;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.input.PartitionMapper;
import org.talend.sdk.component.api.input.PartitionSize;
import org.talend.sdk.component.api.input.Split;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import com.liferay.talend.service.TalendService;

/**
 * @author Zoltán Takács
 */
@Version(1)
@Icon(Icon.IconType.STAR)
@PartitionMapper(name = "tLiferayInput")
@Documentation("TODO fill the documentation for this mapper")
public class TLiferayInputMapper implements Serializable {
    private final TLiferayInputMapperConfiguration configuration;
    private final TalendService service;
    private final RecordBuilderFactory recordBuilderFactory;

    public TLiferayInputMapper(@Option("configuration") final TLiferayInputMapperConfiguration configuration,
                        final TalendService service,
                        final RecordBuilderFactory recordBuilderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.recordBuilderFactory = recordBuilderFactory;
    }

    @Assessor
    public long estimateSize() {
        return 1L;
    }

    @Split
    public List<TLiferayInputMapper> split(@PartitionSize final long bundles) {
        return singletonList(this);
    }

    @Emitter
    public TLiferayInputSource createWorker() {
        return new TLiferayInputSource(configuration, service, recordBuilderFactory);
    }
}