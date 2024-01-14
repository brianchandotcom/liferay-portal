/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dagre from 'dagre';
import React, {useCallback, useEffect, useState} from 'react';
import ReactFlow, {
	Background,
	ConnectionLineType,
	Controls,
	Panel,
	useEdgesState,
	useNodesState,
} from 'reactflow';

import 'reactflow/dist/style.css';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {Form, Input} from 'antd';

import {
	addNode,
	deleteFolderTemplateBatch,
	getAvailableTemplatesNodesPage,
	updateFolderTemplate,
} from '../../services/TemplateDiagramService';
import {showError} from '../../utils/util';
import FolderNode from './controls/custom-node/FolderNode';

import './Diagram.css';

const DAGRE_GRAPH = new dagre.graphlib.Graph();

DAGRE_GRAPH.setDefaultEdgeLabel(() => ({}));

const NODE_WIDTH = 172;

const NODE_HEIGHT = 36;

const POSITION = {x: 0, y: 0};

const EDGE_TYPE = 'SmoothStep';

const getLayoutedElements = (nodes, edges) => {
	DAGRE_GRAPH.setGraph({
		rankdir: 'TB',
	});

	nodes.forEach((node) => {
		DAGRE_GRAPH.setNode(node.id, {height: NODE_HEIGHT, width: NODE_WIDTH});
	});

	edges.forEach((edge) => {
		DAGRE_GRAPH.setEdge(edge.source, edge.target);
	});

	dagre.layout(DAGRE_GRAPH);

	nodes.forEach((node) => {
		const nodeWithPosition = DAGRE_GRAPH.node(node.id);

		node.targetPosition = 'top';

		node.sourcePosition = 'bottom';

		node.draggable = false;

		node.position = {
			x: nodeWithPosition.x - NODE_WIDTH / 2,

			y: nodeWithPosition.y - NODE_HEIGHT / 2,
		};

		return node;
	});

	return {edges, nodes};
};

const getNodesFromHeadless = async (templateId) => {
	const templateNodesPage = await getAvailableTemplatesNodesPage(templateId);

	const templateNodes = templateNodesPage.items;

	if (!templateNodes.length) {
		const rootNode = await addNode(0, true, 'Root Node', templateId);
		templateNodes.push(rootNode);
	}

	const normalizedNodes = templateNodes.map((node) => {
		return {
			POSITION,
			data: {
				description: node.description,
				label: node.name,
				nodeId: `${node.id}`,
				parent: node.root ? null : `${node.parentID}`,
				root: node.root,
			},
			deletable: !node.root,
			id: `${node.id}`,
			parent: node.root ? null : `${node.parentID}`,
			type: node.root ? 'folderNode' : 'folderNode',
		};
	});

	const templateEdges = [];

	templateNodes.forEach((node) => {
		if (node.parentID !== 0) {
			const edge = {
				animated: false,
				id: `e${node.id}${node.parentID}`,
				source: `${node.parentID}`,
				target: `${node.id}`,
				type: EDGE_TYPE,
			};
			templateEdges.push(edge);
		}
	});

	return getLayoutedElements(normalizedNodes, templateEdges);
};

const getChildNodeIds = (nodeId) => {
	const subNodes = [];

	const checkSubNodes = (parentNode) => {
		const childrenNodes = DAGRE_GRAPH.successors(parentNode);

		subNodes.push(parentNode);

		if (childrenNodes && childrenNodes.length) {
			childrenNodes.forEach((node) => {
				checkSubNodes(node);
			});
		}
	};

	checkSubNodes(nodeId);

	return subNodes;
};

const deleteNodes = async (nodeIds) => {
	await deleteFolderTemplateBatch(
		nodeIds.map((nodeId) => {
			return {
				id: nodeId,
			};
		})
	);
};

const addNewNode = async (parentNodeId, templateId, nodes, edges) => {
	const newNode = await addNode(parentNodeId, false, 'New Node', templateId);

	const newDiagramNode = {
		POSITION,
		data: {
			description: newNode.description,
			label: newNode.name,
			nodeId: `${newNode.id}`,
			parent: newNode.root ? null : `${parentNodeId}`,
			root: newNode.root,
		},
		deletable: !newNode.root,
		id: `${newNode.id}`,
		parent: newNode.root ? null : `${parentNodeId}`,
		type: newNode.root ? 'folderNode' : 'folderNode',
	};

	const newDiagramEdge = {
		animated: false,
		id: `e${newDiagramNode.id}${parentNodeId}`,
		source: `${parentNodeId}`,
		target: `${newDiagramNode.id}`,
		type: EDGE_TYPE,
	};

	nodes.push(newDiagramNode);

	edges.push(newDiagramEdge);

	return getLayoutedElements(nodes, edges);
};

const Diagram = ({key, templateId}) => {
	const [nodes, setNodes] = useNodesState(null);

	const [edges, setEdges] = useEdgesState(null);

	const [selectedNode, setSelectedNode] = useState();
	const [isLoading, setIsLoading] = useState(false);

	const [form] = Form.useForm();

	const handleNodeSelect = (data) => {
		form.setFieldsValue({
			description: data.description,
			id: data.nodeId,
			name: data.label,
			parentID: data.parent,
			root: data.root,
			templateID: templateId,
		});
		setSelectedNode(data);
	};

	const handlePaneClick = () => {
		setSelectedNode(null);
	};

	const updateDiagramDataSourceLocally = useCallback(
		(currentNodes, currentEdges, idsToExclude) => {
			const filteredNodes = currentNodes.filter(
				(node) => !idsToExclude.includes(node.id)
			);

			const filteredEdges = currentEdges.filter(
				(edge) => !idsToExclude.includes(edge.source)
			);

			const {
				edges: layoutedEdges,
				nodes: layoutedNodes,
			} = getLayoutedElements(filteredNodes, filteredEdges);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[setNodes, setEdges]
	);

	const updateDiagramSingleNodeLocally = useCallback(
		(updatedNode) => {
			const selectedDiagramNode = nodes.filter(
				(node) => node.id.toString() === updatedNode.id
			);

			if (selectedDiagramNode && !!selectedDiagramNode.length) {
				selectedDiagramNode[0].data.description =
					updatedNode.description;
				selectedDiagramNode[0].data.label = updatedNode.name;
			}
		},
		[nodes]
	);

	const handleAdd = useCallback(
		(parentNodeId) => {
			const addNodeAsync = async () => {
				const {
					edges: layoutedEdges,
					nodes: layoutedNodes,
				} = await addNewNode(parentNodeId, templateId, nodes, edges);

				setNodes([...layoutedNodes]);

				setEdges([...layoutedEdges]);
			};

			addNodeAsync();
		},
		[nodes, edges, setNodes, setEdges, templateId]
	);

	const handleDelete = useCallback(
		(params) => {
			try {
				const nodeId = params[0].id || params || selectedNode.id;

				const nodesToBeDeleted = getChildNodeIds(nodeId);

				updateDiagramDataSourceLocally(nodes, edges, nodesToBeDeleted);

				deleteNodes(nodesToBeDeleted);

				setSelectedNode(null);
			}
			catch (error) {
				showError(error.message);
			}
		},
		[nodes, edges, updateDiagramDataSourceLocally, selectedNode]
	);

	const loadNodes = useCallback(
		async (templateId) => {
			const {
				edges: layoutedEdges,
				nodes: layoutedNodes,
			} = await getNodesFromHeadless(templateId);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[setEdges, setNodes]
	);

	useEffect(() => {
		loadNodes(templateId);
	}, [templateId, loadNodes]);

	const handleSave = () => {
		form.validateFields()
			.then(
				async (values) => {
					try {
						setIsLoading(true);

						await updateFolderTemplate(values.id, values);

						setIsLoading(false);

						updateDiagramSingleNodeLocally(values);
					}
					catch (error) {
						setIsLoading(false);

						showError(error.message);
					}
				},
				(error) => {
					showError(error.message);
				}
			)
			.catch((error) => {
				showError(error.message);
			});
	};

	return (
		<>
			{nodes && edges && (
				<ReactFlow
					connectionLineType={ConnectionLineType.SmoothStep}
					edges={edges}
					fitView
					key={key}
					nodeTypes={{
						folderNode: (props) => (
							<FolderNode
								{...props}
								onAdd={handleAdd}
								onDelete={handleDelete}
								onSelect={handleNodeSelect}
							/>
						),
					}}
					nodes={nodes}
					onConnect={null}
					onNodesDelete={handleDelete}
					onPaneClick={handlePaneClick}
				>
					<Controls />
					<Background className="background" />
					{selectedNode && (
						<Panel className="side-panel" position="top-right">
							<div className="sidebar">
								<div className="border-bottom sidebar-header">
									<div className="autofit-row sidebar-section">
										<div className="autofit-col autofit-col-expand">
											<div className="component-title mb-auto mt-auto">
												<span className="text-truncate-inline">
													{selectedNode.label}
												</span>
											</div>
										</div>
										<div className="autofit-col">
											<ClayButtonWithIcon
												aria-label="Close"
												displayType="unstyled"
												onClick={() => {
													handlePaneClick(null);
												}}
												symbol="times"
												title="Close"
											/>
										</div>
									</div>
								</div>
								<div className="sidebar-body">
									<Form
										autoComplete="off"
										form={form}
										layout="vertical"
									>
										<Form.Item
											initialValue={selectedNode.label}
											label="Title"
											name="name"
											rules={[
												{
													message:
														'Please provide node name.',
													required: true,
												},
											]}
										>
											<ClayInput />
										</Form.Item>
										<Form.Item
											initialValue={
												selectedNode.description
											}
											label="Description"
											name="description"
										>
											<ClayInput
												component="textarea"
												type="text"
											/>
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={selectedNode.parent}
											label="parentID"
											name="parentID"
											rules={[
												{
													message:
														'Please provide node parent id.',
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={templateId}
											label="templateID"
											name="templateID"
											rules={[
												{
													message:
														'Please provide a template id.',
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={selectedNode.root}
											label="root"
											name="root"
											rules={[
												{
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={selectedNode.id}
											label="id"
											name="id"
											rules={[
												{
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
									</Form>
								</div>
								<div className="actions fixed-bottom sidebar-footer">
									{!selectedNode.root && (
										<ClayButton
											disabled={isLoading}
											displayType="danger"
											onClick={() => {
												handleDelete(
													selectedNode.nodeId
												);
											}}
										>
											Delete
										</ClayButton>
									)}
									<ClayButton
										disabled={isLoading}
										onClick={() => {
											handleSave();
										}}
									>
										Save
									</ClayButton>
								</div>
							</div>
						</Panel>
					)}
				</ReactFlow>
			)}
		</>
	);
};

export default Diagram;
