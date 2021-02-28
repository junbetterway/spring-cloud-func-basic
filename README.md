# Java Spring Cloud Function Basic

__[Spring Cloud Function](https://spring.io/projects/spring-cloud-function)__ is a Spring project with the following high-level goals:

* Promote the implementation of business logic via functions.
* Decouple the development lifecycle of business logic from any specific runtime target so that the same code can run as a web endpoint, a stream processor, or a task.
* Support a uniform programming model across serverless providers, as well as the ability to run standalone (locally or in a PaaS).
* Enable Spring Boot features (auto-configuration, dependency injection, metrics) on serverless providers.

It abstracts away all of the transport details and infrastructure, allowing the developer to keep all the familiar tools and processes, and focus firmly on business logic.

This __[repository](https://github.com/junbetterway/spring-cloud-func-basic)__ aims to do a basic introduction on using this amazing project. There is no datastore or complicated dependencies here, we just want a quick introduction on how to convert business rules into functions. In simplicity, we will be needing two endpoints as an example for an __Account module__ - create and read. We will be faking the create endpoint wherein, whatever your throw will be returned as is.

__[Spring Cloud v3.1.1](https://docs.spring.io/spring-cloud-function/docs/3.1.1/reference/html/spring-cloud-function.html#_functional_bean_definitions)__ supports a "functional" style of bean declarations for small apps where you need fast startup unlike the traditional bean definitions. See __[this link](https://spring.io/blog/2018/10/22/functional-bean-registrations-in-spring-cloud-function)__ for a detailed comparison of cold starts in AWS. 

Now for the functional bean style - the user application code can be recast into "functional" form, like how I did in our entry-point __[SpringcloudfuncApplication.class](https://github.com/junbetterway/spring-cloud-func-basic/blob/main/src/main/java/com/junbetterway/serverless/springcloudfunc/SpringcloudfuncApplication.java)__:

```
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
```

# Run the Spring Boot Application Using Spring Tool Suite (STS)
1. Download STS version 3.4.* (or better) from the [Spring website](https://spring.io/tools). STS is a free Eclipse bundle with many features useful for Spring developers.
2. Right-click on the project or the main application class then select "Run As" > "Spring Boot App"

# Test The Functions
Now one can invoke the two endpoints in any API tool such as Postman or cURL. Using cURL, we can call:
1. Create Account function by:

```
curl -H "Content-type: application/json" -X POST -d '{"name":"Jun King Minon", "balance": 12000}' http://localhost:8080/createAccount
```

2. Read Account function by:

```
curl http://localhost:8080/readAccount
```

*__Note:__ The endpoint is case-sensitive or else you will end up getting an error response __Function for provided path can not be found__.*

## Powered By
Contact me at [junbetterway](mailto:jkpminon12@yahoo.com)

Happy coding!!!
