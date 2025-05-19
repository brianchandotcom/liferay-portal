import {forwardRef, useEffect, useState} from "react";
import {getFolderFiles, getFolderSubfolders, getRootFiles, getRootFolders} from "../../services/delivery/document";
import {ClayCardWithHorizontal} from "@clayui/card";
import ClayLayout from '@clayui/layout';

const AssetSelector = forwardRef((props, ref) => {

    const {handleSendAttachment} = props;

    const [files, setFiles] = useState([]);
    const [folders, setFolders] = useState([]);
    const [path, setPath] = useState([]);

    const handleOpenFolder = (folder) => {

        setPath(prevPath => [...prevPath, folder]);

    }

    const handleGoToFolder = (folderIndex) => {

        setPath(prevPath => [...(prevPath.splice(0,folderIndex+1))]);

    }

    const loadFolder = async () => {

        if (path.length > 0 ){

            let currentFolder = path[path.length -1];

            if (currentFolder.id === 0){

                let currentFolders = await getRootFolders();
                let currentFiles = await getRootFiles();

                setFolders(currentFolders.items);
                setFiles(currentFiles.items);

            }else{
                let currentFolder = path[path.length -1];
                let currentFolders = await getFolderSubfolders(currentFolder.id);
                let currentFiles = await getFolderFiles(currentFolder.id);

                setFolders(currentFolders.items);
                setFiles(currentFiles.items);

            }

        }

    }

    useEffect(()=>{

        loadFolder();

    },[path])

    useEffect(()=>{

        setPath([{
            name:"Root",
            id: 0
        }]);

    },[])

    return (
        <div className={"asset-selector"}>
            <ClayLayout.ContainerFluid>
                <ClayLayout.Row justify="start">
                    <ClayLayout.Col size={12}>
                        <ol className="breadcrumb">
                            {path && path.length > 0 && path.map((item, index) =>
                                <li key={`folder_${index}`} onClick={()=>handleGoToFolder(index)} role="button" className={`breadcrumb-item breadcrumb-link ${index === path.length -1 ? "active" : ""}`}>
                                    <span className="breadcrumb-text-truncate" title={item.name}>{item.name}</span>
                                </li>
                            )}

                        </ol>
                    </ClayLayout.Col>
                </ClayLayout.Row>
                <ClayLayout.Row justify="start">
                    {folders && folders.length > 0 && folders.map((folder, index) =>
                        <ClayLayout.Col size={4}>
                            <ClayCardWithHorizontal onClick={() => handleOpenFolder(folder)} title={folder.name}/>
                        </ClayLayout.Col>
                    )}
                    {files && files.length > 0 && files.map((file, index) =>
                        <ClayLayout.Col size={4}>
                            <ClayCardWithHorizontal onClick={()=>handleSendAttachment(file)} symbol={"document-default"} title={file.title}/>
                        </ClayLayout.Col>
                    )}
                </ClayLayout.Row>
            </ClayLayout.ContainerFluid>


        </div>
    );

});

export default AssetSelector;
