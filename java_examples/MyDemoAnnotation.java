public @interface MyDemoAnnotation {
    String name() default "defaultName";
    int id() default -1;
}