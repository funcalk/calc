package io.github.funcalk.graph

import io.github.funcalk.expression.Expression
import io.github.funcalk.expression.Parser
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.DISPOSE_ON_CLOSE
import kotlin.math.roundToInt

fun main() {
    JFrame("Funcalk Graph").apply {
        size = Dimension(600, 400)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        contentPane = GraphPane()
    }.isVisible = true
}

private const val ARROW_WIDTH = 25
private const val ARROW_LENGTH = 50

class GraphPane : JPanel() {
    private val function1 = Parser("x^2").parse()
    private val function2 = Parser("sin(x)").parse()
    private val vars = mutableMapOf("x" to 0.0)
    private val pixelsPerUnit = 20.0
    override fun paintComponent(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        drawAxisX(g2d)
        drawAxisY(g2d)
        drawGraph(g2d, function1)
        drawGraph(g2d, function2)
    }

    private fun drawAxisX(g2d: Graphics2D) {
        val zeroY = height / 2
        g2d.drawLine(0, zeroY, width - 1, zeroY)
        g2d.drawArc(width - 1 - ARROW_LENGTH / 2, zeroY - ARROW_WIDTH, ARROW_LENGTH, ARROW_WIDTH, 210, 60)
        g2d.drawArc(width - 1 - ARROW_LENGTH / 2, zeroY, ARROW_LENGTH, ARROW_WIDTH, 150, -60)
    }

    private fun drawAxisY(g2d: Graphics2D) {
        val zeroX = width / 2
        g2d.drawLine(zeroX, 0, zeroX, height - 1)
        g2d.drawArc(zeroX - ARROW_WIDTH, -ARROW_LENGTH / 2, ARROW_WIDTH, ARROW_LENGTH, 300, 60)
        g2d.drawArc(zeroX, -ARROW_LENGTH / 2, ARROW_WIDTH, ARROW_LENGTH, 240, -60)
    }

    private fun drawGraph(g2d: Graphics2D, function: Expression) {
        val zeroX = width.toDouble() / 2.0
        val zeroY = height.toDouble() / 2.0
        val xPoints = IntArray(width)
        val yPoints = IntArray(width)
        for (x in 0 until width) {
            xPoints[x] = x
            vars["x"] = (x.toDouble() - zeroX) / pixelsPerUnit
            val y = function.calculate(vars)
            yPoints[x] = (-(y * pixelsPerUnit) + zeroY).roundToInt()
        }
        g2d.drawPolyline(xPoints, yPoints, width)
    }
}