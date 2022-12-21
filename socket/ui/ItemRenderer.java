package ui;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import model.User;

import java.awt.Component;


class ItemRenderer extends BasicComboBoxRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected,
          cellHasFocus);
      if (value != null) {
        User item = (User) value;
        setText(item.getName());
      }
      return this;
    }
  }