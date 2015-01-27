//import java.io.ByteArrayOutputStream;
//import java.io.ByteArrayInputStream;
//import java.io.StringReader;
import java.io.DataInputStream;
import java.io.FileInputStream;	
//import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.text.rtf.*;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;			  
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.lang.Object;
import javax.swing.text.*;

			  
/*   9:    */ public class BackpropagationNet
/*  10:    */   extends NeuralNet
/*  11:    */ {
/*  12:    */   Vector neuronLayerVector;
/*  13:    */   NeuronLayer[] neuronLayerArray;
/*  14:    */   WeightMatrix[] weightMatrixArray;
/*  15:    */   Pattern[] inputPatternArray;
/*  16:    */   Pattern[] targetPatternArray;
/*  17:    */   String[] outputPatternArray;
/*  18:    */   double minimumError;
/*  19:    */   double error;
/*  20:    */   double accuracy;
/*  21:    */   float[][] layerOutputError;
/*  22:    */   String[][] conversionTable;
/*  23:    */   int lastLayer;
/*  24:    */   int lastMatrix;
/*  25:    */   int lastPattern;
/*  26:    */   int multiplier;
/*  27:    */   
/*  28:    */   public BackpropagationNet()
/*  29:    */   {
/*  30: 64 */     this.learningCycle = 0;
/*  31: 65 */     this.maxLearningCycles = -1;
/*  32: 66 */     this.minimumError = 0.0005D;
/*  33: 67 */     this.learningRate = 0.25D;
/*  34: 68 */     this.error = 1000.0D;
/*  35: 69 */     this.multiplier = 0;
/*  36: 70 */     this.accuracy = 0.2D;
/*  37: 71 */     this.neuronLayerVector = new Vector();
/*  38: 72 */     this.stopLearning = false;
/*  39: 73 */     resetTime();
/*  40:    */   }
/*  41:    */   
/*  42:    */   void addNeuronLayer(int paramInt)
/*  43:    */   {
/*  44: 79 */     this.neuronLayerVector.addElement(new NeuronLayer(paramInt * this.multiplier));
/*  45:    */   }
/*  46:    */   
/*  47:    */   void connectLayers()
/*  48:    */   {
/*  49: 85 */     this.weightMatrixArray = new WeightMatrix[this.neuronLayerVector.size() - 1];
/*  50: 86 */     this.neuronLayerArray = new NeuronLayer[this.neuronLayerVector.size()];
/*  51: 87 */     int i = 0;
/*  52: 88 */     for (Enumeration localEnumeration = this.neuronLayerVector.elements(); localEnumeration.hasMoreElements();) {
/*  53: 89 */       this.neuronLayerArray[(i++)] = ((NeuronLayer)localEnumeration.nextElement());
/*  54:    */     }
/*  55: 90 */     this.neuronLayerVector = null;
/*  56: 91 */     for (i = 0; i < this.weightMatrixArray.length; i++)
/*  57:    */     {
/*  58: 92 */       this.weightMatrixArray[i] = new WeightMatrix(this.neuronLayerArray[i].size(), this.neuronLayerArray[(i + 1)].size(), true);
/*  59: 93 */       this.weightMatrixArray[i].init();
/*  60:    */     }
/*  61: 95 */     this.lastLayer = (this.neuronLayerArray.length - 1);
/*  62: 96 */     this.lastMatrix = (this.weightMatrixArray.length - 1);
/*  63:    */   }
/*  64:    */   
/*  65:    */   void setMinimumError(double paramDouble)
/*  66:    */   {
/*  67:101 */     this.minimumError = paramDouble;
/*  68:    */   }
/*  69:    */   
/*  70:    */   double getMinimumError()
/*  71:    */   {
/*  72:106 */     return this.minimumError;
/*  73:    */   }
/*  74:    */   
/*  75:    */   void setAccuracy(double paramDouble)
/*  76:    */   {
/*  77:111 */     this.accuracy = paramDouble;
/*  78:    */   }
/*  79:    */   
/*  80:    */   double getAccuracy()
/*  81:    */   {
/*  82:116 */     return this.accuracy;
/*  83:    */   }
/*  84:    */   
/*  85:    */   float[][] getWeightValues(int paramInt)
/*  86:    */   {
/*  87:121 */     return this.weightMatrixArray[paramInt].getWeights();
/*  88:    */   }
/*  89:    */   
/*  90:    */   float[] getNeuronOutputs(int paramInt)
/*  91:    */   {
/*  92:126 */     return this.neuronLayerArray[paramInt].getOutput();
/*  93:    */   }
/*  94:    */   
/*  95:    */   int getNumberOfLayers()
/*  96:    */   {
/*  97:131 */     return this.neuronLayerArray.length;
/*  98:    */   }
/*  99:    */   
/* 100:    */   int getNumberOfNeurons(int paramInt)
/* 101:    */   {
/* 102:136 */     return this.neuronLayerArray[paramInt].size();
/* 103:    */   }
/* 104:    */   
/* 105:    */   int getNumberOfWeights()
/* 106:    */   {
/* 107:141 */     int i = 0;
/* 108:142 */     for (int j = 0; j <= this.lastMatrix; j++) {
/* 109:143 */       i += this.weightMatrixArray[j].size();
/* 110:    */     }
/* 111:144 */     return i;
/* 112:    */   }
/* 113:    */   
/* 114:    */   int getNumberOfWeights(int paramInt)
/* 115:    */   {
/* 116:149 */     return this.weightMatrixArray[paramInt].size();
/* 117:    */   }
/* 118:    */   
/* 119:    */   int getNumberOfPatterns()
/* 120:    */   {
/* 121:154 */     return this.inputPatternArray.length;
/* 122:    */   }
/* 123:    */   
/* 124:    */   String getInputPattern(int paramInt)
/* 125:    */   {
/* 126:159 */     return this.inputPatternArray[paramInt].getPatternString();
/* 127:    */   }
/* 128:    */   
/* 129:    */   String getTargetPattern(int paramInt)
/* 130:    */   {
/* 131:164 */     return this.targetPatternArray[paramInt].getPatternString();
/* 132:    */   }
/* 133:    */   
/* 134:    */   String getOutputPattern(int paramInt)
/* 135:    */   {				  
/* 136:170 */     String str1 = new String();
/* 137:171 */     String str2 = new String();
/* 138:172 */     float f = 0.0F;
					if(paramInt == 26)	
					{
						str1 = " ";
						return str1;
					}
					else
					{
/* 139:173 */     for (int i = 0; i < this.layerOutputError[0].length; i++)
/* 140:    */     {
/* 141:174 */       f = this.targetPatternArray[paramInt].getValue(i) - this.layerOutputError[paramInt][i];
/* 142:175 */       str2 = str2 + (f < this.accuracy / 10.0D ? "0" : "1");
/* 143:    */     }
/* 144:177 */     str1 = "";
/* 145:178 */     for (int j = 0; j < str2.length(); j += this.multiplier) {
/* 146:179 */       str1 = str1 + getAsciiValue(str2.substring(j, j + this.multiplier));
/* 147:    */     }
/* 148:181 */     return str1;
					}
/* 149:    */   }
/* 150:    */   
/* 151:    */   float getPatternError(int paramInt)
/* 152:    */   {
/* 153:187 */     float f = 0.0F;
/* 154:188 */     for (int i = 0; i < this.layerOutputError[0].length; i++) {
/* 155:189 */       f += Math.abs(this.layerOutputError[paramInt][i]);
/* 156:    */     }
/* 157:191 */     return f;
/* 158:    */   }
/* 159:    */   
/* 160:    */   double getError()
/* 161:    */   {
/* 162:197 */     return this.error;
/* 163:    */   }
/* 164:    */   
/* 165:    */   void learn()
/* 166:    */   {
/* 167:204 */     if ((this.error > this.minimumError) && ((this.learningCycle < this.maxLearningCycles) || (this.maxLearningCycles == -1)))
/* 168:    */     {
/* 169:205 */       this.learningCycle += 1;
/* 170:206 */       for (int i = 0; i <= this.lastPattern; i++)
/* 171:    */       {
/* 172:210 */         this.neuronLayerArray[0].setInput(this.inputPatternArray[i]);
/* 173:211 */         for (int j = 1; j <= this.lastLayer; j++)
/* 174:    */         {
/* 175:212 */           this.neuronLayerArray[j].computeInput(this.neuronLayerArray[(j - 1)], this.weightMatrixArray[(j - 1)]);
/* 176:213 */           this.neuronLayerArray[j].computeOutput();
/* 177:    */         }
/* 178:217 */         this.neuronLayerArray[this.lastLayer].computeLayerError(this.targetPatternArray[i]);
/* 179:    */         
/* 180:219 */         this.layerOutputError[i] = this.neuronLayerArray[this.lastLayer].getLayerError();
/* 181:222 */         for (int k = this.lastMatrix; k >= 0; k--)
/* 182:    */         {
/* 183:223 */           this.weightMatrixArray[k].changeWeights(this.neuronLayerArray[k].getOutput(), this.neuronLayerArray[(k + 1)].getLayerError(), this.learningRate);
/* 184:224 */           if (k > 0) {
/* 185:225 */             this.neuronLayerArray[k].computeLayerError(this.neuronLayerArray[(k + 1)], this.weightMatrixArray[k]);
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:230 */       double d = 0.0D;
/* 190:231 */       for (int m = 0; m < this.layerOutputError.length; m++) {
/* 191:232 */         for (int n = 0; n < this.layerOutputError[0].length; n++) {
/* 192:233 */           d += square(this.layerOutputError[m][n]);
/* 193:    */         }
/* 194:    */       }
/* 195:236 */       this.error = Math.abs(d * 0.5D);
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:239 */       this.stopLearning = true;
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   String recall(String paramString)
/* 204:    */   {
/* 205:245 */     Pattern localPattern = new Pattern(paramString, this.conversionTable);
/* 206:246 */     float[] arrayOfFloat = new float[this.targetPatternArray[0].size()];
/* 207:    */     
/* 208:248 */     this.neuronLayerArray[0].setInput(localPattern);
/* 209:249 */     for (int i = 1; i <= this.lastLayer; i++)
/* 210:    */     {
/* 211:250 */       this.neuronLayerArray[i].computeInput(this.neuronLayerArray[(i - 1)], this.weightMatrixArray[(i - 1)]);
/* 212:251 */       this.neuronLayerArray[i].computeOutput();
/* 213:    */     }
/* 214:253 */     arrayOfFloat = this.neuronLayerArray[this.lastLayer].getOutput();
/* 215:    */     String str2;
/* 216:254 */     String str1 = str2 = "";
/* 217:255 */     for (int j = 0; j < arrayOfFloat.length; j++) {
/* 218:255 */       str1 = str1 + (arrayOfFloat[j] < this.accuracy ? "0" : "1");
/* 219:    */     }
/* 220:256 */     for (int k = 0; k < str1.length(); k += this.multiplier) {
/* 221:256 */       str2 = str2 + getAsciiValue(str1.substring(k, k + this.multiplier));
/* 222:    */     }
/* 223:257 */     return str2;
/* 224:    */   }
/* 225:    */   
/* 226:    */   synchronized void readConversionFile(String paramString)
/* 227:    */   {
/* 228:263 */     String str = null;
/* 229:    */     try
/* 230:    */     {
/* 231:265 */       DataInputStream localDataInputStream = new DataInputStream(new FileInputStream(paramString));
/* 232:266 */       int i = Integer.parseInt(localDataInputStream.readLine());
/* 233:267 */       this.conversionTable = new String[i][2];
/* 234:268 */       for (int j = 0; j < i; j++)
/* 235:    */       {
/* 236:269 */         str = localDataInputStream.readLine();
/* 237:270 */         this.conversionTable[j][0] = String.valueOf(str.charAt(0));
/* 238:271 */         this.conversionTable[j][1] = str.substring(1);
/* 239:    */       }
/* 240:273 */       this.multiplier = this.conversionTable[0][1].length();
/* 241:    */     }
/* 242:    */     catch (FileNotFoundException localFileNotFoundException)
/* 243:    */     {
/* 244:276 */       error(105);
/* 245:    */     }
/* 246:    */     catch (IOException localIOException)
/* 247:    */     {
/* 248:279 */       error(104);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   String getAsciiValue(String paramString)
/* 253:    */   {
/* 254:287 */     int i = 0;int j = this.conversionTable.length;int k = 0;int m = 0;
/* 255:288 */     while (i < j)
/* 256:    */     {
/* 257:289 */       k = i + j >> 1;
/* 258:290 */       m = paramString.compareTo(this.conversionTable[k][1]);
/* 259:291 */       if (m == 0) {
/* 260:292 */         return this.conversionTable[k][0];
/* 261:    */       }
/* 262:294 */       if (m > 0) {
/* 263:294 */         i = k;
/* 264:    */       } else {
/* 265:295 */         j = k;
/* 266:    */       }
/* 267:    */     }
/* 268:298 */     return "*";
/* 269:    */   }
/* 270:    */   
/* 271:    */   synchronized void readPatternFile(String paramString)
/* 272:    */   {
/* 273:313 */     String str = null;
				  String[] rtf_output;
    			  
								
					try 
					{	
						FileInputStream stream = new FileInputStream(paramString);
						RTFEditorKit kit = new RTFEditorKit();
						Document doc = kit.createDefaultDocument();
						kit.read(stream, doc, 0);
					//out = editor.getText();
					//System.out.println(editor.getDocument()); 
					//System.out.println(rtf.toString());
						//RTFEditorKit rtf = new RTFEditorKit();
						//JEditorPane editor = new JEditorPane();
						//editor.setEditorKit(rtf);
						//editor.setContentType("text/rtf");
						//editor.setBackground( Color.white );

						// This text could be big so add a scroll pane
						//JScrollPane scroller = new JScrollPane();
						//scroller.getViewport().add( editor );
						//topPanel.add( scroller, BorderLayout.CENTER );

						// Load an RTF file into the editor
						/*try 
						{
							FileInputStream fi = new FileInputStream( "towns.rtf" );
							rtf.read( fi, editor.getDocument(), 0 );
						}
						catch( FileNotFoundException e )
						{
							System.out.println( "File not found" );
						}
						catch( IOException e )
						{
							System.out.println( "I/O error" );
						}
						catch( BadLocationException e )
						{
						}*/
					try
	    			  {
						//File file = new File(editor.getText());
						//Document file = new Document(editor.getDocument());
						//file.open();
						//FileOutputStream str1 = new FileOutputStream(file);
						/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try
						{
						   rtf.write(baos, editor, 0, editor.length());
						   //rtf_output = baos.toString();
						}
						catch (IOException e)
						{
						   e.printStackTrace();
						}
						catch( BadLocationException e )  
						{  
						} */
						/*catch (NullPointerException npe) {
						}*/
						//FileInputStream output = new FileInputStream(out);
						//OutputStream out = baos.toString();
						//FileInputStream in1 = new FileInputStream(str1);
						//BufferedReader in = new BufferedReader(new InputStreamReader(in1));
						//DataInputStream dis = new DataInputStream(baos.toString());
						//String s = baos.toString();
						//StringReader sr = new StringReader(editor.getText().toString());
						//InputStream input = sr;
						//String output = editor.getText().toString();
						//InputStream input = new ByteArrayInputStream(output.getBytes("UTF-8"));
						//BufferedReader in = new BufferedReader(new InputStreamReader(input,"UTF-8"));
						String plainText = doc.getText(0, doc.getLength());
						rtf_output = plainText.split("\\n");
						
	/* 279:317 */         int i = Integer.parseInt(rtf_output[0]);
	/* 280:318 */         int j = Integer.parseInt(rtf_output[1]);
	/* 281:319 */         if (j * this.multiplier != this.neuronLayerArray[0].size()) {
	/* 282:320 */           error(106);
	/* 283:    */         }
	/* 284:321 */         int k = Integer.parseInt(rtf_output[2]);
	/* 285:322 */         if (k * this.multiplier != this.neuronLayerArray[this.lastLayer].size()) {
	/* 286:323 */           error(107);
	/* 287:    */         }
	/* 288:324 */         this.inputPatternArray = new Pattern[i];
	/* 289:325 */         this.targetPatternArray = new Pattern[i];
	/* 290:326 */         this.outputPatternArray = new String[i];
	/* 291:327 */         this.lastPattern = (this.inputPatternArray.length - 1);
	/* 292:328 */         this.layerOutputError = new float[this.lastPattern + 1][this.neuronLayerArray[this.lastLayer].size()];
	/* 293:329 */         for (int m = 0; m < i; m++)
	/* 294:    */         {
	/* 295:330 */           str = rtf_output[m+3];
	/* 296:331 */           if (str == null)
	/* 297:    */           {
	/* 298:332 */             error(102);
	/* 299:    */           }
	/* 300:334 */           else if (str.length() != j + k + 1)
	/* 301:    */           {
	/* 302:335 */             error(100);
	/* 303:    */           }
	/* 304:    */           else
	/* 305:    */           {
	/* 306:337 */             this.inputPatternArray[m] = new Pattern(str.substring(0, j), this.conversionTable);
	/* 307:338 */             this.targetPatternArray[m] = new Pattern(str.substring(j + 1), this.conversionTable);
	/* 308:339 */             this.outputPatternArray[m] = new String();
	/* 309:    */           }
	/* 310:    */         }
	/* 311:    */       	    
					}
					catch (NumberFormatException nfe) 
					{
					}
					catch (NullPointerException npe) 
					{
					}
					catch( BadLocationException e )
					{
					}
					
					}
					catch( FileNotFoundException e )  
					{  
					System.out.println( "File not found" );  
					}  
					catch( IOException e )  
					{  
					System.out.println( "I/O error" );  
					} 
					catch( BadLocationException e )
					{
					}
										
  				}
 		}


/* Location:           C:\Users\Harshit\Downloads\Compressed\bpn\
 * Qualified Name:     BackpropagationNet
 * JD-Core Version:    0.7.0.1
 */