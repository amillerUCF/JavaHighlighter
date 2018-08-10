import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

public class MyHighlighter {

	private int mPositionOfHighlight;
	private Highlighter mHighlighter;
	private JTextArea mTextArea;
	private Highlighter.HighlightPainter mHighlightPainter;
	private String mLastPattern;
	
	public MyHighlighter(JTextArea textArea)
	{
		mPositionOfHighlight = 0;
		mHighlighter = textArea.getHighlighter();
		mTextArea = textArea;
		mHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
		mLastPattern = "";
	}
	
	public void findOccurrence(String pattern)
	{
		// Reset position highlight back to '0'
		// if using a new pattern string
		if (!mLastPattern.equals(pattern))
		{
			mPositionOfHighlight = 0;
		}
		// Remove all highlights and then find
		// the next occurrence to highlight
		else
		{
			mHighlighter.removeAllHighlights();
		}
		
		Document doc = mTextArea.getDocument();   
	    String text;
		try
		{
			text = doc.getText(0, doc.getLength());
		    
			// If occurrences are found, then highlight pattern in text
			if (text.contains(pattern))
			{
				int position = text.indexOf(pattern, mPositionOfHighlight);

				// Position index has hit the end of text document.
				// Reset the position back to beginning and try again
				if (position == -1)
				{
					mPositionOfHighlight = 0;
					position = text.indexOf(pattern, mPositionOfHighlight);
				}
				
				int endPostition = position + pattern.length();
				
				// Store highlight in highlighter object
		    	mHighlighter.addHighlight(position, endPostition, mHighlightPainter);
				
		    	mPositionOfHighlight = endPostition + 1;
		    	mLastPattern = pattern;
			}
			// No occurrences found, relay message
			else
			{
				JOptionPane.showMessageDialog(mTextArea, String.format("No occurrences found for " + "'%s'", pattern));
			}
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}        
	}
}
