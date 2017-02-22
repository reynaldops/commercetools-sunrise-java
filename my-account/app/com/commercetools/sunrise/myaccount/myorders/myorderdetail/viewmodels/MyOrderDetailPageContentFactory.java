package com.commercetools.sunrise.myaccount.myorders.myorderdetail.viewmodels;

import com.commercetools.sunrise.common.models.PageContentFactory;
import com.commercetools.sunrise.common.utils.PageTitleResolver;
import com.commercetools.sunrise.myaccount.myorders.myorderdetail.OrderWithCustomer;
import com.commercetools.sunrise.common.models.carts.OrderViewModelFactory;

import javax.inject.Inject;

public class MyOrderDetailPageContentFactory extends PageContentFactory<MyOrderDetailPageContent, OrderWithCustomer> {

    private final PageTitleResolver pageTitleResolver;
    private final OrderViewModelFactory orderViewModelFactory;

    @Inject
    public MyOrderDetailPageContentFactory(final PageTitleResolver pageTitleResolver, final OrderViewModelFactory orderViewModelFactory) {
        this.pageTitleResolver = pageTitleResolver;
        this.orderViewModelFactory = orderViewModelFactory;
    }

    @Override
    protected MyOrderDetailPageContent getViewModelInstance() {
        return new MyOrderDetailPageContent();
    }

    @Override
    public final MyOrderDetailPageContent create(final OrderWithCustomer orderWithCustomer) {
        return super.create(orderWithCustomer);
    }

    @Override
    protected final void initialize(final MyOrderDetailPageContent viewModel, final OrderWithCustomer orderWithCustomer) {
        super.initialize(viewModel, orderWithCustomer);
        fillOrder(viewModel, orderWithCustomer);
    }

    @Override
    protected void fillTitle(final MyOrderDetailPageContent viewModel, final OrderWithCustomer orderWithCustomer) {
        viewModel.setTitle(pageTitleResolver.getOrEmpty("myAccount:myOrderDetailPage.title"));
    }

    protected void fillOrder(final MyOrderDetailPageContent viewModel, final OrderWithCustomer orderWithCustomer) {
        viewModel.setOrder(orderViewModelFactory.create(orderWithCustomer.getOrder()));
    }
}
