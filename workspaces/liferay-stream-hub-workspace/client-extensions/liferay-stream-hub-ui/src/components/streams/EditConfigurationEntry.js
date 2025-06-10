import {forwardRef, useEffect, useImperativeHandle, useRef, useState} from "react";
import ClayForm from "@clayui/form";
import {getUserRoles} from "../../services/object/users";
import ClayLayout from '@clayui/layout';
import {useFormik} from 'formik';
import {ClayToggle} from '@clayui/form';
import {showError, showSuccess} from "../../utils/util";
import {getDefinitions} from "../../services/object/definition";
import ClayTabs from '@clayui/tabs';
import {putStreamConfigurationEntry} from "../../services/object/streamConfiguration";
import ClayAlert from "@clayui/alert";
import {ActionsTypes} from "../../utils/constants";
import StreamEventConfigurationForm from "./StreamEventConfigurationForm";


const EditConfigurationEntry = forwardRef((props, ref) => {

    const {SuccessCallback, entry} = props;

    const [roles, setRoles] = useState(null);
    const [objectDefinitions, setObjectDefinitions] = useState(null);
    const [active, setActive] = useState(0);
    const [selectedObjectDefinition, setSelectedObjectDefinition] = useState(null);

    const [onAfterAdd, setOnAfterAdd] = useState(false);
    const [onAfterUpdate, setOnAfterUpdate] = useState(false);
    const [onAfterRemove, setOnAfterRemove] = useState(false);
    const [onStandalone, setOnStandalone] = useState(false);

    const [onAfterAddValues, setOnAfterAddValues] = useState(null);
    const [onAfterUpdateValues, setOnAfterUpdateValues] = useState(null);
    const [onAfterRemoveValues, setOnAfterRemoveValues] = useState(null);
    const [onStandaloneValues, setOnStandaloneValues] = useState(null);

    const onAfterAddStreamEventConfigurationFrom = useRef(null);
    const onAfterUpdateStreamEventConfigurationFrom = useRef(null);
    const onAfterRemoveStreamEventConfigurationFrom = useRef(null);
    const onStandaloneStreamEventConfigurationFrom = useRef(null);

    const setActiveTab = () => {

        if (onAfterAdd) {
            setActive(0);
            return;
        }
        if (onAfterUpdate) {
            setActive(1);
            return;
        }
        if (onAfterRemove) {
            setActive(2);
            return;
        }
        if (onStandalone) {
            setActive(3);
            return;
        }

    }

    const getLiferayRolesList = async () => {

        let result = await getUserRoles();

        if (result.items && result.items.length > 0) {
            setRoles(result.items);
        }

    }

    const handleChange = (event) => {

        const {name, value} = event.target;

        if (name === "name") {

            const objectDefinition = objectDefinitions.find(item => item.name === value);

            setSelectedObjectDefinition(objectDefinition);

            setFieldValue("objectDefinitionId", objectDefinition ? objectDefinition.id : value, false).then(() => {

                setFieldValue(name, value, false).then(() => {

                    validateForm();

                });
            })
        } else {
            setFieldValue(name, value, false).then(() => {

                validateForm();

            });
        }

    };

    const getLiferayObjectDefinitions = async () => {

        let result = await getDefinitions(0, 0);

        if (result.items && result.items.length > 0) {

            let objectDefinitions = result.items.map(item => {
                return {
                    name: item.name,
                    id: item.id,
                    objectFields: item.objectFields,
                    system: item.system
                }
            });

            setObjectDefinitions(objectDefinitions);

            return objectDefinitions;
        }
    }

    const {errors, isValid, validateForm, setFieldValue, handleSubmit, touched, values} = useFormik({
        initialValues: {
            objectDefinitionId: '',
            name: '',
            configuration: ''
        },
        onSubmit: async (values) => {

            let configuration = {
                objectId: values.objectDefinitionId,
                actionsList: []
            }
            if (isValid) {
                if (onAfterAdd) {

                    let configurationValues =
                        onAfterAddStreamEventConfigurationFrom.current.getValues();
                    configuration.actionsList.push({
                        name: `stream${values.objectDefinitionId}OnAdd`,
                        type: `onAfterAdd`,
                        roles: configurationValues.roles,
                        fields:configurationValues.fields,
                        enableOfflineMessageQueue:configurationValues.enableOfflineMessageQueue
                    })
                }

                if (onAfterUpdate) {

                    let configurationValues =
                        onAfterUpdateStreamEventConfigurationFrom.current.getValues();

                    configuration.actionsList.push({
                        name: `stream${values.objectDefinitionId}OnUpdate`,
                        type: `onAfterUpdate`,
                        roles: configurationValues.roles,
                        fields:configurationValues.fields,
                        enableOfflineMessageQueue:configurationValues.enableOfflineMessageQueue
                    })

                }

                if (onAfterRemove) {

                    let configurationValues =
                        onAfterRemoveStreamEventConfigurationFrom.current.getValues();

                    configuration.actionsList.push({
                        name: `stream${values.objectDefinitionId}OnRemove`,
                        type: `onAfterDelete`,
                        roles: configurationValues.roles,
                        fields:configurationValues.fields,
                        enableOfflineMessageQueue:configurationValues.enableOfflineMessageQueue
                    })

                }

                if (onStandalone) {

                    let configurationValues =
                        onStandaloneStreamEventConfigurationFrom.current.getValues();

                    configuration.actionsList.push({
                        name: `stream${values.objectDefinitionId}Standalone`,
                        type: `standalone`,
                        roles: configurationValues.roles,
                        fields:configurationValues.fields,
                        enableOfflineMessageQueue:configurationValues.enableOfflineMessageQueue
                    })

                }

                try {
                    let result = await putStreamConfigurationEntry(entry.id, {
                        configuration: JSON.stringify(configuration),
                        objectDefinitionId: values.objectDefinitionId,
                        name: values.name
                    });

                    if (result && result.id) {

                        showSuccess("Stream Update", "Stream Updated successfully.");

                        SuccessCallback();

                    } else {

                        showError("Stream Update Error", "Unable to update the stream. Please try again.");

                    }

                } catch (error) {

                    showError("Stream Creation Error", "Unable to update the stream. Please try again.");

                }


            }


        },
        validate: (values) => {

            const errors = {};

            if (!values.name || values.name == "-1") {

                errors.name = 'Please select object definition.';
            }

            if (!onAfterAdd && !onAfterUpdate && !onAfterRemove && !onStandalone) {
                errors.noEvents = "Please select at least one event."
            }


            if (!onAfterAdd && !onAfterUpdate && !onAfterRemove && !onStandalone) {
                errors.noEvents = "Please select at least one event."
            }


            if (onAfterAdd){

                let configurationValues =
                    onAfterAddStreamEventConfigurationFrom.current.getValues();

                if (configurationValues.roles.length <= 0 ){
                    errors.onAfterAddRoles = "Please select roles for on after add event."
                }

                if (configurationValues.fields.length <= 0 ){
                    errors.onAfterAddFields = "Please select message fields for on after add event."
                }

            }

            if (onAfterUpdate){

                let configurationValues =
                    onAfterUpdateStreamEventConfigurationFrom.current.getValues();

                if (configurationValues.roles.length <= 0 ){
                    errors.onAfterUpdateRoles = "Please select roles for on after update event."
                }

                if (configurationValues.fields.length <= 0 ){
                    errors.onAfterUpdateFields = "Please select message fields for on after update event."
                }

            }

            if (onAfterRemove){

                let configurationValues =
                    onAfterRemoveStreamEventConfigurationFrom.current.getValues();

                if (configurationValues.roles.length <= 0 ){
                    errors.onAfterRemoveRoles = "Please select roles for on after remove event."
                }

                if (configurationValues.fields.length <= 0 ){
                    errors.onAfterRemoveFields = "Please select message fields for on after remove event."
                }

            }

            if (onStandalone){

                let configurationValues =
                    onStandaloneStreamEventConfigurationFrom.current.getValues();

                if (configurationValues.roles.length <= 0 ){
                    errors.onStandaloneRoles = "Please select roles for standalone event."
                }

                if (configurationValues.fields.length <= 0 ){
                    errors.onStandaloneFields = "Please select message fields for standalone add event."
                }

            }
            return errors;

        },
    });

    const prepareForm = async (configuration) => {

        await getLiferayRolesList();

        let objectDefinitions = await getLiferayObjectDefinitions();

        let objectDefinition = objectDefinitions.find((objectDefinition) => objectDefinition.id === configuration.objectId);

        setSelectedObjectDefinition(objectDefinition);

        await setFieldValue("objectDefinitionId", configuration.objectId);

        await setFieldValue("name", objectDefinition.name);

        let onAfterAddConfig = configuration.actionsList.find(action => action.type === ActionsTypes.ON_AFTER_ADD);

        let onAfterUpdateConfig = configuration.actionsList.find(action => action.type === ActionsTypes.ON_AFTER_UPDATE);

        let onAfterRemoveConfig = configuration.actionsList.find(action => action.type === ActionsTypes.ON_AFTER_REMOVE);

        let onAfterStandaloneConfig = configuration.actionsList.find(action => action.type === ActionsTypes.STAND_ALONE);

        if (onAfterAddConfig) {

            setOnAfterAdd(true);

            setOnAfterAddValues(
                {
                    enableOfflineMessageQueue: onAfterAddConfig.enableOfflineMessageQueue,
                    roles: onAfterAddConfig.roles,
                    fields: onAfterAddConfig.fields
                }
            );

        }

        if (onAfterUpdateConfig) {

            setOnAfterUpdate(true);

            setOnAfterUpdateValues(
                {
                    enableOfflineMessageQueue: onAfterUpdateConfig.enableOfflineMessageQueue,
                    roles: onAfterUpdateConfig.roles,
                    fields: onAfterUpdateConfig.fields
                });

        }

        if (onAfterRemoveConfig) {

            setOnAfterRemove(true);

            setOnAfterRemoveValues(
                {
                    enableOfflineMessageQueue: onAfterRemoveConfig.enableOfflineMessageQueue,
                    roles: onAfterRemoveConfig.roles,
                    fields: onAfterRemoveConfig.fields
                });


        }

        if (onAfterStandaloneConfig) {

            setOnStandalone(true);

            setOnStandaloneValues(
                {
                    enableOfflineMessageQueue: onAfterStandaloneConfig.enableOfflineMessageQueue,
                    roles: onAfterStandaloneConfig.roles,
                    fields: onAfterStandaloneConfig.fields
                });

        }

    }

    useEffect(() => {

        prepareForm(entry.configuration);


    }, []);

    useEffect(() => {
        if (onAfterAdd) {
            setActive(0);
        } else
            setActiveTab();
        validateForm();

    }, [onAfterAdd]);

    useEffect(() => {
        if (onAfterUpdate) {
            setActive(1);
        } else
            setActiveTab();
        validateForm();

    }, [onAfterUpdate]);

    useEffect(() => {
        if (onAfterRemove) {
            setActive(2);

        } else
            setActiveTab();
        validateForm();

    }, [onAfterRemove]);

    useEffect(() => {
        if (onStandalone) {
            setActive(3);
        } else
            setActiveTab();
        validateForm();

    }, [onStandalone]);

    useImperativeHandle(ref, () => ({
        handleSubmit
    }));

    return (
        <>
            {
                roles && objectDefinitions && (
                    <>
                        {
                            roles && objectDefinitions && (
                                <>
                                    <form>
                                        <ClayLayout.ContainerFluid view>
                                            <ClayLayout.Row justify="between">
                                                <ClayLayout.Col size={8}>
                                                    <ClayForm.Group className="form-group-sm">
                                                        <label htmlFor="name">Object Definition</label>
                                                        <select
                                                            className="form-control"
                                                            id="name"
                                                            name="name"
                                                            onChange={handleChange}
                                                            value={values["name"]}
                                                        >
                                                            <option value={-1}>Select Object Definition</option>

                                                            {objectDefinitions && objectDefinitions.map((definition) => (
                                                                <option key={definition.id} value={definition.name}>
                                                                    {definition.name.toUpperCase()}
                                                                </option>
                                                            ))}

                                                        </select>
                                                    </ClayForm.Group>
                                                </ClayLayout.Col>
                                                <ClayLayout.Col size={4}>
                                                    <ClayForm.Group className="form-group-sm">
                                                        <label htmlFor="objectDefinitionId">Object Definition ID</label>
                                                        <input className="form-control" id="objectDefinitionId"
                                                               value={values["objectDefinitionId"]} name="objectDefinitionId"
                                                               disabled={true}/>
                                                    </ClayForm.Group>
                                                </ClayLayout.Col>
                                            </ClayLayout.Row>
                                            {selectedObjectDefinition && (
                                                <>
                                                    <ClayLayout.Row justify="between">
                                                        <ClayLayout.Col size={12}>
                                                            <ClayForm.Group className="form-group-sm">
                                                                <label htmlFor="objectDefinitionId">Enable Events</label>
                                                            </ClayForm.Group>
                                                            <ClayLayout.Row>
                                                                <ClayLayout.Col size={3}>
                                                                    <ClayToggle
                                                                        label="Add"
                                                                        onToggle={setOnAfterAdd}
                                                                        toggled={onAfterAdd}
                                                                    />
                                                                </ClayLayout.Col>
                                                                <ClayLayout.Col size={3}>
                                                                    <ClayToggle
                                                                        label="Update"
                                                                        onToggle={setOnAfterUpdate}
                                                                        toggled={onAfterUpdate}
                                                                    />
                                                                </ClayLayout.Col>
                                                                <ClayLayout.Col size={3}>
                                                                    <ClayToggle
                                                                        label="Remove"
                                                                        onToggle={setOnAfterRemove}
                                                                        toggled={onAfterRemove}
                                                                    />
                                                                </ClayLayout.Col>
                                                                {!selectedObjectDefinition.system && (
                                                                    <ClayLayout.Col size={3}>
                                                                        <ClayToggle
                                                                            label="Standalone"
                                                                            onToggle={setOnStandalone}
                                                                            toggled={onStandalone}
                                                                        />
                                                                    </ClayLayout.Col>
                                                                )}
                                                            </ClayLayout.Row>
                                                        </ClayLayout.Col>
                                                    </ClayLayout.Row>
                                                    <ClayTabs active={active} onActiveChange={setActive}>
                                                        {onAfterAdd && (
                                                            <ClayTabs.Item
                                                                innerProps={{
                                                                    "aria-controls": "tabpanel-add"
                                                                }}
                                                            >
                                                                On After Add
                                                            </ClayTabs.Item>
                                                        )}
                                                        {onAfterUpdate && (
                                                            <ClayTabs.Item
                                                                innerProps={{
                                                                    "aria-controls": "tabpanel-update"
                                                                }}
                                                            >
                                                                On After Update
                                                            </ClayTabs.Item>
                                                        )}
                                                        {onAfterRemove && (
                                                            <ClayTabs.Item
                                                                innerProps={{
                                                                    "aria-controls": "tabpanel-remove"
                                                                }}
                                                            >
                                                                On After Remove
                                                            </ClayTabs.Item>
                                                        )}
                                                        {onStandalone && (
                                                            <ClayTabs.Item
                                                                innerProps={{
                                                                    "aria-controls": "tabpanel-standalone"
                                                                }}
                                                            >
                                                                Standalone
                                                            </ClayTabs.Item>
                                                        )}
                                                    </ClayTabs>
                                                    <ClayTabs.Content activeIndex={active}>
                                                        {onAfterAdd && selectedObjectDefinition && (
                                                            <ClayTabs.TabPane key={'add'} aria-labelledby="tab-1">
                                                                <StreamEventConfigurationForm callback={validateForm}
                                                                                              roles={roles}
                                                                                              selectedRoles={onAfterAddValues? onAfterAddValues.roles : []}
                                                                                              selectedFields={onAfterAddValues?onAfterAddValues.fields:[]}
                                                                                              offlineMessageQueue = {onAfterAddValues?onAfterAddValues.enableOfflineMessageQueue:false}
                                                                                              objectDefinition={selectedObjectDefinition}
                                                                                              ref={onAfterAddStreamEventConfigurationFrom}></StreamEventConfigurationForm>
                                                            </ClayTabs.TabPane>
                                                        )}
                                                        {onAfterUpdate && selectedObjectDefinition && (
                                                            <ClayTabs.TabPane key={'update'} aria-labelledby="tab-2">
                                                                <StreamEventConfigurationForm callback={validateForm}
                                                                                              roles={roles}
                                                                                              selectedRoles={onAfterUpdateValues?onAfterUpdateValues.roles:[]}
                                                                                              selectedFields={onAfterUpdateValues?onAfterUpdateValues.fields:[]}
                                                                                              offlineMessageQueue = {onAfterUpdateValues?onAfterUpdateValues.enableOfflineMessageQueue:false}
                                                                                              objectDefinition={selectedObjectDefinition}
                                                                                              ref={onAfterUpdateStreamEventConfigurationFrom}></StreamEventConfigurationForm>
                                                            </ClayTabs.TabPane>
                                                        )}
                                                        {onAfterRemove && selectedObjectDefinition && (
                                                            <ClayTabs.TabPane key={'remove'} aria-labelledby="tab-3">
                                                                <StreamEventConfigurationForm callback={validateForm}
                                                                                              roles={roles}
                                                                                              selectedRoles={onAfterRemoveValues?onAfterRemoveValues.roles:[]}
                                                                                              selectedFields={onAfterRemoveValues?onAfterRemoveValues.fields:[]}
                                                                                              offlineMessageQueue = {onAfterRemoveValues?onAfterRemoveValues.enableOfflineMessageQueue:false}
                                                                                              objectDefinition={selectedObjectDefinition}
                                                                                              ref={onAfterRemoveStreamEventConfigurationFrom}></StreamEventConfigurationForm>
                                                            </ClayTabs.TabPane>
                                                        )}
                                                        {onStandalone && selectedObjectDefinition && (
                                                            <ClayTabs.TabPane key={'standalone'} aria-labelledby="tab-4">
                                                                <StreamEventConfigurationForm callback={validateForm}
                                                                                              roles={roles}
                                                                                              selectedRoles={onStandaloneValues?onStandaloneValues.roles:[]}
                                                                                              selectedFields={onStandaloneValues?onStandaloneValues.fields:[]}
                                                                                              offlineMessageQueue = {onStandaloneValues?onStandaloneValues.enableOfflineMessageQueue:false}
                                                                                              objectDefinition={selectedObjectDefinition}
                                                                                              ref={onStandaloneStreamEventConfigurationFrom}></StreamEventConfigurationForm>
                                                            </ClayTabs.TabPane>
                                                        )}

                                                    </ClayTabs.Content>
                                                </>
                                            )}
                                            {errors && touched && Object.keys(errors).length > 0 && (
                                                <div className={"mt-4"}>
                                                    {Object.keys(errors).map((error, index) => (
                                                        <ClayAlert key={`key_${index}`} displayType="danger" role={null}>
                                                            {errors[error]}
                                                        </ClayAlert>
                                                    ))}
                                                </div>
                                            )}
                                        </ClayLayout.ContainerFluid>
                                    </form>
                                </>
                            )
                        }
                    </>
                )
            }
        </>
    )


});

export default EditConfigurationEntry;
