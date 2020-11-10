package fractals

import math.Complex
import kotlin.math.abs
import kotlin.math.max

/**
 * класс фракталов типа Манделброта
 */
class Mandelbrot {

    //Переменная в квадрате для ограничения
    private var r2: Double = 4.0

    //Количество итераций, в течение которых проверяется
    //Принадлежность точки множеству
    var maxIters = 200
        set(value) {
            //Проверяем устанавливаемое значение на корректность
            field = max(200, abs(value))
        }
        get(){
            return field
        }

    /**
     * Метод определения принадлежности точки множеству Мандельброта
     * @param c точка комплексной плоскости
     * @return true, если точка принадлежит множеству (при заданном значении maxIter)
     * false - в противном случае
     */
    fun isInSet(c: Complex): Float{
        val z = Complex()
        for (i in 1..maxIters){
            z powAssign 2
            z +=c
            //z.valueChange((z pow 2) + c)
            if (z.abs2() > r2) return i.toFloat()/maxIters.toFloat()
        }
        return 1F
    }
}
