package io.pivotal;

import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

public class CreateAccountFlowExecutionTest extends AbstractXmlFlowExecutionTests {

    private AccountRepository accountRepository;

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
        return resourceFactory.createClassPathResource("/flow/createAccount.xml", getClass());
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
        accountRepository = new AccountRepository();
        builderContext.registerBean("accountService", new AccountService(accountRepository));
    }

    public void testShouldAllowACustomerToCreateAnAccount() throws Exception {
        MutableAttributeMap input = new LocalAttributeMap();
        MockExternalContext context = new MockExternalContext();
        startFlow(input, context);

        Account modelAccount = (Account) getFlowScope().get("account");

        assertCurrentStateEquals("enterCustomerDetails");

        modelAccount.setOwner("Alice");
        context.setEventId("submit");
        resumeFlow(context);

        assertCurrentStateEquals("chooseAccountType");

        modelAccount.setType(AccountType.PREMIER);
        context.setEventId("submit");
        resumeFlow(context);

        assertCurrentStateEquals("requestOverdraft");

        modelAccount.setOverdraft(5000);
        context.setEventId("submit");
        resumeFlow(context);

        assertFlowExecutionEnded();

        Iterable<Account> accounts = accountRepository.findAll();
        Account savedAccount = accounts.iterator().next();
        assertSame(modelAccount, savedAccount);
    }

}
