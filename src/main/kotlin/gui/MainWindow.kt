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

        //Событие,которое возникает при изменении параметров панели
        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                plane.realWidth = mainPanel.width
                plane.realHeight = mainPanel.height
                mainPanel.repaint()
            }
        })

        //Событие,которое возникает при нажатии на панель
        //Не используется пока что
        mainPanel.addMouseListener(object: MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                if (e == null) return
                val cx = Converter.xScr2Crt(e.x, plane)
                val cy = Converter.yScr2Crt(e.y, plane)
                //mainPanel.repaint()
            }
        })

        //Создаем экземпляр FractalPainter
        val fp = FractalPainter(plane)
        //Создаем фрактал типа мандерброта
        val fractal = Mandelbrot()
        //"Привязка события" явно указываем функциональную переменную класса FractalPainter
        fp.fractalTest = fractal::isInSet

        mainPanel.addPainter(fp)

    }
}