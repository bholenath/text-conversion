//import java.io.PrintStream;
import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
//import javax.swing.text.*;
//import javax.swing.text.rtf.*;
//import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import rtf.AdvancedRTFDocument;
//import rtf.AdvancedRTFEditorKit;
//import java.awt.GridLayout;
//import java.awt.geom.AffineTransform;

public class BPN
{
static BackpropagationNet bpn;
static String trans_txt;
static StringBuffer targetArray;	
	
		static String output(String input)
		{
		String[] parts;
		int l;

		StringBuffer localStringBuffer;
		localStringBuffer = new StringBuffer();
		targetArray = new StringBuffer();
		//localStringBuffer.append("\n\rInput String: " + input + "\n\r");
		input = input.toUpperCase();
		l = input.length();
		parts = input.split("(?!^)");

		//localStringBuffer.append("\n\rOutput String: ");
		for (int j = 0; j < l; j++) 
		{	
			if(parts[j].compareTo(" ") == 0)
			{
				targetArray.append("  ");
				localStringBuffer.append("  ");
				//j++;
				//break;
			}
			
			else
			{
			for (int i = 0; i < bpn.getNumberOfPatterns(); i++) 
			{				
				if (bpn.getInputPattern(i).compareTo(parts[j]) == 0) 
				{
					targetArray.append(bpn.getTargetPattern(i));
					localStringBuffer.append(bpn.getOutputPattern(i));					
					break;
				}				
			}
			}
		}
		/* 28: 38 */// localStringBuffer.append("\n\rcycle: " +
					// bpn.getLearningCycle() +
					// "\n\n\r  input:       target:  output:    error:\n\r");
		/* 29: 39 */// for (int i = 0; i < bpn.getNumberOfPatterns(); i++) {
		/* 30: 40 */// localStringBuffer.append("  " + bpn.getInputPattern(i) +
					// "   " + bpn.getTargetPattern(i) + "  " +
					// bpn.getOutputPattern(i) + "    " + bpn.getPatternError(i)
					// + "\n\r");
		/* 31: */// }
		/* 32: 42 */// localStringBuffer.append("\n\r");
		/* 33: 43 */// for (int j = 0; j < bpn.getNumberOfPatterns(); j++)
		/* 34: */// {
		/* 35: 44 */// String str1 = bpn.recall(bpn.getInputPattern(j));
		/* 36: 45 */// String str2 = compare(str1, bpn.getTargetPattern(j));
		/* 37: 46 */// localStringBuffer.append("  recalling '" +
					// bpn.getInputPattern(j) + "': " + str1 + str2 + "\n\r");
		/* 38: */// }
		/* 39: 49 */// double d = bpn.getError();
		/* 40: 50 */// String str3 = String.valueOf((1.0D - d) * 100.0D) + "%";
		/* 41: 51 */// localStringBuffer.append("\n\rminerror: " +
					// bpn.getMinimumError() + "\n\rneterror: " + d +
					// "\n\raccuracy: " + str3 + "\n\rtime    : " +
					// bpn.getElapsedTime() + " sec");
		/* 42: 52 */// localStringBuffer.append("\n\r-----------------------------------------------------");
		
		return localStringBuffer.toString();
		}
	
		 static String compare(String paramString1)
		 {
		 int i = 0; int j = paramString1.length();
		 char[] arrayOfChar2 = new char[j];
		 char[] arrayOfChar1 = paramString1.toCharArray();
		 String targetArray1 = targetArray.toString();
		 arrayOfChar2 = targetArray1.toCharArray();
		 for (int k = 0; k < j; k++) {
		 if (arrayOfChar1[k] == arrayOfChar2[k]) {
		 i++;
		 }
		 }
		 //if (i == j) {
		 //return "  GOT IT!";
		 //}
		 return "(" + i + "/" + j + " : " + String.valueOf(i * 100 / j) + "%)";
		 }
		
		public static void main(String[] paramArrayOfString)
		{
		//String s;
		//static String trans_txt;
			try 
			{
	            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	        } 
			catch(Exception e) 
			{
	            e.printStackTrace();
	        }
		final JTextArea comp = new JTextArea();
		final JTextArea comp1 = new JTextArea();
		final JTextArea comp2 = new JTextArea();
		JFrame Frame = new JFrame("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		Frame.setDefaultLookAndFeelDecorated(true);
		Frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		Frame.setTitle("English to Sagar Font Conversion");

		//String Str = JOptionPane.showInputDialog("Enter the English Text : \n\n\r");
		//int Columns = Integer.parseInt(Str);
		//String Str1 = JOptionPane.showInputDialog("Text in Sagar Font : \n\n\r");
		//int Rows = Integer.parseInt(Str1);
		// Frame.setSize(500 , 500);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
	    contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    //contentPane.setPreferredSize(new Dimension(930, 500));
	    //contentPane.setBounds(10,10,500,500);
	    //contentPane.setLayout(null);
	    
	    Frame.setContentPane(contentPane);
	    
		JLabel txt = new JLabel("Enter your English Text");
		txt.setForeground(Color.black);
		txt.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txt.setLayout(new FlowLayout(FlowLayout.LEFT));
		txt.setPreferredSize(new Dimension(555, 20));
		//System.out.println("\n");		
		/*JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(330, 190));*/
        
		//comp.setBounds(20,20,400,300);
		//comp.setLayout(null);
		//comp.setSize(500,400);
		//comp = new JTextArea();
		//comp.setLocation(40,12);
		comp.setSize(432,100);
		comp.setLayout(new FlowLayout(FlowLayout.LEFT));
		comp.setBackground(Color.white);
		comp.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		comp.setForeground(Color.black);
		comp.setWrapStyleWord(true);
        comp.setLineWrap(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		comp.setBorder(BorderFactory.createCompoundBorder(border, 
		BorderFactory.createEmptyBorder(7, 7, 7, 7)));
		/*JScrollPane scrollPane = new JScrollPane(comp,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);*/
		/*JScrollPane scrolltxt = new JScrollPane();
        scrolltxt.setViewportView(comp);
        scrolltxt.setWheelScrollingEnabled(true);*/
		
		JLabel txt1 = new JLabel("Translated text in Sagar font");
		txt1.setForeground(Color.black);
		txt1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txt1.setLayout(new FlowLayout(FlowLayout.LEFT));
		txt1.setPreferredSize(new Dimension(400, 20));
		
		JLabel txt2 = new JLabel();	
		txt2.setLayout(new FlowLayout(FlowLayout.CENTER));
		txt2.setPreferredSize(new Dimension(1000, 30));
		
		//JTextArea comp1 = new JTextArea();
		//String result = getTxt();
		//BPN results = new BPN();
		//Txt results = new Txt();		
		//comp.setLocation(40,12);
		//AdvancedRTFEditorKit kit = new AdvancedRTFEditorKit();
		//comp1.setEditorKit(kit);
		//RTFEditorKit kit = new RTFEditorKit();
		//comp1.setEditorKit(kit);
		//comp1.setContentType("text/rtf");
		comp1.setSize(432,100);		
		comp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		comp1.setBackground(Color.white);
		comp1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		comp1.setForeground(Color.black);
		comp1.setWrapStyleWord(true);
        comp1.setLineWrap(true);
		Border border1 = BorderFactory.createLineBorder(Color.BLACK);
		comp1.setBorder(BorderFactory.createCompoundBorder(border1, 
		BorderFactory.createEmptyBorder(7, 7, 7, 7)));
		
		comp2.setSize(new Dimension(227, 27));
		comp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		//comp2.setRows(1);
		comp2.setBackground(Color.white);
		comp2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		comp2.setForeground(Color.black);
		comp2.setWrapStyleWord(true);
        comp2.setLineWrap(true);
		Border border2 = BorderFactory.createLineBorder(Color.BLACK);
		comp2.setBorder(BorderFactory.createCompoundBorder(border2, 
		BorderFactory.createEmptyBorder(0, 7, 0, 7)));
		
		final JButton translate = new JButton();
		translate.setLayout(new FlowLayout(FlowLayout.CENTER));
		translate.setPreferredSize(new Dimension(120, 30));
		
		translate.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	bpn = new BackpropagationNet();
		    	bpn.readConversionFile("ascii2bin.cnv");
		    	bpn.addNeuronLayer(1);
				bpn.addNeuronLayer(10);
				bpn.addNeuronLayer(2);
				bpn.connectLayers();
				bpn.readPatternFile("towns.rtf");
				bpn.setLearningRate(0.25D);
				bpn.setMinimumError(0.0005D);
				bpn.setAccuracy(0.2D);
				bpn.setMaxLearningCycles(-1);
				bpn.setDisplayStep(500);
				bpn.resetTime();
				while (!bpn.finishedLearning())
				{
					bpn.learn();
					if (bpn.displayNow()) 
					{	
					trans_txt = output(comp.getText());
					}
				}
				comp1.setText(trans_txt);	
				//String accuracy = compare(trans_txt);
				comp2.setText("Accuracy : "+compare(trans_txt));
			}		   		    
		});
		
		translate.setText("Translate >>>");
		
		JScrollPane scroll = new JScrollPane(comp , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(432, 190));
		//scroll.setLayout(new ScrollPaneLayout(JScrollBar.VERTICAL_SCROLLBAR));
		scroll.setViewportView(comp);
        scroll.setWheelScrollingEnabled(true);
		
		JScrollPane scroll1 = new JScrollPane(comp1 , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll1.setPreferredSize(new Dimension(432, 190));
		//scroll1.setLayout(new ScrollPaneLayout(JScrollBar.VERTICAL_SCROLLBAR));
		scroll1.setViewportView(comp1);
        scroll1.setWheelScrollingEnabled(true);
		
		//String accuracy = compare(trans_txt);
		//JLabel txt2 = new JLabel("Accuracy : ");	
		//txt2.setLayout(new FlowLayout(FlowLayout.LEFT));
		//txt2.setPreferredSize(new Dimension(500, 20));
				     
        contentPane.add(txt);	
		contentPane.add(txt1);
		contentPane.add(scroll);
		contentPane.add(translate);				
		contentPane.add(scroll1);	
		contentPane.add(txt2);
		contentPane.add(comp2);
		//contentPane.add(scrolltxt);
	
		//Frame.add(contentPane);
		Frame.pack();
		Frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * JPanel NorthPanel = new JPanel();
		 * NorthPanel.setBackground(Color.red); JPanel SouthPanel = new
		 * JPanel(); SouthPanel.setBackground(Color.red); JPanel eastPanel = new
		 * JPanel(); eastPanel.setBackground(Color.blue); JPanel westPanel = new
		 * JPanel(); westPanel.setBackground(Color.blue); JPanel middlePanel =
		 * new JPanel(); middlePanel.setBackground(Color.yellow); Container pane
		 * = Frame.getContentPane();
		 * 
		 * pane.add(NorthPanel, BorderLayout.NORTH); pane.add(SouthPanel,
		 * BorderLayout.SOUTH); pane.add(eastPanel, BorderLayout.EAST);
		 * pane.add(westPanel, BorderLayout.WEST); pane.add(middlePanel);
		 */

		// JLabel jlbHelloWorld = new JLabel("Hello World");
		// add(jlbHelloWorld);

		Frame.setVisible(true);

		/*Scanner in = new Scanner(System.in);
		System.out.println("Enter a string/integer");
		s = in.nextLine();*/

		/*
		 * System.out.println("You entered string "+s);
		 * System.out.println("\n\n\rDEMO OF A BACKPROPAGATION NET\n\r"); if
		 * (paramArrayOfString.length == 0) {
		 */

		/*if (s.length() == 0) {
			System.out.println("Missing argument --- Usage:  java BPN <display step>");
			System.exit(0);
			}
		else
		{*/
			//bpn = new BackpropagationNet();
			
			/* 58: 69 */// System.out.print("Reading conversion file ...");
			/* 59: 70 *///bpn.readConversionFile("ascii2bin.cnv");
			/* 60: 71 */// System.out.println("OK");
			/* 61: */
			/* 62: 73 */// System.out.print("Creating neuron layers ...");
			/* 63: 74 *///bpn.addNeuronLayer(1);
			/* 64: 75 *///bpn.addNeuronLayer(10);
			/* 65: */
			/* 66: 77 *///bpn.addNeuronLayer(2);
			/* 67: 78 */// System.out.println("OK");
			/* 68: */
			/* 69: 80 */// System.out.print("Connecting neuron layers ...");
			/* 70: 81 *///bpn.connectLayers();
			/* 71: 82 */// System.out.println("OK");
			/* 72: */
			/* 73: 84 */// System.out.println("\n\rNet structure:");
			/* 74: 85 */// for (int i = 0; i < bpn.getNumberOfLayers(); i++) {
			/* 75: 86 */// System.out.println("layer " + i + ": " +
						// bpn.getNumberOfNeurons(i) + " neurons");
			/* 76: */// }
			/* 77: 87 */// System.out.println("weights: " +
						// bpn.getNumberOfWeights() + "\n\r");
			/* 78: */
			/* 79: 89 */// System.out.print("Reading pattern file ...");
			/* 80: 90 *///bpn.readPatternFile("towns.pat");
			/* 81: 91 */// System.out.println("OK - patterns: " +
						// bpn.getNumberOfPatterns());
			/* 82: */
			/* 83: */
			/* 84: 94 *///bpn.setLearningRate(0.25D);
			/* 85: 95 *///bpn.setMinimumError(0.0005D);
			/* 86: 96 *///bpn.setAccuracy(0.2D);
			/* 87: 97 *///bpn.setMaxLearningCycles(-1);
			/* 88: 98 *///bpn.setDisplayStep(500);
			/* 89: */
			/* 90:100 */// System.out.print("\n\rallright, let's learn...\n\r");
			/* 91:101 *///bpn.resetTime();
			/* 92:102 *///while (!bpn.finishedLearning())
			/* 93: *///{
				/* 94:103 *///bpn.learn();
				/* 95:104 *///if (bpn.displayNow()) {
					/* 96:104 *///output(s);
					/* 97: *///}
				/* 98: *///}
			/* 99:106 */// output(s);
			/* 100:107 *///System.out.println("\n\rFINISHED.");
			/* 101: *///}
		}	
}

/*
 * Location: C:\Users\Harshit\Downloads\Compressed\bpn\ * Qualified Name: BPN *
 * JD-Core Version: 0.7.0.1
 */