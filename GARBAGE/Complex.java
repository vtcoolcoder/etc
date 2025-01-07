public class Complex {
    private final double re;   
    private final double im;   

    /**
     * Создает экземпляр комплексного числа.
     *
     * @param real действительная часть
     * @param imag мнимая часть
     */
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    /**
     * Возвращает модуль числа.
     *
     * @return модуль комплексного числа
     */
    public double abs() {
        return Math.hypot(re, im);
    }


    /**
     * Вычисляет сумму чисел.
     *
     * @param b второе слагаемое
     * @return результат сложения
     */
    public Complex plus(Complex b) {
        Complex a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    /**
     * Вычисляет разность чисел.
     *
     * @param b второе слагаемое
     * @return результат вычитания
     */
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    /**
     * Выполняет умножение числа.
     *
     * @param b второй множитель
     * @return результат умножения
     */
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }


    /**
     * Возвращает действительную часть числа.
     *
     * @return действительную часть числа
     */
    public double re() {
        return re;
    }

    /**
     * Возвращает мнимую часть.
     *
     * @return мнимую часть числа
     */
    public double im() {
        return im;
    }

    /**
     * Возвращает результат деления числа.
     *
     * @param b делитель
     * @return результат деления
     */
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    /**
     * Возвращает обратное комплексное число.
     *
     * @return обратное число
     */
    public Complex reciprocal() {
        double scale = re * re + im * im;
        return new Complex(re / scale, -im / scale);
    }
    

    /**
     * Возвращает сумму комплексных чисел.
     *
     * @param a первое слагаемое
     * @param b второе слагаемое
     * @return сумму чисел
     */
    public static Complex plus(Complex a, Complex b) {
        double real = a.re + b.re;
        double imag = a.im + b.im;
        Complex sum = new Complex(real, imag);
        return sum;
    }

    /**
     * Сравнивает комплексные числа.
     *
     * @param x число для сравнения
     * @return true, если числа равны, иначе - false
     */
    public boolean equals(Object x) {
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        Complex that = (Complex) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im < 0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

}