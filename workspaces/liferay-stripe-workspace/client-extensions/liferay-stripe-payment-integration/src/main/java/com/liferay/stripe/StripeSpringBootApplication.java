/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stripe;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Crescenzo Rega
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class StripeSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripeSpringBootApplication.class, args);
	}

}