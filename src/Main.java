import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {

	static Chain chain;

	public static void main (String[] args) {

		JFrame f = new JFrame ("chain");
		JPanel p = new JPanel ();

		p.setSize (400, 800);
		f.setSize (p.getSize ());

		p.setLayout (new BoxLayout (p, BoxLayout.PAGE_AXIS));

		JTextArea a = new JTextArea ("paste some text here");
		JTextArea genA = new JTextArea ("chain-generated text will appear here");

		JTextField len = new JTextField ("12");
		JTextField start = new JTextField ("the");
		len.setMaximumSize (new Dimension (800, 800)); start.setMaximumSize (new Dimension (800, 800));

		len.setToolTipText ("length of generated text");
		start.setToolTipText ("the first word of generated text");

		genA.setMaximumSize (new Dimension (800, 800));
		genA.setRows (10);
		genA.setLineWrap (true);
		genA.setWrapStyleWord (true);

		JButton butGenChain = new JButton ("generate chain");
		JButton butGenText = new JButton ("generate text");

		butGenText.setEnabled (false);

		butGenChain.addActionListener (event -> {

			chain = new Chain ();
			chain.add (a.getText ());
			chain.bake ();

			butGenText.setEnabled (true);

		});

		butGenText.addActionListener (event -> {

			genA.setText (chain.generate (Integer.parseInt (len.getText ()), start.getText ()));

		});

		p.add (new JScrollPane (a));
		p.add (butGenChain);
		p.add (len);
		p.add (start);
		p.add (butGenText);
		p.add (genA);

		f.add (p);
		f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		f.setVisible (true);

	}
}
