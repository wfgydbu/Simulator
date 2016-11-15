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
 * @since OCT 4,2015
 * 
 */
public class TRR extends Procedures{
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        
        String pattern = "([0-3],)([0-3])";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        }
        
        
        int phase0 = 0;
        int phase1 = 0;
        
        String temp[] = insCommonResources.instruction2.split(",");  
        
        //
        phase0 = Integer.parseInt(temp[0]);
        phase1 = Integer.parseInt(temp[1]);
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),1)        
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),5);
 
        System.out.println("Encode TRR content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words){
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        byte[] RY = new byte[2];
        int iRX = 0;
        int iRY = 0;
        //first ALU input
        byte[] fALUInput = new byte[16];
        //second ALU input
        byte[] sALUInput = new byte[16];
        LogicOperation insLogicOperation = new LogicOperation();
        
        insCommonResources.GetPartofBytes(words, RX, 6, 7);
        insCommonResources.GetPartofBytes(words, RY, 8, 9);
        
        iRX = insCommonResources.setByteToInteger(RX);
        iRY = insCommonResources.setByteToInteger(RY);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RY:" + iRY + "\n");
         
        switch(iRX){
            case 0:
               fALUInput = insCommonResources.R0;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R0)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
               break;
            case 1:
               fALUInput = insCommonResources.R1;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R1)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n");              
               break;
            case 2:
               fALUInput = insCommonResources.R2;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R2)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");              
               break;
            case 3:
               fALUInput = insCommonResources.R3;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R3)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R3) + "\n");              
               break;
            default:
               break;
        }
        
        switch(iRY){
            case 0:
               sALUInput = insCommonResources.R0;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R0)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
               break;
            case 1:
               sALUInput = insCommonResources.R1;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R1)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n");              
               break;
            case 2:
               sALUInput = insCommonResources.R2;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R2)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");              
               break;
            case 3:
               sALUInput = insCommonResources.R3;
               insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R3)->ALU, the second input is:" + insCommonResources.setByteToString(insCommonResources.R3) + "\n");              
               break;
            default:
               break;
        }
        
        if(insLogicOperation.TestEqualityBetweenRegisters(fALUInput, sALUInput, insCommonResources)){
            insCommonResources.SetCC(3, 1);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]The test result is: Equal", "\n");
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter + "]ALU->CC:" + insCommonResources.setByteToString(insCommonResources.CC) + "\n");
        }
        else{
            insCommonResources.SetCC(3, 0);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]The test result is: Not Equal", "\n");
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter + "]ALU->CC:" + insCommonResources.setByteToString(insCommonResources.CC) + "\n");
        }
                
        insCommonResources.GUI.RefreshPane();
        return true;
    }   
}
