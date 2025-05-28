import useSWR from "swr";
import HeadlessCommerceAdminCatalog from "../../../services/rest/HeadlessCommerceAdminCatalog";
import SearchBuilder from "../../../core/SearchBuilder";
import { addDays } from 'date-fns';
import { ProductTypeVocabulary, ProductWorkflowStatusCode } from "../../../enums/Product";


export const METRIC_PARAMETER = {
    month: 30,
    q1: 1,
    q2: 2,
    q3: 3,
    q4: 4,
    week: 7,
};

type FilterType = 'month' | 'q1' | 'q2' | 'q3' | 'q4' | 'week';

const useAppsMetricks = (param: FilterType = 'week') => {
    const currentTime = new Date();

    const beforeLastPeriod = addDays(
        currentTime,
        -METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER] * 2
    );

    const lastPeriod = addDays(
        currentTime,
        -METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
    );

    beforeLastPeriod.setHours(0, 0, 0);
    lastPeriod.setHours(23, 59, 59);

    const searchBuilder = new SearchBuilder().lambda(
        'categoryNames',
        ProductTypeVocabulary.APP
    );

    const filters = {
        products: searchBuilder.clone().build(),
        inReview: searchBuilder.clone().and().in('statusCode', [1, 2], { unquote: true }).build(),
        inReviewLastlastweek: searchBuilder.clone().and().in('statusCode', [ProductWorkflowStatusCode.PENDING, ProductWorkflowStatusCode.DRAFT], { unquote: true }).and().gt('createDate', lastPeriod.toISOString()).build(),
        inReviewBeforeLastWeek: searchBuilder.clone().and().in('statusCode', [ProductWorkflowStatusCode.PENDING, ProductWorkflowStatusCode.DRAFT], { unquote: true }).and().lt('createDate', lastPeriod.toISOString()).and().gt('createDate', beforeLastPeriod.toISOString()).build(),
        approved: searchBuilder.clone().and().lambda('statusCode', ProductWorkflowStatusCode.APPROVED, { unquote: true }).build(),
        approvedLastWeek: searchBuilder.clone().and().lambda('statusCode', ProductWorkflowStatusCode.APPROVED, { unquote: true }).and().gt('createDate', lastPeriod.toISOString()).build(),
        approvedBeforeLastWeek: searchBuilder.clone().and().lambda('statusCode', ProductWorkflowStatusCode.APPROVED, { unquote: true }).and().lt('createDate', lastPeriod.toISOString()).and().gt('createDate', beforeLastPeriod.toISOString()).build()
    }

    const query = `{
                headlessCommerceAdminCatalog_v1_0  {
                    products : products(filter: "${filters.products}") {
                        totalCount
                    },
                    inReview : products(filter: "${filters.inReview}") {
                        totalCount
                    },
                    inReviewLastlastweek: products(filter: "${filters.inReviewLastlastweek}") {
                        totalCount
                    },
                    inReviewBeforeLastWeek: products(filter: "${filters.inReviewBeforeLastWeek}") {
                        totalCount
                    },
                    approved: products(filter: "${filters.approved}") {
                        totalCount
                    },
                    approvedLastWeek: products(filter: "${filters.approvedLastWeek}") {
                        totalCount
                    },
                    approvedBeforeLastWeek: products(filter: "${filters.approvedBeforeLastWeek}") {
                        totalCount
                    }
                   
                }
            }`

    const response = useSWR('marketplace/getProductsInfocardKPI', async () => await HeadlessCommerceAdminCatalog.getProductsInfocardKPI(query))

    return {
        products: response?.data?.headlessCommerceAdminCatalog_v1_0.products?.totalCount || 0,
        inReview: response?.data?.headlessCommerceAdminCatalog_v1_0.inReview?.totalCount || 0,
        inreviewLastlastweek: response?.data?.headlessCommerceAdminCatalog_v1_0.inReviewLastlastweek?.totalCount || 0,
        inreviewBeforeLastWeek: response?.data?.headlessCommerceAdminCatalog_v1_0.inReviewBeforeLastWeek?.totalCount || 0,
        approved: response?.data?.headlessCommerceAdminCatalog_v1_0.approved?.totalCount || 0,
        approvedLastWeek: response?.data?.headlessCommerceAdminCatalog_v1_0.approvedLastWeek?.totalCount || 0,
        approvedBeforeLastWeek: response?.data?.headlessCommerceAdminCatalog_v1_0.approvedBeforeLastWeek?.totalCount || 0,
        isLoading: response.isValidating,
        isError: response.error,
    }
}

export default useAppsMetricks; 