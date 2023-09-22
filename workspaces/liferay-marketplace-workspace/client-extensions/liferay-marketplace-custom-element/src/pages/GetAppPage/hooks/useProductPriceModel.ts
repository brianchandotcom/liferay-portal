/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { useEffect, useState } from "react";

const useProductPriceModel = (product: Product | undefined) => {
    const [isFreeApp, setIsFreeApp] = useState<boolean>(false);

    useEffect(() => {
        const productPriceModel = product?.productSpecifications?.filter(
            (specification) => specification?.specificationKey === "price-model"
          );
      
          productPriceModel?.map((priceModel) => {
            if (priceModel.value.en_US.toLowerCase() === "free") {
              setIsFreeApp(true);
            }
          });
    }, [product?.productSpecifications])

    return {
        isFreeApp
    }
}

export default useProductPriceModel;