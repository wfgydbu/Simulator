/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.Instructions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.demo.CommonResources;
import project.demo.Procedures;
import project.demo.units.LogicOperation;

/**
 *
 * @author Zhe Yang
 * @verison 1.0 
 * @since SCT 11,2015
 */
public class SRC extends Procedures{
    
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        //加一个正则判断式，用来判断是不是3-4个数字。
      
        String pattern = "([0-3],)([0-9]|[1]{1}[0-5]{1})(,[0-1],)([0-1])";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        }
        
        int phase0 = 0;
        int phase1 = 0;
        int phase2 = 0;
        int phase3 = 0;
        
        String temp[] = insCommonResources.instruction2.split(",");  
        
        //
        phase0 = Integer.parseInt(temp[0]);
        phase1 = Integer.parseInt(temp[1]);
        phase2 = Integer.parseInt(temp[2]);
        phase3 = Integer.parseInt(temp[3]);
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase3),1)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase2),1)  
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0), 2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),4);
 
        System.out.println("Encode SRC content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words){
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        byte[] Count = new byte[4];
        byte[] Direction = new byte[1];
        byte[] Method = new byte[1];
        int iRX = 0;
        int iCount = 0;
        //direction of shift, 0 means right and 1 means left
        int iDirection = 0;
        //method of shift, 0 means arithmetic and 1 means logical
        int iMethod = 0;
        //first ALU input
        byte[] fALUInput = new byte[16];
        byte[] ALUOutput = new byte[16];
        LogicOperation insLogicOperation = new LogicOperation();
        
        insCommonResources.GetPartofBytes(words, RX, 6, 7);
        insCommonResources.GetPartofBytes(words, Count, 12, 15);
        insCommonResources.GetPartofBytes(words, Direction, 9, 9);
        insCommonResources.GetPartofBytes(words, Method, 8, 8);
        
        iRX = insCommonResources.setByteToInteger(RX);
        iCount = insCommonResources.setByteToInteger(Count);
        iDirection = insCommonResources.setByteToInteger(Direction);
        iMethod = insCommonResources.setByteToInteger(Method);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Count:" + iCount + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Direction:" + iDirection + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Method:" + iMethod + "\n");

        //R0-R3
        switch(iRX){
            case 0:
                fALUInput = insCommonResources.R0;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R0)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
                //logical shift
                if(iMethod == 1){
                    ALUOutput = insLogicOperation.LogicalShift(fALUInput, iDirection, iCount, insCommonResources);
                }       
                //arithmetic shift
                else if(iMethod == 0){
                    ALUOutput = insLogicOperation.ArithmeticShift(fALUInput, iDirection, iCount, insCommonResources);
                }
                insCommonResources.R0 = ALUOutput;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R0:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
                break;
                
            case 1:
                fALUInput = insCommonResources.R1;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R1)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n"); 
                //logical shift
                if(iMethod == 1){
                    ALUOutput = insLogicOperation.LogicalShift(fALUInput, iDirection, iCount, insCommonResources);
                }       
                //arithmetic shift
                else if(iMethod == 0){
                    ALUOutput = insLogicOperation.ArithmeticShift(fALUInput, iDirection, iCount, insCommonResources);
                }
                insCommonResources.R1 = ALUOutput;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R1:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n");
                break;
                
            case 2:
                fALUInput = insCommonResources.R2;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R2)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");
                //logical shift
                if(iMethod == 1){
                    ALUOutput = insLogicOperation.LogicalShift(fALUInput, iDirection, iCount, insCommonResources);
                }       
                //arithmetic shift
                else if(iMethod == 0){
                    ALUOutput = insLogicOperation.ArithmeticShift(fALUInput, iDirection, iCount, insCommonResources);
                }
                insCommonResources.R2 = ALUOutput;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R2:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");
                break;
                
            case 3:
                fALUInput = insCommonResources.R3;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R3)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R3) + "\n"); 
                //logical shift
                if(iMethod == 1){
                    ALUOutput = insLogicOperation.LogicalShift(fALUInput, iDirection, iCount, insCommonResources);
                }       
                //arithmetic shift
                else if(iMethod == 0){
                    ALUOutput = insLogicOperation.ArithmeticShift(fALUInput, iDirection, iCount, insCommonResources);
                }
                insCommonResources.R3 = ALUOutput;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R3:" + insCommonResources.setByteToString(insCommonResources.R3) + "\n");
                break;
            default:
               break;
        }
        
        
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }
}
