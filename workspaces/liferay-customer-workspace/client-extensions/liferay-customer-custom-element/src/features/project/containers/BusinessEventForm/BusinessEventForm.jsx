/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { ClayInput } from '@clayui/form';
import i18n from '~/utils/I18n';
import { useNavigate } from 'react-router-dom';
import { FieldArray, Formik } from "formik";
import getInitialEvent from '~/utils/getInitialEvent';
import { Button, Input, Select } from '~/components';
import Layout from '../../../../components/FormLayout';
import { useEffect, useMemo, useState } from 'react';
import useGetBusinessEventTypesList from './hooks/useGetBusinessEventTypesList';
import useGetGMTTimeZonesList from './hooks/useGetGMTTimeZonesList';
import useGetVersionOfLiferaySoftwareList from './hooks/useGetVersionOfLiferaySoftwareList';
import useHasAllEventsPermissions from '../../../project/pages/Project/BusinessEvent/hooks/useHasAllEventsPermissions';
import './BusinessEventForm.css'
import { useCustomerPortal } from '~/features/project/context';
import { PRODUCT_TYPES } from '../../utils/constants';
import DatePicker from '~/components/DatePicker/DatePicker';
import TimePicker from '~/components/TimePicker/TimePicker';
import { useAppPropertiesContext } from '~/contexts/AppPropertiesContext';
import { addBusinessEvent } from '~/services/liferay/graphql/queries';
import { getBusinessEvents } from '~/services/liferay/api';

const BusinessEventPage = ({
    errors,
    setFieldValue,
    touched,
    values
}) => {
    const { client } = useAppPropertiesContext();

    const [{ project, subscriptionGroups }] = useCustomerPortal();

    const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);

    const [businessEventTypesOptions, setBusinessEventTypesOptions] = useState([]);

    const [gmtTimeZonesOptions, setGMTTimeZonesOptions] = useState([]);

    const [isLoadingSubmitButton, setIsLoadingSubmitButton] = useState(false);

    const [time, setTime] = useState({
        hours: '--',
        minutes: '--',
    });

    const [versionOfLiferaySoftwareOptions, setVersionOfLiferaySoftwareOptions] = useState([]);

    const businessEventTypesList = useGetBusinessEventTypesList();

    const emptyOption = {
        disabled: true,
        label: i18n.translate('select-the-option'),
        value: '',
    };

    const gmtTimeZonesList = useGetGMTTimeZonesList();

    const handleSubmit = async () => {
        const businessEvent = values?.businessEvent;

        if (businessEvent.time) {
            businessEvent.targetGoLiveDateTime = `${businessEvent.targetGoLiveDate}T${businessEvent.time}`
        }
        else {
            businessEvent.targetGoLiveDateTime = `${businessEvent.targetGoLiveDate}T00:00:00.000`
        }

        try {
            setIsLoadingSubmitButton(true);
            
            const { data } = await client.mutate({
                context: {
                    displaySuccess: false,
                    type: 'liferay-rest',
                },
                mutation: addBusinessEvent,
                variables: {
                    businessEvent
                },
            });

            navigate(`/${project?.accountKey}/business-events`);

            Liferay.Util.openToast({
                message: i18n.translate('business-event-created-successfully'),
                type: 'success'
            });

        }
        catch (error) {
            setIsLoadingSubmitButton(false);

            Liferay.Util.openToast({
                message: i18n.translate('an-unexpected-error-occurred'),
                type: 'danger'
            });
        }
    };

    const handleTimeChange = (newTime) => {
        if (newTime.hours !== '--' && newTime.minutes !== '--') {
            setFieldValue('businessEvent.time', `${newTime.hours}:${newTime.minutes}:00.000`);
        }
        else {
            setFieldValue('businessEvent.time', '');
        }

        setTime(newTime);
    };

    const hasAllEventsPermissions = useHasAllEventsPermissions();

    const isSaasOnly = useMemo(
        () => subscriptionGroups?.length === 1 && subscriptionGroups[0].name === PRODUCT_TYPES.liferayExperienceCloud,
        [subscriptionGroups]
    );

    const isDescriptionRequired = useMemo(
        () => isSaasOnly || values.businessEvent.eventType === 'otherEvent',
        [isSaasOnly, values.businessEvent.eventType]
    );

    const businessEvents = useMemo(
        async () => {
            try {
                const response = await getBusinessEvents(`r_accountEntryToBusinessEvents_accountEntryERC eq '${project?.accountKey}'`);
                return response.items;
            }
            catch (error) {
                console.error('Error', error);
            }
            return [];

        },
        [getBusinessEvents, project]
    );

    const isNewLiferayVersionRequired = useMemo(
        () => !isSaasOnly && ['migration', 'upgrade'].includes(values.businessEvent.eventType),
        [isSaasOnly, values.businessEvent.eventType]
    );

    const navigate = useNavigate();

    const versionOfLiferaySoftwareList = useGetVersionOfLiferaySoftwareList();

    useEffect(() => {
        const hasTouched = !Object.keys(touched).length;
        const hasError = Object.keys(errors).length;

        setBaseButtonDisabled(hasTouched || hasError);
    }, [touched, errors]);

    useEffect(() => {
        if (businessEventTypesList?.length) {
            setBusinessEventTypesOptions(
                [emptyOption, ...businessEventTypesList]
            );
        }
    }, [businessEventTypesList, setBusinessEventTypesOptions]);

    useEffect(() => {
        if (gmtTimeZonesList?.length) {
            setGMTTimeZonesOptions(
                [emptyOption, ...gmtTimeZonesList]
            );
        }
    }, [gmtTimeZonesList, setGMTTimeZonesOptions]);

    useEffect(() => {
        if (versionOfLiferaySoftwareList?.length) {
            setVersionOfLiferaySoftwareOptions(
                [emptyOption, ...versionOfLiferaySoftwareList]
            );
        }
    }, [versionOfLiferaySoftwareList, setVersionOfLiferaySoftwareOptions]);

    useEffect(() => {
        if (businessEventTypesOptions?.length) {
            setFieldValue(
                'businessEvent.eventType',
                businessEventTypesOptions[0].value
            );
        }
    }, [businessEventTypesOptions, setFieldValue]);

    useEffect(() => {
        if (versionOfLiferaySoftwareOptions?.length) {
            setFieldValue(
                'businessEvent.currentLiferayVersion',
                versionOfLiferaySoftwareOptions[0].value
            );
        }
    }, [versionOfLiferaySoftwareOptions, setFieldValue]);

    useEffect(() => {
        setFieldValue('businessEvent.r_accountEntryToBusinessEvents_accountEntryId', project?.id);
    }, [project?.id, setFieldValue]);

    return hasAllEventsPermissions ? (
        <Layout
            footerProps={{
                leftButton: (
                    <Button
                        displayType="secondary"
                        onClick={() => {
                            navigate(`/${project?.accountKey}/business-events`);
                        }}
                    >
                        {i18n.translate('cancel')}
                    </Button>
                ),
                middleButton: (
                    <Button
                        disabled={baseButtonDisabled || isLoadingSubmitButton}
                        displayType="primary"
                        isLoading={isLoadingSubmitButton}
                        onClick={handleSubmit}
                    >
                        {i18n.translate('create-event')}
                    </Button>
                ),
            }}
            headerProps={{
                greetings: project?.name,
                title: i18n.translate('create-business-event'),
            }}
        >
            <FieldArray
                name="businessEvent"
                render={() => (
                    <>
                        <Input
                            badgeClassName="ml-3 mr-3"
                            groupStyle="pb-1"
                            label={i18n.translate('event-name')}
                            name="businessEvent.name"
                            placeholder={i18n.translate('event-name')}
                            required
                            type="text"
                        />

                        <Select
                            badgeClassName="ml-3 mr-3"
                            groupStyle="pb-1"
                            label={i18n.translate(
                                'event-type'
                            )}
                            link="https://help.liferay.com/hc"
                            linkText="here"
                            name="businessEvent.eventType"
                            options={businessEventTypesOptions}
                            required
                            showPopover
                            text="to-learn-more-about-types-of-business-events-please-click-x"
                        />

                        {subscriptionGroups && !isSaasOnly && (
                            <Select
                                badgeClassName="ml-3 mr-3"
                                className="text-capitalize"
                                groupStyle="pb-1"
                                label={i18n.translate(
                                    'your-current-liferay-version'
                                )}
                                name="businessEvent.currentLiferayVersion"
                                options={versionOfLiferaySoftwareOptions}
                                required
                            />
                        )}

                        {isNewLiferayVersionRequired && (
                            <Select
                                badgeClassName="ml-3 mr-3"
                                className="text-capitalize"
                                groupStyle="pb-1"
                                label={i18n.translate(
                                    'new-version'
                                )}
                                name="businessEvent.newLiferayVersion"
                                options={versionOfLiferaySoftwareOptions}
                                required
                            />
                        )}

                        {isDescriptionRequired && (
                            <Input
                                badgeClassName="ml-3 mr-3"
                                component="textarea"
                                groupStyle="pb-1"
                                label={i18n.translate('event-description')}
                                name="businessEvent.description"
                                placeholder={i18n.translate('event-description')}
                                required
                                type="text"
                            />
                        )}

                        <ClayInput.Group className="m-0">
                            <ClayInput.GroupItem className="m-0">
                                <DatePicker
                                    badgeClassName="ml-3 mr-3"
                                    dateFormat="MM/dd/yyyy"
                                    groupStyle="pb-1"
                                    label={i18n.translate('target-go-live-date')}
                                    name="businessEvent.targetGoLiveDate"
                                    onChange={value => setFieldValue("businessEvent.targetGoLiveDate", value)}
                                    placeholder={i18n.translate('mm-dd-yyyy')}
                                    required
                                    useNative
                                    value={values.businessEvent.targetGoLiveDate}
                                />
                            </ClayInput.GroupItem>

                            <ClayInput.GroupItem className="m-0">
                                <Select
                                    groupStyle="pb-1"
                                    id="select-businessEvent.timeZone"
                                    label={i18n.translate(
                                        'time-zone'
                                    )}
                                    name="businessEvent.timeZone"
                                    options={gmtTimeZonesOptions}
                                />
                            </ClayInput.GroupItem>

                            <ClayInput.GroupItem className="m-0">
                                <TimePicker
                                    groupStyle="pb-1"
                                    label={i18n.translate('time')}
                                    name="businessEvent.time"
                                    onChange={handleTimeChange}
                                    value={time}
                                />
                            </ClayInput.GroupItem>
                        </ClayInput.Group>
                    </>
                )}
            />
        </Layout>
    ) : (<p>{i18n.translate('make-sure-the-project-link-is-correct-and-that-you-have-access-to-this-project')}</p>);
};

const BusinessEventForm = (props) => {
    return (
        <Formik
            initialValues={{ businessEvent: getInitialEvent() }}
            validateOnChange
        >
            {(formikProps) => (
                <BusinessEventPage {...props} {...formikProps} />
            )}
        </Formik>
    );
};

export default BusinessEventForm;
