/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo.Instructions;

import java.awt.Color;
import project.demo.CommonResources;
import project.demo.Procedures;

/**
 *
 * @author yangzhe
 */
public class HLT extends Procedures{
    
    @Override
    public boolean EncodeContent(CommonResources insCommonResources, byte[] specificword) {
        
        int phase0 = 0;
        int phase1 = 0;
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(phase0),4)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase1),6);
 
        System.out.println("Encode HLT content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }

    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPaneForERROR("Application is shutted down.\nPlease reset or close the application.\n");
        
        insCommonResources.GUI.SetAllButtonsDisabled();
        insCommonResources.GUI.RefreshPane();
        
        insCommonResources.PC = insCommonResources.setIntegerToByte(insCommonResources.PC, 1001);
        return true;
    }
    
}
