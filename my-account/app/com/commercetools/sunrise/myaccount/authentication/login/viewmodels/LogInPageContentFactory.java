package com.commercetools.sunrise.myaccount.authentication.login.viewmodels;

import com.commercetools.sunrise.common.utils.PageTitleResolver;
import com.commercetools.sunrise.myaccount.authentication.AbstractAuthenticationPageContentFactory;
import com.commercetools.sunrise.myaccount.authentication.AuthenticationPageContent;
import com.commercetools.sunrise.myaccount.authentication.login.LogInFormData;
import com.commercetools.sunrise.myaccount.authentication.signup.viewmodels.SignUpFormSettingsViewModelFactory;
import play.data.Form;

import javax.inject.Inject;

public class LogInPageContentFactory extends AbstractAuthenticationPageContentFactory<LogInFormData> {

    private final SignUpFormSettingsViewModelFactory signUpFormSettingsViewModelFactory;

    @Inject
    public LogInPageContentFactory(final PageTitleResolver pageTitleResolver, final SignUpFormSettingsViewModelFactory signUpFormSettingsViewModelFactory) {
        super(pageTitleResolver);
        this.signUpFormSettingsViewModelFactory = signUpFormSettingsViewModelFactory;
    }

    @Override
    protected void fillLogInForm(final AuthenticationPageContent viewModel, final Form<? extends LogInFormData> form) {
        viewModel.setLogInForm(form);
    }

    @Override
    protected void fillSignUpForm(final AuthenticationPageContent viewModel, final Form<? extends LogInFormData> form) {
        viewModel.setSignUpForm(null);
    }

    @Override
    protected void fillSignUpFormSettings(final AuthenticationPageContent viewModel, final Form<? extends LogInFormData> form) {
        viewModel.setSignUpFormSettings(signUpFormSettingsViewModelFactory.create(null));
    }
}
