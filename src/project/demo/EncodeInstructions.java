/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.demo.Instructions.*;

/**
* This class contains the methods to encode instructions
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
* @author Zhe Yang
* @verison 2.0 
* @since OCT 4,2015
* 
*/
public class EncodeInstructions {
    /**
        * @description encode instructions in the List. and then Load then into memory by using PC.
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param CommonBuffer A List contains the instructions need to be encoded
        * @param insCommonResources the public resources pool
        * @return 1-success 0-fail
        * @edit record:
    */
    public boolean EncodeOpcode (List CommonBuffer, CommonResources insCommonResources){
        String instruction = new String();
        byte[] specificword;
        //loop, to encode all the instructions in the List
        while(true) {
            specificword = new byte[16];
            //judge if the list is empty
            if(CommonBuffer.size() == 0 ) {
                insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "The instructions sequence is empty, all instructions has been encoded.\n");
                return true;
             }
            
            //encode the last instrction in the List first, so we can execute them in the order as we type in.
            instruction = (String)CommonBuffer.get(CommonBuffer.size() - 1);
            CommonBuffer.remove(CommonBuffer.size() - 1);           
            
            System.out.println("EncodeOpcode:Successfully receive raw instrucion:" + instruction);
            insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Receive raw instrucion:" + instruction + "\n");
            insCommonResources.GUI.AddContectToPane("[EncodeOpcode]Start to encode this instruction...","\n");
            
            //Is this command effective?
            InstructionsList insInstructionsList = new InstructionsList();
            if ( false == insInstructionsList.VerifyOpcode(instruction, insCommonResources) ) {
                insCommonResources.GUI.AddContectToPane("[EncodeOpcode]Invald operation code","\n");
                ErrorControl insErrorControl = new ErrorControl();
                insErrorControl.MachineFaultHandler(insCommonResources, 2);
                int mpos = insCommonResources.setByteToInteger(insCommonResources.PC);
                mpos = mpos - 1;
                insCommonResources.setIntegerToByte(insCommonResources.PC, mpos);
                
                insCommonResources.GUI.ExecuteClick();
                return false;
            }   
            
            //
            //insCommonResources.SetZero(specificword);

            //According to opcode_str, decide which exection should to be used
            switch(insCommonResources.opcode_str) {
                case "LDR" :
                    //put the opcode into IOR register
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    LDR insLDR = new LDR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insLDR.EncodeContent(insCommonResources,specificword);
                    insLDR.LoadIntoMemory(insCommonResources, specificword);
                    break;
                case "STR": 
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    STR insSTR = new STR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSTR.EncodeContent(insCommonResources,specificword);
                    insSTR.LoadIntoMemory(insCommonResources, specificword);
                    break;
                case "LDA":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    LDA insLDA = new LDA();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insLDA.EncodeContent(insCommonResources, specificword);
                    insLDA.LoadIntoMemory(insCommonResources, specificword);
                    break;
                case "LDX":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    LDX insLDX = new LDX();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insLDX.EncodeContent(insCommonResources, specificword);
                    insLDX.LoadIntoMemory(insCommonResources, specificword);
                    break;    
                case "STX":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    STX insSTX = new STX();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSTX.EncodeContent(insCommonResources, specificword);
                    insSTX.LoadIntoMemory(insCommonResources, specificword);
                    break;  
                case "AMR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    AMR insAMR = new AMR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insAMR.EncodeContent(insCommonResources, specificword);
                    insAMR.LoadIntoMemory(insCommonResources, specificword);
                    break;    
                case "SMR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    SMR insSMR = new SMR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSMR.EncodeContent(insCommonResources,specificword);
                    insSMR.LoadIntoMemory(insCommonResources,specificword);
					break;
                case "AIR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    AIR insAIR = new AIR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insAIR.EncodeContent(insCommonResources, specificword);
                    insAIR.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "SIR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    SIR insSIR = new SIR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSIR.EncodeContent(insCommonResources, specificword);
                    insSIR.LoadIntoMemory(insCommonResources, specificword); 
                
                    break; 
                case "JZ":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    JZ insJZ = new JZ();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insJZ.EncodeContent(insCommonResources, specificword);
                    insJZ.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "JNE":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    JNE insJNE = new JNE();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insJNE.EncodeContent(insCommonResources, specificword);
                    insJNE.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "JCC":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    JCC insJCC = new JCC();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insJCC.EncodeContent(insCommonResources, specificword);
                    insJCC.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "JMA":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    JMA insJMA = new JMA();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insJMA.EncodeContent(insCommonResources, specificword);
                    insJMA.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "JSR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    JSR insJSR = new JSR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insJSR.EncodeContent(insCommonResources, specificword);
                    insJSR.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "RFS":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    RFS insRFS = new RFS();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insRFS.EncodeContent(insCommonResources, specificword);
                    insRFS.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "SOB":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    SOB insSOB = new SOB();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSOB.EncodeContent(insCommonResources, specificword);
                    insSOB.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "JGE":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    JGE insJGE = new JGE();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insJGE.EncodeContent(insCommonResources, specificword);
                    insJGE.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "MLT":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    MLT insMLT = new MLT();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insMLT.EncodeContent(insCommonResources, specificword);
                    insMLT.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "DVD":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    DVD insDVD = new DVD();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insDVD.EncodeContent(insCommonResources, specificword);
                    insDVD.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "TRR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    TRR insTRR = new TRR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insTRR.EncodeContent(insCommonResources, specificword);
                    insTRR.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "AND":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    AND insAND = new AND();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insAND.EncodeContent(insCommonResources, specificword);
                    insAND.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "ORR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    ORR insORR = new ORR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insORR.EncodeContent(insCommonResources, specificword);
                    insORR.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "NOT":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    NOT insNOT = new NOT();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insNOT.EncodeContent(insCommonResources, specificword);
                    insNOT.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "SRC":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    SRC insSRC = new SRC();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSRC.EncodeContent(insCommonResources, specificword);
                    insSRC.LoadIntoMemory(insCommonResources, specificword);
                    
                    break;
                case "RRC":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    RRC insRRC = new RRC();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insRRC.EncodeContent(insCommonResources, specificword);
                    insRRC.LoadIntoMemory(insCommonResources, specificword);
 					break;
                case "IN":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IN successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    IN insIN = new IN();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insIN.EncodeContent(insCommonResources, specificword);
                    insIN.LoadIntoMemory(insCommonResources, specificword);;   
                
                    break; 
                case "OUT":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set OUT successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    OUT insOUT = new OUT();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insOUT.EncodeContent(insCommonResources, specificword);
                    insOUT.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "TRAP":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set TRAP successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    TRAP insTRAP = new TRAP();
                    insCommonResources.SetOpcodeInWords(specificword);
                    if (false == insTRAP.EncodeContent(insCommonResources, specificword)) {
                        return false;
                    }
                    insTRAP.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "CHK":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");                

                    CHK insCHK = new CHK();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insCHK.EncodeContent(insCommonResources, specificword);
                    insCHK.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "HLT":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    HLT insHLT = new HLT();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insHLT.EncodeContent(insCommonResources, specificword);
                    insHLT.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "FADD":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    FADD insFADD = new FADD();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insFADD.EncodeContent(insCommonResources, specificword);
                    insFADD.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "FSUB":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    FSUB insFSUB = new FSUB();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insFSUB.EncodeContent(insCommonResources, specificword);
                    insFSUB.LoadIntoMemory(insCommonResources, specificword);
                
                    break;
                case "VADD":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    VADD insVADD = new VADD();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insVADD.EncodeContent(insCommonResources, specificword);
                    insVADD.LoadIntoMemory(insCommonResources, specificword);
                
                    break;   
                case "VSUB":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    VSUB insVSUB = new VSUB();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insVSUB.EncodeContent(insCommonResources, specificword);
                    insVSUB.LoadIntoMemory(insCommonResources, specificword);
                
                    break;      
                case "CNVRT":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    CNVRT insCNVRT = new CNVRT();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insCNVRT.EncodeContent(insCommonResources, specificword);
                    insCNVRT.LoadIntoMemory(insCommonResources, specificword);
                
                    break;  
                case "LDFR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    LDFR insLDFR = new LDFR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insLDFR.EncodeContent(insCommonResources, specificword);
                    insLDFR.LoadIntoMemory(insCommonResources, specificword);
                
                    break; 
                case "STFR":
                    insCommonResources.IOR = insCommonResources.setIntegerToByte(insCommonResources.IOR, insCommonResources.opcode);
                    insCommonResources.GUI.AddContectToPane("[EncodeOpcode]", "Set IOR successfully:" + insCommonResources.setByteToString(insCommonResources.IOR) + "\n");  
                    
                    STFR insSTFR = new STFR();
                    insCommonResources.SetOpcodeInWords(specificword);
                    insSTFR.EncodeContent(insCommonResources, specificword);
                    insSTFR.LoadIntoMemory(insCommonResources, specificword);
                
                    break;     
                default :
                    break;
            }
            
            insCommonResources.GUI.AddContectToPane("[EncodeOpcode]Finish to encode this instruction...","\n\n");
        }
        
        
  
        
        
    }
    
  
    
    
}
