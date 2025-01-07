package testaop;


public class Runner {

    public static void main(String[] args) {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(Config.class);
        
        Greeting greeting = context.getBean(Greeting.class);
        
        greeting.sayHello();
    }
}


@Configuration
@EnableAspectJAutoProxy
class Config {

    @Bean
    public Greeting greeting() {
        return new Greeting();
    }

    @Bean
    public TestAspect aspect() {
        return new TestAspect();
    }
}


class Greeting {
    
    public void sayHello() {
        System.out.println("Hello, World");
    }
}


@Aspect
@Slf4j
class TestAspect {

    @Around("execution(* *.*.*(..))")
    public void greeting(MethodInvocationProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Привет");
        joinPoint.proceed();
        log.info("Пока");
    }
}