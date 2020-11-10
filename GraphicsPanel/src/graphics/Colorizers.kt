package graphics

import java.awt.Color
import kotlin.math.*

/**
 * Все ниже методы задают окраски точкам
 * @param x - Точка которую нужно окрасить
 * @return Color - Цвет заданной точки
 */
fun colorScheme1(x:Float):Color{
    val r:Float = abs(sin(3*cos(2*sin(3*x)))*sin(3*cos(2*sin(3*x))))
    val g:Float = abs(cos(1F-sin(7*x)*cos(7*x)))
    val b:Float = 1F - abs(cos(sin(11*x)*cos(5*x)))
    return Color(r,g,b)
}
fun colorScheme2(x:Float):Color{
    val r = ((4 * x*100) % 255)/255
    val g = ((6 * x*100) % 255)/255
    val b = ((8 * x*100) % 255)/255
    return Color(r,g,b)
}
fun colorScheme4(x: Float): Color {
    val r = 1F-abs(sin(17+6*sin(15*x))*sin(7+2*sin(28*x)))
    val b = log2(1F+abs(sin(2*sin(3*x))*cos(2*sin(3*x))))
    val g = 1F-abs(cos(12+6*sin(15*x))*cos(13+2*sin(28*x)))
    return Color(r, g, b)
}