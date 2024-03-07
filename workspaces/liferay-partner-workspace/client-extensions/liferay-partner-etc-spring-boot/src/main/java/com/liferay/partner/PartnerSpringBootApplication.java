/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Jair Medeiros
 * @author Thaynam Lazaro
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class PartnerSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnerSpringBootApplication.class, args);
	}

}