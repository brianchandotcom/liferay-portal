import {forwardRef, useCallback, useEffect, useImperativeHandle, useState} from "react";

import Prism from "prismjs";
import "prismjs/themes/prism.css"; // You can customize the theme

const CodeWindow = forwardRef((props, ref) => {

    let {entry} = props;


    useImperativeHandle(ref, () => ({

    }));

    useEffect(()=>{

        Prism.highlightAll();

    },[])

    return (
        <>
            {entry.configuration.actionsList.map((item, index) => (
                <div key={index}>
                    <strong>
                        {item.type}
                    </strong>
                    <pre className={`language-javascript`} style={{ backgroundColor: "#f4f4f4", padding: "15px", borderRadius: "5px" }} key={index}>
                    <code className="language-javascript">
                        {
                            `window.addEventListener("${item.name}", (event) => {\n\talert("Event received with data:" + JSON.stringify( event.detail) );\n});`
                        }
                    </code>
                </pre>
                </div>

            ))}
        </>
    );

});

export default CodeWindow;
