package gui

import CartesianScreenPlane
import components.GraphicsPanel
import fractals.Mandelbrot
import gui.graphics.FractalPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.*
import javax.swing.GroupLayout
import javax.swing.JFrame
import graphics.colorScheme2
import gui.graphics.MouseFramePainter
import kotlin.math.max
import kotlin.math.min


/**
 * Класс окна для вывода на экран фракталов
 */
class MainWindow : JFrame(){

    //Минимальные размеры окошка
    private val minSize = Dimension(300, 200)
    //Панель на которой будем рисовать
    private val mainPanel: GraphicsPanel

    init{
        //Установка параметров окна
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Построение множества Мандельброта"
        minimumSize = Dimension(700, 700)

        mainPanel = GraphicsPanel()
        mainPanel.background = Color.WHITE

        //Установка разметки компонент на окне
        val gl = GroupLayout(contentPane)

        gl.setVerticalGroup(gl.createSequentialGroup()
            .addGap(4)
            .addComponent(mainPanel, minSize.height, minSize.height, GroupLayout.DEFAULT_SIZE)
            .addGap(4)
        )

        gl.setHorizontalGroup(gl.createSequentialGroup()
            .addGap(4)
            .addGroup(
                gl.createParallelGroup()
                    .addComponent(mainPanel, minSize.width, minSize.width, GroupLayout.DEFAULT_SIZE)
            )
            .addGap(4))
        layout = gl

        pack()

        //Установка участка для декартовой системы координат
        val plane = CartesianScreenPlane(
            mainPanel.width, mainPanel.height,
            -2.0, 1.0, -1.0, 1.0
        )

        //Создание экземпляра класса с данной графикой mainPanel
        val mfp = MouseFramePainter(mainPanel.graphics)

        //Создаем экземпляр FractalPainter
        val fp = FractalPainter(plane)

        //Создаем фрактал типа мандерброта
        val fractal = Mandelbrot()

        //Событие,которое возникает при изменении параметров панели
        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                plane.realWidth = mainPanel.width
                plane.realHeight = mainPanel.height

                //Перерисовывается графика
                mainPanel.repaint()

                //Перерисовывается графика MouseFramePainter
                mfp.repaint(mainPanel.graphics)
            }
        })

        //Событие,которое возникает когда кнопку мыши отжали
        mainPanel.addMouseListener(object: MouseAdapter(){
            override fun mouseReleased(e: MouseEvent?) {
                e?.let{

                    mfp.currentPoint=it.point

                    //Создание новой разметки
                    val newPlane=CartesianScreenPlane(
                            mainPanel.width,
                            mainPanel.height,
                            Converter.xScr2Crt(min((mfp.currentPoint?.x)?:return,(mfp.startPoint?.x)?:return),plane),
                            Converter.xScr2Crt(max((mfp.currentPoint?.x)?:return,(mfp.startPoint?.x)?:return),plane),
                            Converter.yScr2Crt(max((mfp.currentPoint?.y)?:return,(mfp.startPoint?.y)?:return),plane),
                            Converter.yScr2Crt(min((mfp.currentPoint?.y)?:return,(mfp.startPoint?.y)?:return),plane)
                    )
                    //Изменение разметки
                    plane.xMin=newPlane.xMin
                    plane.xMax=newPlane.xMax
                    plane.yMin=newPlane.yMin
                    plane.yMax=newPlane.yMax

                    //Перерисовываются пэинтеры у graphics panel(а именно fractal painter по новому plane так как var plane в его инициализации)
                    //mainPanel.paint(mainPanel.graphics)

                    //Перерисовывается графика
                    //Вопрос включает ли метод repaint() вызов mainPanel.paint? или мы приближаем картинку не строя ее заного
                    mainPanel.repaint()

                    //Удаляем позиции чтобы не оставалось следа от рамки
                    mfp.currentPoint=null
                    mfp.startPoint=null
                }
                mfp.isVisible=false
            }

            //Событие возникает когда нажали на кнопку
            override fun mousePressed(e: MouseEvent?) {
                e?.let {
                    mfp.isVisible = true
                    mfp.startPoint = it.point
                }
            }
        })

        //Событие,которое возникает при движении курсора мыши по панели
        mainPanel.addMouseMotionListener(object:MouseAdapter(){
            override fun mouseDragged(e: MouseEvent?) {
                e?.let{
                    mfp.currentPoint=it.point
                }
            }
        })

        //"Привязка события" явно указываем функциональную переменную класса FractalPainter
        fp.fractalTest = fractal::isInSet
        //"Привязка события" явно указываем функциональную переменную функции colorScheme1
        fp.getColor = ::colorScheme2

        mainPanel.addPainter(fp)

    }
}