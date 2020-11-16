package gui.graphics

import java.awt.Color
import java.awt.Graphics
import java.awt.Point


class MouseFramePainter(var g:Graphics ) {

    var startPoint: Point?=null
        get(){
            return field
        }
    var currentPoint:Point?=null
        set(value){
            //Отрисовывается пэинтер до изменения текущей точки (стираем рамку)
            paint()
            field=value
            //Отрисовывается пэинтер после изменения текущей точки(стираем рамку)
            paint()
        }
        get(){
            return field
        }
    var isVisible = false
        set(value){
            field=value
            //Если isVisible=true, Сбрасываем startPoint и currentPoint
            if(value){
                currentPoint=null
                startPoint=null
            }
        }

    private fun paint(){
        if(isVisible){
            g.color = Color.BLACK
            //проверка на null "?." и в случае не null выполняется следущие действия после let (startpoint -> s , currentpoint -> c и дальше рисуем прямоугольники)
            startPoint?.let{s->
                currentPoint?.let{c->
                    //исключающий или
                    g.setXORMode(Color.WHITE)//второй цвет от которого зависит цвет пикселя
                    if (s.x < c.x && s.y > c.y)
                        g.drawRect(s.x,s.y,c.x-s.x,s.y-c.y)//рамка слева направо снизу вверх
                    else if (s.x > c.x && s.y > c.y)
                        g.drawRect(c.x,c.y,s.x-c.x,s.y-c.y)//рамка справа налево снизу вверх
                    else if(s.x > c.x && s.y < c.y )//рамка справа налево сверху вниз
                        g.drawRect(c.x,s.y,s.x-c.x,c.y-s.y)
                    else g.drawRect(s.x,s.y,c.x-s.x,c.y-s.y)//рамка слева направо сверху вниз
                    g.setPaintMode()
                }
            }
        }
    }

    /**
     * Метод перерисовки графики
     * @param g - Новая графика
     */
    fun repaint(g:Graphics){
        this.g=g
    }

}