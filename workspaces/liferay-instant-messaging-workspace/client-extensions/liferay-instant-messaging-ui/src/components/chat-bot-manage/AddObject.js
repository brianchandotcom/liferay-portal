import {forwardRef, useCallback, useEffect, useImperativeHandle, useState} from "react";
import {request} from "../../utils/request";
import {useFormik} from "formik";
import ClayForm from "@clayui/form";
import {config} from "../../utils/constants";
import {showError, showSuccess} from "../../utils/util";
import ClayAlert from "@clayui/alert";
import ClayLoadingIndicator from "@clayui/loading-indicator";
import {getDefinitions} from "../../services/object/definition";


const AddObject = forwardRef((props, ref) => {

    const [objectDefinitions, setObjectDefinitions] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [selectedObject, setSelectedObject] = useState(null);

    const {handleClose,handleReload} = props;


    useImperativeHandle(ref, () => ({
        handleSubmit
    }));

    const {errors, handleChange, handleSubmit, touched,values} = useFormik({
        initialValues: {
            contextClauseField: '',
            contextObjectDefinitionID: '',
            familyName:'',
            givenName: '',
            name: '',
        },
        onSubmit: (values) => {

            values.familyName = "Bot";
            values.givenName = "Chat";
            values.contextObjectDefinitionID = selectedObject.id;

            request({
                method: 'POST',
                url: config.chatBotEndPoint,
                data:values
            }).then((result)=>{

                showSuccess("Success","The ChatBot configuration entry has been successfully added.");
                handleReload();
                handleClose();

            },error=>{

                showError("Error","The object can only be added once.");

            })

        },
        validate: (values) => {

            const errors = {};

            if (!values.contextClauseField || values.contextClauseField == -1) {
                errors.contextClauseField = 'Please select a valid field.';
            }

            if (!values.name || values.name == "") {
                errors.name = 'Please provide a name for your ChatBot';
            }

            return errors;

        },
    });

    const handleObjectSelect = useCallback((item) =>{

        let objectDefinition = objectDefinitions.find(definition=>definition.id == item.target.value);

        setSelectedObject(objectDefinition);

    },[objectDefinitions])

    useEffect(()=>{

        setIsLoading(true);


        getDefinitions().then(result => {

            setObjectDefinitions(result);

            setIsLoading(false);

        }).finally(()=>{

            setIsLoading(false);

        })

    },[]);

    return <>
        {!isLoading && objectDefinitions && objectDefinitions.length > 0 && (
            <>
                <ClayForm.Group className="form-group-sm">
                    <label>Object Definition</label>

                    <select aria-label="Select Label" className="form-control" onChange={handleObjectSelect}>
                        <option value={-1} label="Select Object Definition"></option>
                        {objectDefinitions.map(item => (
                            <option
                                key={item.id}
                                label={item.name}
                                value={item.id}
                            />
                        ))}
                    </select>

                </ClayForm.Group>
                {selectedObject && (
                    <>
                        <form onSubmit={handleSubmit}>
                            <ClayForm.Group className="form-group-sm">
                                <label htmlFor="name">ChatBot Name</label>

                                <input  className="form-control" id="name" onChange={handleChange} />

                                {errors.name && touched.name && (
                                    <div className="form-feedback-item mt-2 text-2 text-danger">
                                        {errors.name.toUpperCase()}
                                    </div>
                                )}
                            </ClayForm.Group>
                            <ClayForm.Group className="form-group-sm">
                                <label htmlFor="contextClauseField">Input File Field</label>

                                <select
                                    className="form-control"
                                    id="contextClauseField"
                                    onChange={handleChange}
                                >
                                    <option value={-1}>Select Clause Field</option>

                                    {selectedObject.objectFields.map((field) => (
                                        <option key={field.id} value={field.name}>
                                            {field.name.toUpperCase()}
                                        </option>
                                    ))}
                                </select>

                                {errors.contextClauseField && touched.contextClauseField && (
                                    <div className="form-feedback-item mt-2 text-2 text-danger">
                                        {errors.contextClauseField.toUpperCase()}
                                    </div>
                                )}
                            </ClayForm.Group>
                        </form>
                    </>
                )}

            </>
        )}
        {isLoading && (
            <>
                <ClayAlert displayType="info"  title="Loading" role={null}>
                <span>
                    Loading available object definitions...
                </span>
                </ClayAlert>
                <ClayLoadingIndicator displayType="secondary" size="sm" />
            </>
        )}
        {!isLoading && objectDefinitions && objectDefinitions.length <= 0 && (
            <ClayAlert displayType="warning"  title="No Objects Definitions" role={null}>
                There are no object definitions available or configured. Please ensure that the necessary objects are defined before proceeding.
            </ClayAlert>
        )}
    </>

});

export default AddObject;
