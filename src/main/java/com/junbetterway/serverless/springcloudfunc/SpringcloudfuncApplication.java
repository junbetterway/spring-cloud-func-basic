package com.junbetterway.serverless.springcloudfunc;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import com.junbetterway.serverless.springcloudfunc.functions.CreateAccount;
import com.junbetterway.serverless.springcloudfunc.functions.ReadAccount;
import com.junbetterway.serverless.springcloudfunc.model.Account;

@SpringBootConfiguration	
public class SpringcloudfuncApplication implements ApplicationContextInitializer<GenericApplicationContext> {

	public static void main(String[] args) {
		FunctionalSpringApplication.run(SpringcloudfuncApplication.class, args); 
	}

	@Override
	public void initialize(final GenericApplicationContext context) {
		
	    context.registerBean("readAccount", FunctionRegistration.class,
	            () -> new FunctionRegistration<>(new ReadAccount())
	                .type(FunctionType.supplier(Account.class)));
	    
	    context.registerBean("createAccount", FunctionRegistration.class,
	            () -> new FunctionRegistration<>(new CreateAccount())
	                .type(FunctionType.from(Account.class).to(Account.class)));
	}
	
}
