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
public class NOT extends Procedures{
    
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        
        String pattern = "([0-3])";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        }
        
        int phase0 = 0;
        
        String temp[] = insCommonResources.instruction2.split(",");  
        
        //
        phase0 = Integer.parseInt(temp[0]);
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),1)        
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),5);
 
        System.out.println("Encode NOT content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words){
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] RX = new byte[2];
        int iRX = 0;
        //first ALU input
        byte[] fALUInput = new byte[16];
        LogicOperation insLogicOperation = new LogicOperation();
        
        insCommonResources.GetPartofBytes(words, RX, 6, 7);
        
        iRX = insCommonResources.setByteToInteger(RX);
        
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
         
        switch(iRX){
            case 0:
                fALUInput = insCommonResources.R0;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R0)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
                insCommonResources.R0 = insLogicOperation.returnLogicalNOTByte(fALUInput);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R0:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
                break;
            case 1:
                fALUInput = insCommonResources.R1;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R1)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n"); 
                insCommonResources.R1 = insLogicOperation.returnLogicalNOTByte(fALUInput);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R1:" + insCommonResources.setByteToString(insCommonResources.R1) + "\n");
                break;
            case 2:
                fALUInput = insCommonResources.R2;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R2)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");
                insCommonResources.R2 = insLogicOperation.returnLogicalNOTByte(fALUInput);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R2:" + insCommonResources.setByteToString(insCommonResources.R2) + "\n");
                break;
            case 3:
                fALUInput = insCommonResources.R3;
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]C(R3)->ALU, the first input is:" + insCommonResources.setByteToString(insCommonResources.R3) + "\n"); 
                insCommonResources.R3 = insLogicOperation.returnLogicalNOTByte(fALUInput);
                insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]ALU->R3:" + insCommonResources.setByteToString(insCommonResources.R0) + "\n");
                break;
            default:
               break;
        }
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }
}
