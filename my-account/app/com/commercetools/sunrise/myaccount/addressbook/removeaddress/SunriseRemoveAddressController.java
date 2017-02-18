package com.commercetools.sunrise.myaccount.addressbook.removeaddress;

import com.commercetools.sunrise.common.pages.PageContent;
import com.commercetools.sunrise.common.template.engine.TemplateRenderer;
import com.commercetools.sunrise.controllers.SunriseTemplateFormController;
import com.commercetools.sunrise.controllers.WithTemplateFormFlow;
import com.commercetools.sunrise.framework.hooks.RunRequestStartedHook;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.AddressBookReverseRouter;
import com.commercetools.sunrise.myaccount.CustomerFinder;
import com.commercetools.sunrise.myaccount.WithRequiredCustomer;
import com.commercetools.sunrise.myaccount.addressbook.AddressFinder;
import com.commercetools.sunrise.myaccount.addressbook.AddressWithCustomer;
import com.commercetools.sunrise.myaccount.addressbook.WithRequiredAddress;
import com.commercetools.sunrise.myaccount.addressbook.addresslist.view.AddressBookPageContentFactory;
import io.sphere.sdk.customers.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public abstract class SunriseRemoveAddressController<F extends RemoveAddressFormData> extends SunriseTemplateFormController implements WithTemplateFormFlow<F, AddressWithCustomer, Customer>, WithRequiredCustomer, WithRequiredAddress {

    private final CustomerFinder customerFinder;
    private final AddressFinder addressFinder;
    private final RemoveAddressExecutor removeAddressExecutor;
    private final AddressBookPageContentFactory addressBookPageContentFactory;

    protected SunriseRemoveAddressController(final TemplateRenderer templateRenderer, final FormFactory formFactory,
                                             final CustomerFinder customerFinder, final AddressFinder addressFinder,
                                             final RemoveAddressExecutor removeAddressExecutor,
                                             final AddressBookPageContentFactory addressBookPageContentFactory) {
        super(templateRenderer, formFactory);
        this.customerFinder = customerFinder;
        this.addressFinder = addressFinder;
        this.removeAddressExecutor = removeAddressExecutor;
        this.addressBookPageContentFactory = addressBookPageContentFactory;
    }

    @Override
    public CustomerFinder getCustomerFinder() {
        return customerFinder;
    }

    @Override
    public AddressFinder getAddressFinder() {
        return addressFinder;
    }

    @RunRequestStartedHook
    @SunriseRoute(AddressBookReverseRouter.REMOVE_ADDRESS_PROCESS)
    public CompletionStage<Result> process(final String languageTag, final String addressId) {
        return requireCustomer(customer ->
                requireAddress(customer, addressId,
                        address -> processForm(AddressWithCustomer.of(address, customer))));
    }

    @Override
    public CompletionStage<Customer> executeAction(final AddressWithCustomer addressWithCustomer, final F formData) {
        return removeAddressExecutor.apply(addressWithCustomer, formData);
    }

    @Override
    public abstract CompletionStage<Result> handleSuccessfulAction(final Customer updatedCustomer, final F formData);

    @Override
    public void preFillFormData(final AddressWithCustomer input, final F formData) {
        // Do not pre-fill anything
    }

    @Override
    public PageContent createPageContent(final AddressWithCustomer addressWithCustomer, final Form<F> form) {
        return addressBookPageContentFactory.create(addressWithCustomer.getCustomer());
    }
}
