static void init(Supplier<InputStream>... suppliers) {
    Arrays.stream(suppliers)
          .map(Supplier::get)
          .map(Scanner::new)
          .map(scanner -> scanner.useDelimiter("\n"))
          .map(Scanner::tokens)
          .map(stream -> (Runnable) () -> stream.forEach(System.out::println))
          .map(Thread::new)
          .forEach(Thread::start);
}


static void init(Supplier<InputStream>... suppliers) {
    for (var supplier : suppliers) {
        Runnable runnable = () -> {
            var scanner = new Scanner(supplier.get());
            
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        };
        
        new Thread(runnable).start();
    }        
}


// ################################################################################
// ################################################################################

static void init(InputStream input, boolean flag) {
    Runnable runnable = () -> {
        var scanner = new Scanner(input);
        
        while (scanner.hasNextLine()) {
            System.out.println((flag ? "[STDOUT]: " : "[STDERR]: ") + scanner.nextLine());
        }
    };
        
    new Thread(runnable).start();
}

var process = new ProcessBuilder("python", "-i", "-q").start();
init(process.getInputStream(), true);
init(process.getErrorStream(), false);
var writer = new PrintWriter(process.getOutputStream(), true);

writer.println("print(23 ** 23)");

writer.println("for x in range(10):");
writer.println("    print(x)");
writer.println();

process.destroy();
