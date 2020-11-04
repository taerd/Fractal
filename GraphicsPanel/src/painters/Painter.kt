package painters

import java.awt.Graphics

/**
 * Абстрактный класс Painter
 * Служит для переопределения классов наследников
 */
interface Painter {
    fun paint(g: Graphics?)
}