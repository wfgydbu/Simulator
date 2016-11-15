/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.Instructions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project.demo.CommonResources;
import project.demo.ExecuteInstructions;
import project.demo.Procedures;

/**
 *
 * @author Zhe Yang
 * @verison 1.0 
 * @since OCT 4,2015
 * 
 */
public class RFS extends Procedures{
    
    @Override
    public boolean EncodeContent(CommonResources insCommonResources,byte[] specificword){
        
        String pattern = "([0-9]|[1-2]{1}[0-9]{1}|30|31)";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        }
        
        int phase0 = 0;
                
        String temp = insCommonResources.instruction2;  
        
        //
        phase0 = Integer.parseInt(temp);
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),2)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(0),1)        
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),5);
 
        System.out.println("Encode RFS content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }
    
    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words){
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        //byte[] RX = new byte[2];
        //byte[] IX = new byte[2];
        //byte[] I = new byte[1];
        byte[] Immed = new byte[5];
        int iImmed = 0;
        //int iRX = 0;
        //int iIX = 0;
        //int iI = 0;
        //int EA = 0;
        String str = new String();
        //int pos = insCommonResources.getByteUnits(insCommonResources.PC);
        
        insCommonResources.GetPartofBytes(words, Immed, 11, 15);
        
        str = insCommonResources.setByteToString(Immed);
        iImmed = insCommonResources.setByteToInteger(Immed);
        str = insCommonResources.VerifyMachineString(str, insCommonResources.IAR.length);
        insCommonResources.setStringToByte(insCommonResources.IAR, str, 0, 15);        
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IR->IAR:", 
                insCommonResources.setByteToString(insCommonResources.IAR) +"\n");
        
        //insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:IX:" + iIX + "\n");
        //insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:RX:" + iRX + "\n");
        //insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:I:" + iI + "\n");
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully:Address:" + iImmed + "\n");
         
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode immediate number:" + iImmed + "\n");
        
        //
        insCommonResources.MAR = insCommonResources.IAR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IAR->MAR:", insCommonResources.setByteToString(insCommonResources.MAR) +"\n");      
        
        //save Immed to R0
        insCommonResources.IRR = insCommonResources.MAR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]MAR->IRR:", insCommonResources.setByteToString(insCommonResources.R3) +"\n");      
        insCommonResources.R0 = insCommonResources.IRR;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]IRR->R0:", insCommonResources.setByteToString(insCommonResources.R3) +"\n");      

        //load C(R3) to PC
        insCommonResources.PC = insCommonResources.GetPartofBytes(insCommonResources.R3, 4, 15);
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter + "]c(R3)->PC:", insCommonResources.setByteToString(insCommonResources.PC) +"\n");
        
        
        insCommonResources.GUI.RefreshPane();
        return true;
    }
}
