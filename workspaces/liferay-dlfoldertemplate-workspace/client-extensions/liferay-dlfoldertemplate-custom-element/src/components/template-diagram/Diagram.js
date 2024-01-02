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

const dagreGraph = new dagre.graphlib.Graph();

dagreGraph.setDefaultEdgeLabel(() => ({}));

const nodeWidth = 172;

const nodeHeight = 36;

const position = {x: 0, y: 0};

const edgeType = 'SmoothStep';

const getLayoutedElements = (nodes, edges, direction = 'TB') => {
	dagreGraph.setGraph({
		rankdir: direction,
	});

	nodes.forEach((node) => {
		dagreGraph.setNode(node.id, {height: nodeHeight, width: nodeWidth});
	});

	edges.forEach((edge) => {
		dagreGraph.setEdge(edge.source, edge.target);
	});

	dagre.layout(dagreGraph);
	nodes.forEach((node) => {
		const nodeWithPosition = dagreGraph.node(node.id);

		node.targetPosition = 'top';

		node.sourcePosition = 'bottom';

		node.draggable = false;

		node.position = {
			x: nodeWithPosition.x - nodeWidth / 2,

			y: nodeWithPosition.y - nodeHeight / 2,
		};

		return node;
	});

	return {edges, nodes};
};

const getNodesFromHeadless = async (templateId) => {
	const dBNodesPage = await getAvailableTemplatesNodesPage(templateId);

	const dBNodes = dBNodesPage.items;

	if (!dBNodes.length) {
		const root = await addNode(0, true, 'Root Node', templateId);
		dBNodes.push(root);
	}

	const dbNormalizedNodes = dBNodes.map((node) => {
		return {
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
			position,
			type: node.root ? 'folderNode' : 'folderNode',
		};
	});

	const dBEdges = [];

	dBNodes.forEach((node) => {
		if (node.parentID !== 0) {
			const edge = {
				animated: false,
				id: `e${node.id}${node.parentID}`,
				source: `${node.parentID}`,
				target: `${node.id}`,
				type: edgeType,
			};
			dBEdges.push(edge);
		}
	});

	return getLayoutedElements(dbNormalizedNodes, dBEdges);
};

const getSubNodes = (parentNodeId) => {
	const subNodes = [];

	const checkSubNodes = (parentNode) => {
		const childrenNodes = dagreGraph.successors(parentNode);

		subNodes.push(parentNode);

		if (childrenNodes && childrenNodes.length) {
			childrenNodes.forEach((node) => {
				checkSubNodes(node);
			});
		}
	};

	checkSubNodes(parentNodeId);

	return subNodes;
};

const deleteNodes = async (nodes) => {
	const liferayNodes = nodes.map((node) => {
		return {
			id: node,
		};
	});

	await deleteFolderTemplateBatch(liferayNodes);
};

const addNewNode = async (parentNodeId, templateId, nodes, edges) => {
	const newNode = await addNode(parentNodeId, false, 'New Node', templateId);

	const newDiagramNode = {
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
		position,
		type: newNode.root ? 'folderNode' : 'folderNode',
	};

	const newDiagramEdge = {
		animated: false,
		id: `e${newDiagramNode.id}${parentNodeId}`,
		source: `${parentNodeId}`,
		target: `${newDiagramNode.id}`,
		type: edgeType,
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

	const onNodeSelect = (data) => {
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

	const onPaneClick = () => {
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

	const onAdd = useCallback(
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

	const onDelete = useCallback(
		(params) => {
			try {
				const nodeId = params[0].id || params || selectedNode.id;

				const nodesToBeDeleted = getSubNodes(nodeId);

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

	const onSave = () => {
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
								onAdd={onAdd}
								onDelete={onDelete}
								onSelect={onNodeSelect}
							/>
						),
					}}
					nodes={nodes}
					onConnect={null}
					onNodesDelete={onDelete}
					onPaneClick={onPaneClick}
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
													onPaneClick(null);
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
												onDelete(selectedNode.nodeId);
											}}
										>
											Delete
										</ClayButton>
									)}
									<ClayButton
										disabled={isLoading}
										onClick={() => {
											onSave();
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
