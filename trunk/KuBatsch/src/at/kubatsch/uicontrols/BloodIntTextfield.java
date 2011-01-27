/**
 * This file is part of KuBatsch.
 *   created on: 19.01.2011
 *   filename: IntBloodTextBox.java
 *   project: KuBatsch
 */
package at.kubatsch.uicontrols;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A {@link BloodTextfield} which only accepts integer numbers within the
 * specified range.
 * @author Daniel Kuschny (dku2375)
 * 
 */
public class BloodIntTextfield extends BloodTextfield
{
    /**
     * A unique serialization id.
     */
    private static final long serialVersionUID = 1686037551528804682L;

    /**
     * Gets the minValue.
     * @return the minValue
     */
    public int getMinValue()
    {
        return getIntDocument().getMinValue();
    }

    /**
     * Sets the minValue.
     * @param minValue the minValue to set
     */
    public void setMinValue(int minValue)
    {
        getIntDocument().setMinValue(minValue);
    }

    /**
     * Gets the maxValue.
     * @return the maxValue
     */
    public int getMaxValue()
    {
        return getIntDocument().getMaxValue();
    }

    /**
     * Sets the maxValue.
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(int maxValue)
    {
        getIntDocument().setMaxValue(maxValue);
    }

    private IntTextDocument getIntDocument()
    {
        Document d = getDocument();
        if (!(d instanceof IntTextDocument))
        {
            setDocument(createDefaultModel());
        }
        return (IntTextDocument) getDocument();
    }

    /**
     * Initializes a new instance of the {@link BloodIntTextfield} class.
     */
    public BloodIntTextfield()
    {
        this("");
    }

    /**
     * Initializes a new instance of the {@link BloodIntTextfield} class.
     * @param text
     */
    public BloodIntTextfield(String text)
    {
        this(text, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Initializes a new instance of the {@link BloodIntTextfield} class.
     * @param minValue
     * @param maxValue
     */
    public BloodIntTextfield(int minValue, int maxValue)
    {
        this("0", minValue, maxValue);
    }

    /**
     * Initializes a new instance of the {@link BloodIntTextfield} class.
     * @param text
     */
    public BloodIntTextfield(String text, int minValue, int maxValue)
    {
        super(text);
        setMinValue(minValue);
        setMaxValue(maxValue);
    }

    /**
     * @see java.awt.Component#isValid()
     */
    @Override
    public boolean isValid()
    {
        if (!super.isValid())
        {
            return false;
        }
        try
        {
            String txt = getText() == null ? "0" : getText();
            int value = Integer.parseInt(txt);
            return (value >= getMinValue() && value <= getMaxValue());
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * @see javax.swing.JTextField#createDefaultModel()
     */
    @Override
    protected Document createDefaultModel()
    {
        return new IntTextDocument();
    }

    /**
     * Gets the numerical value of this texbox.
     * @return the numerical value of the data stored within this numerical
     *         textbox.
     */
    public int getValue()
    {
        try
        {
            return Integer.parseInt(getText());
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }

    /**
     * A textdocument which validates inserted text for numbers.
     * @author Daniel Kuschny (dku2375)
     */
    class IntTextDocument extends PlainDocument
    {
        /**
         * 
         */
        private static final long serialVersionUID = 656913955059237220L;

        private int               _minValue;
        private int               _maxValue;

        /**
         * Initializes a new instance of the
         * {@link BloodIntTextfield.IntTextDocument} class.
         */
        public IntTextDocument()
        {
            this(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        /**
         * Initializes a new instance of the {@link IntTextDocument} class.
         * @param minValue
         * @param maxValue
         */
        public IntTextDocument(int minValue, int maxValue)
        {
            _minValue = minValue;
            _maxValue = maxValue;
        }

        /**
         * Gets the minValue.
         * @return the minValue
         */
        public int getMinValue()
        {
            return _minValue;
        }

        /**
         * Sets the minValue.
         * @param minValue the minValue to set
         */
        public void setMinValue(int minValue)
        {
            _minValue = minValue;
        }

        /**
         * Gets the maxValue.
         * @return the maxValue
         */
        public int getMaxValue()
        {
            return _maxValue;
        }

        /**
         * Sets the maxValue.
         * @param maxValue the maxValue to set
         */
        public void setMaxValue(int maxValue)
        {
            _maxValue = maxValue;
        }

        /**
         * @see javax.swing.text.PlainDocument#insertString(int,
         *      java.lang.String, javax.swing.text.AttributeSet)
         */
        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException
        {
            if (str == null)
                return;

            // if any string was inserted i.e. by paste, build the new full
            // string
            String oldString = getText(0, getLength());
            String newString = oldString.substring(0, offs) + str
                    + oldString.substring(offs);
            newString = newString.trim();
            try
            {
                // is the resulting string a integer, accept it.
                // add 0 for validation, if no text is entried
                int i = Integer.parseInt(newString + "0");
                i = i / 10;
                if (!newString.equals("-") && i >= _minValue && i <= _maxValue)
                {
                    super.insertString(offs, str, a);
                }
            }
            catch (NumberFormatException e)
            {
            }
        }
    }
}
