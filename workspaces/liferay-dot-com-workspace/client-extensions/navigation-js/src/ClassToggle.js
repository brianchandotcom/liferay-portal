/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {default as delegate} from './delegate.es';

class ClassToggle {
	constructor() {
		this.activateCallback = null;
		this.baseClassName = 'class-toggle';
		this.deactivateCallback = null;
		this.item = '.class-toggle';
		this.offclickContent = null;
		this.targetClass = null;
		this.targetNodes = null;
		this.toggleType = null;
		this.trigger = null;

		this.syncUI();
	}

	syncUI = function () {
		const instance = this;

		let wrapper = document.getElementById('wrapper');

		if (!wrapper) {
			wrapper = document.querySelector('body');
		}

		let trigger = this.trigger;

		if (!trigger) {
			trigger = this.item;
		}

		delegate(wrapper, 'click', trigger, (event) => {
			let node = event.delegateTarget;

			if (!node) {
				node = event.currentTarget;
			}

			const targetClass = instance._getTargetClass(node);
			const targetNodes = instance._getTargetNodes(node);

			let active = false;

			if (targetNodes[0].classList.contains(targetClass)) {
				active = true;
			}

			let toggleType = this.toggleType;

			if (node.dataset.toggleType) {
				toggleType = node.dataset.toggleType;
			}

			if (toggleType === 'offclick') {
				if (active) {
					return;
				}

				instance._activate(node, targetNodes, targetClass);

				instance._offclickAction(node, targetNodes, targetClass, event);
			}
			else if (toggleType === 'carousel') {
				if (active) {
					return;
				}

				instance._carouselAction(node, targetNodes, targetClass);

				instance._activate(node, targetNodes, targetClass);
			}
			else if (active) {
				instance._deactivate(node, targetNodes, targetClass);
			}
			else {
				instance._activate(node, targetNodes, targetClass);
			}
		});
	};

	_activate = function (node, targetNodes, targetClass) {
		const instance = this;

		for (const targetNode in targetNodes) {
			targetNode.classList.add(targetClass);
		}

		const activateCallback = this.activateCallback;

		if (activateCallback instanceof Function) {
			activateCallback(instance, node, targetNodes, targetClass);
		}
	};

	_carouselAction = function (node, targetNodes) {
		const instance = this;

		const curActiveNode = targetNodes[0]._activeNode;

		if (curActiveNode && curActiveNode !== node) {
			instance._deactivate(
				curActiveNode,
				instance._getTargetNodes(curActiveNode),
				instance._getTargetClass(curActiveNode)
			);
		}

		targetNodes[0]._activeNode = node;
	};

	_deactivate = function (node, targetNodes, targetClass) {
		const instance = this;

		for (const targetNode in targetNodes) {
			targetNode.classList.remove(targetClass);
		}

		const deactivateCallback = this.deactivateCallback;

		if (deactivateCallback instanceof Function) {
			deactivateCallback(instance, node, targetNodes, targetClass);
		}
	};

	_getTargetClass = function (node) {
		let className = this.baseClassName + '-active';

		if (node.dataset.targetClass) {
			className = node.dataset.targetClass;
		}
		else if (this.targetClass) {
			className = this.targetClass;
		}

		return className;
	};

	_getTargetNodes = function (node) {
		let nodes;

		if (node.dataset.targetNodes) {
			nodes = document.querySelectorAll(node.dataset.targetNodes);
		}
		else if (this.targetNodes) {
			nodes = document.querySelectorAll(this.targetNodes);
		}

		if (!nodes || !nodes.length) {
			return [node];
		}

		return nodes;
	};

	_offclickAction = function (node, targetNodes, targetClass, event) {
		const instance = this;

		event.stopPropagation();

		let nodeContent;

		if (node.dataset.offclickContent) {
			nodeContent = document.querySelectorAll(
				node.dataset.offclickContent
			);
		}
		else if (node.dataset.targetNodes) {
			const offclickContent =
				node.dataset.targetNodes +
				' .' +
				this.baseClassName +
				'-content';

			nodeContent = document.querySelectorAll(offclickContent);
		}
		else if (this.offclickContent) {
			nodeContent = document.querySelectorAll(this.offclickContent);
		}
		else {
			nodeContent = node.querySelectorAll(
				'.' + this.baseClassName + '-content'
			);
		}

		if (!nodeContent || !nodeContent.length) {
			nodeContent = [node];
		}

		function handleOffclick(event) {
			for (let i = 0; i < nodeContent.length; i++) {
				if (!nodeContent[i].contains(event.target)) {
					instance._deactivate(node, targetNodes, targetClass);
					document.removeEventListener('click', handleOffclick);
				}
			}
		}
		document.addEventListener('click', handleOffclick);
	};
}
export default ClassToggle;
