package components

import painters.Painter
import java.awt.Graphics
import javax.swing.JPanel

/**
 * Класс-панель для рисования
 */
class GraphicsPanel: JPanel(){
    //painters- изменяемый список пэинтеров,которые будут рисовать на панели
    var painters = mutableListOf<Painter>()
    set(value){
        field = value
        repaint()
    }
    //Переопределенный метод отрисовки панелей
    override fun paint(g: Graphics?) {
        //Отрисовывается пэинт "родителя"
        super.paint(g)
        //Отрисовывается пэинт каждого пэинтера
        painters.forEach{
            it.paint(g)
        }
    }
    /**
     * Метод для добавления пэинтеров
     * @param painter - пэинтер который нужно добавить
     */
    fun addPainter(painter: Painter){
        painters.add(painter)
        repaint()
    }

    /**
     * Метод для удаления пэинтеров
     * @param painter - пэинтер который нужно удалить
     */
    fun removePainter(painter: Painter){
        painters.remove(painter)
        repaint()
    }
    /*
    fun updatePainters(){
        painters.forEach {painter->
            painter.up
        }
    }
    */
}