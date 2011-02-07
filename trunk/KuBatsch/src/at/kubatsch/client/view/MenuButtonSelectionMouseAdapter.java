/**
 * This file is part of KuBatsch.
 *   created on: 17.01.2011
 *   filename: MenuButtonSelectionMouseAdapter.java
 *   project: KuBatsch
 */
package at.kubatsch.client.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import at.kubatsch.uicontrols.MenuButton;

/**
 * Used by menu's to set one button as selected.
 * Buttons get selected on mouse enter. 
 * @author Daniel Kuschny (dku2375)
 */
public class MenuButtonSelectionMouseAdapter extends MouseAdapter
{
    private MenuButton[] _buttons;

    /**
     * Initializes a new instance of the {@link MenuButtonSelectionMouseAdapter} class.
     * @param buttons
     */
    public MenuButtonSelectionMouseAdapter(MenuButton[] buttons)
    {
        _buttons = buttons;
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e)
    {
        for (MenuButton button : _buttons)
        {
            button.setSelected(button == e.getComponent());
        }
    }
}
