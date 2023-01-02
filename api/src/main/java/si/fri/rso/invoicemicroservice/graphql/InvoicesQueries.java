package si.fri.rso.invoicemicroservice.graphql;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.invoicemicroservice.lib.Invoice;
import si.fri.rso.invoicemicroservice.services.beans.InvoiceBean;

@GraphQLClass
@ApplicationScoped
public class InvoicesQueries {
    @Inject
    private InvoiceBean invoiceBean;

    @GraphQLQuery
    public PaginationWrapper<Invoice> getAllinvocies(@GraphQLArgument(name = "pagination") Pagination pagination,
            @GraphQLArgument(name = "sort") Sort sort,
            @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(invoiceBean.getInvoices(), pagination, sort, filter);
    }

    @GraphQLQuery
    public List<Invoice> getUserInvoices(@GraphQLArgument(name = "userId") Integer userId) {
        return invoiceBean.getUserInvoices(userId);
    }
}
