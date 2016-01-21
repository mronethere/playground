package happiness.meter.client

import java.awt._
import javax.swing._


class MainPanel(frame: JFrame) extends JPanel {
  val headerLabel = new JLabel("Welcome!", SwingConstants.CENTER)
  val checkBoxPanel = MainPanel.initCheckBoxPanel
}

object MainPanel {
  def initCheckBoxPanel: JPanel = {
    val checkBoxeGroup = new CheckboxGroup()

  }

  def newCheckBoxWithAction(text: String, actionCommand: String): JCheckBox = {
    val checkBox = new JCheckBox(text)
    checkBox.setActionCommand(actionCommand)
    checkBox
  }
}

class CheckboxGroup private() extends JPanel {
  def this(options: List[String]) = {
    ???
  }
}
