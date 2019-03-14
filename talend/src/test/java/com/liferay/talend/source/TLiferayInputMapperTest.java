package com.liferay.talend.source;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.junit.SimpleComponentRule;
import org.talend.sdk.component.runtime.input.Mapper;

public class TLiferayInputMapperTest {

    @ClassRule
    public static final SimpleComponentRule COMPONENT_FACTORY = new SimpleComponentRule("com.liferay.talend");

    @Test
    @Ignore("You need to complete this test")
    public void produce() throws IOException {

        // Source configuration
        // Setup your component configuration for the test here
        final TLiferayInputMapperConfiguration configuration =  new TLiferayInputMapperConfiguration()
                                                                            /* .setDataset()
                                                                               .setTimeout()
                                                                               .setMaxItemsPerRequest() */;

        // We create the component mapper instance using the configuration filled above
        final Mapper mapper = COMPONENT_FACTORY.createMapper(TLiferayInputMapper.class, configuration);

        // Collect the source as a list
        assertEquals(asList(/* TODO - give the expected data */), COMPONENT_FACTORY.collectAsList(Record.class, mapper));
    }

}