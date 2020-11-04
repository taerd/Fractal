package math

import kotlin.math.absoluteValue

/**
 * Класс комплексных чисел
 * @param re - вещественная часть комплексного числа
 * @param im - мнимая часть комплексного числа
 */
class Complex(var re: Double, var im: Double) {

    constructor(): this(0.0, 0.0)

    /**
     * Метод сложения комплексных чисел
     * @param c - комплексное число которое надо сложить с экземпляром
     * @return комплексное число после сложения
     */
    operator fun plus(c: Complex)  =
        Complex(re+c.re, im+c.im)
    /**
     * Метод вычитания комплексных чисел
     * @param c - комплексное число которое надо вычесть из экземпляра
     * @return комплексное число после вычитания
     */
    operator fun minus(c: Complex) =
        Complex(re-c.re, im-c.im)
    /**
     * Метод умножения комплексных чисел
     * @param c - комплексное число которое надо умножить на экземпляр
     * @return комплексное число после умножения
     */
    operator fun times(c: Complex) =
        Complex(re*c.re - im*c.im, re*c.im+im*c.re)
    /**
     * Метод деления комплексных чисел
     * @param c - комплексное число которое надо делится на экземпляр
     * @return комплексное число после деления
     */
    operator fun div(c: Complex): Complex{
        val zn = c.re*c.re + c.im*c.im
        val r = (re*c.re + im*c.im)/zn
        val i = (im*c.re - re*c.im)/zn
        return Complex(r, i)
    }

    /**
     * Метод возведения в степень комплексного числа
     * @param p - степень, в которую возводится комплексное число
     * @return - комплексное число после возведения в степень
     */
    infix fun pow(p: Int): Complex {
        //если степень нулевая
        if (p==0) return Complex(1.0, 0.0)

        var r = Complex(1.0, 0.0)
        //умножение комплексного числа на себя p раз
        repeat(p.absoluteValue){
            r *= this
        }

        //если степень отрицательная
        if (p<0){
            r = Complex(1.0, 0.0) / r
        }
        return r
    }

    /**
     * Метод модуля комплексного числа
     * @return модуль комплексного числа
     */
    fun abs() =  Math.sqrt(re*re+im*im)

    /**
     * Метод модуля комплексного числа в квадрате
     * @return модуль комплексного числа в квадрате
     */
    fun abs2() = re*re+im*im
}
