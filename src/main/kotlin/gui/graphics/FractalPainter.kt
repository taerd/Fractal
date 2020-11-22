package gui.graphics

import CartesianScreenPlane
import math.Complex
import painters.Painter
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.lang.Thread.sleep
import kotlin.concurrent.thread

/**
 * Класс отрисовки фракталов
 * @param plane - Текущая разметка нашей панели
 * Наследник класса Painter(абстрактный)
 */
class FractalPainter(
    var plane: CartesianScreenPlane
) : Painter {

    var fractalTest : ((Complex)->Float)? = null
    var getColor:((Float)->Color)={x->Color(x,x,x)}

    private var threadList : List<Pair<Thread, BufferedImage>>?=null

    /**
     * Рисование фрактала
     * @param g графический контекст для рисования
     */
    override fun paint(g: Graphics?) {
        //Выход из метода,если функциональная переменная не задана
        //Или нет графического контекста для рисования
        if (fractalTest==null || g==null) return

        //Получаем количество свободных подпроцессов
        val threadCount = Runtime.getRuntime().availableProcessors()
        //Длина участка у потока
        val stripWidth=plane.width/threadCount
        //Иницализация threadList
        threadList = List<Pair<Thread,BufferedImage>>(threadCount){
            //Вычисление границ для каждого потока
            val begin = it*stripWidth
            val end = (it+1)*stripWidth + if(it==threadCount -1) plane.width % threadCount else 0
            //У каждого потока своя буфферизованная графика со своими границами
            //Чтобы потоки не использовали 1 графику одновременно или ожидая друг друга
            val bi = BufferedImage(end-begin+1,plane.height,BufferedImage.TYPE_INT_RGB)
            val bg = bi.graphics
            //Создание пар поток,буфферизованное изображение
            Pair(thread{
                for (i in 0.. end - begin){
                    //sleep(10)
                    for (j in 0..plane.height){
                        //Заполнение пикселей на буфферизованной графике каждым из потоков
                        fillPixel(bg,i , j ,i + begin,j)
                    }
                }
            },bi)
        }.apply{
            //Ожидание конца работы каждого потока,чтобы каждый поток успел дорисовать
            //Свое буфферизованное изображение
            forEachIndexed{index,pair ->
                pair.first.join()
                //Каждый поток по очереди рисует на общей графике свое
                //Буфферизованное изображение
                synchronized(g){
                    g.drawImage(pair.second,index*stripWidth,0,null)
                }
            }
        }
    }

    /**
     * Метод Отрисовки пикселей на графике по конкретным участкам
     * @param g- Графика которой рисуем пиксели
     * @param i- Координата по width, на которой нужно рисовать относительно g
     * @param j - Координата по height, на которой нужно рисовать относительно g
     * @param xPos - Действительная координата width относительно другой графики,на которой
     * Будут рисоваться части буфферизованных изображений
     * @param yPos - Действительная координата height относительно другой графики,на которой
     * Будут рисоваться части буфферизованных изображений
     */
    private fun fillPixel(g:Graphics,i:Int,j:Int,xPos:Int,yPos:Int){
        //Для многопоточности комплексное число может меняться
        //Поэтому выходим,если не данный поток вызывал fractalTest
        val r = fractalTest?.invoke(
                Complex(
                        Converter.xScr2Crt(xPos, plane),
                        Converter.yScr2Crt(yPos, plane)
                )
        ) ?: return

        //Окрашиваем точку в зависимости принадлежит она множеству или нет
        val c = if (r == 1F) Color.BLACK else getColor(r)
        g.color = c
        g.fillRect(i,j,1,1)
    }
}
