import {forwardRef, useImperativeHandle, useRef, useState} from "react";
import ClayLayout from "@clayui/layout";
import ClayForm, {ClayToggle} from "@clayui/form";
import RolesSelectorCompact from "./RolesSelectorCompact";
import FieldsSelectorCompact from "./FieldsSelectorCompact";


const StreamEventConfigurationForm = forwardRef((props, ref) => {


    const {offlineMessageQueue,roles, objectDefinition, selectedRoles, selectedFields, callback} = props

    const fieldsSelectorComponentRef = useRef(false);
    const rolesSelectorComponentRef = useRef(false);

    const [enableOfflineMessageQueue, setEnableOfflineMessageQueue] = useState(offlineMessageQueue);

    const getValues = () => {


        const values = {

            enableOfflineMessageQueue: enableOfflineMessageQueue,
            fields: fieldsSelectorComponentRef.current ? fieldsSelectorComponentRef.current.handleGetSelectedFields() : [],
            roles: rolesSelectorComponentRef.current ? rolesSelectorComponentRef.current.handleGetSelectedRole() : [],
        }

        return values;

    }

    useImperativeHandle(ref, () => ({
        getValues
    }));


    return (
        <>
            <ClayLayout.Row justify="start">
                <ClayLayout.Col size={12}>
                    <ClayToggle label="Enable Offline Message Queue" onToggle={setEnableOfflineMessageQueue}
                                toggled={enableOfflineMessageQueue}/>
                    <p>
                        Store event messages for offline users and deliver them when they reconnect.
                    </p>
                </ClayLayout.Col>
            </ClayLayout.Row>
            <ClayLayout.Row justify="between">
                <ClayLayout.Col size={6}>
                    <ClayForm.Group className="form-group-sm">
                        <RolesSelectorCompact callback={callback} ref={rolesSelectorComponentRef} roles={roles}
                                              selectedRoles={selectedRoles}></RolesSelectorCompact>
                    </ClayForm.Group>
                </ClayLayout.Col>
                <ClayLayout.Col size={6}>
                    <ClayForm.Group className="form-group-sm">
                        <FieldsSelectorCompact callback={callback} ref={fieldsSelectorComponentRef}
                                               fields={objectDefinition.objectFields}
                                               selectedFields={selectedFields}></FieldsSelectorCompact>
                    </ClayForm.Group>
                </ClayLayout.Col>
            </ClayLayout.Row>
        </>
    )

});

export default StreamEventConfigurationForm;

