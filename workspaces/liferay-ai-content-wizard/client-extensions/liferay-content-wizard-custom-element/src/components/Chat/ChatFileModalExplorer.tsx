import { TreeView } from '@clayui/core';
import ClayIcon from '@clayui/icon';
import { useMemo } from 'react';

const ChatFileModalExplorer = ({ items, setSelectedTree }: any) => {
    const documentAndFolders = useMemo(() => {
        const folderStructure = {} as any;

        for (const item of items) {
            const folderId = item.folder.name;
            const folder = folderStructure[folderId];

            if (folder) {
                folderStructure[folderId].push(item);
            } else {
                folderStructure[folderId] = [item];
            }
        }

        const newItems = [];

        for (const folder in folderStructure) {
            const documents = folderStructure[folder] as any[];

            newItems.push({
                children: documents.map((document) => ({
                    image: document.contentUrl,
                    name: document.fileName,
                    id: document.id,
                })),
                name: folder,
                type: 'folder',
            });
        }

        return newItems;
    }, [items]);

    return (
        <TreeView
            defaultItems={documentAndFolders}
            nestedKey="children"
            selectionMode="single"
        >
            {(item) => (
                <TreeView.Item>
                    <TreeView.ItemStack>
                        <ClayIcon
                            aria-label="Stack icon"
                            symbol={item.type ? item.type : 'folder'}
                        />
                        {item.name}
                    </TreeView.ItemStack>
                    <TreeView.Group items={item.children}>
                        {(child) => (
                            <TreeView.Item
                                onClick={() => setSelectedTree(child)}
                            >
                                {child.image && (
                                    <img
                                        width={18}
                                        height={18}
                                        className="rounded-circle"
                                        src={child.image}
                                    />
                                )}

                                {child.name}
                            </TreeView.Item>
                        )}
                    </TreeView.Group>
                </TreeView.Item>
            )}
        </TreeView>
    );
};

export default ChatFileModalExplorer;
