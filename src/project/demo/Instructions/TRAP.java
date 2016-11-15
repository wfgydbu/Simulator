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
import project.demo.ErrorControl;

/**
 *
 * @author yangzhe
 */
public class TRAP extends Procedures{

    @Override
    public boolean EncodeContent(CommonResources insCommonResources, byte[] specificword) {

        String pattern = "([0-9]|[1]{1}[0-5]{1})";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(insCommonResources.instruction2);
        
        if( mat.matches() == false) {
            insCommonResources.flag_content = false;
            ErrorControl insErrorControl = new ErrorControl();
            insErrorControl.MachineFaultHandler(insCommonResources, 1);
            int mpos = insCommonResources.setByteToInteger(insCommonResources.PC);
                mpos = mpos - 1;
                insCommonResources.setIntegerToByte(insCommonResources.PC, mpos);
                
                insCommonResources.GUI.ExecuteClick();
            insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Content format is invald:" + insCommonResources.instruction2 + ".Discard and encode next instruction\n");
            return false;
        }
        
        int phase = Integer.parseInt(insCommonResources.instruction2);
        
        String res = insCommonResources.VerifyMachineString(Integer.toBinaryString(0),6)
                        + insCommonResources.VerifyMachineString(Integer.toBinaryString(phase),4);    
        
        System.out.println("Encode TRAP content: " +  res);
           
        specificword = insCommonResources.setStringToByte(specificword, res, 6,15);
        
        insCommonResources.GUI.AddContectToPane("[EncodeContent]", "Encode words successfully:" + insCommonResources.setByteToString(specificword) + "\n");
        insCommonResources.flag_content = true;
        return true;
    }

    @Override
    public boolean GenerateProcedures(CommonResources insCommonResources, byte[] words) {
        insCommonResources.GUI.AddContectToPane("Start to execute...", "\n");
        byte[] TrapCode = new byte[4];
        int iTrapCode = 0;
        int tableEntry = 0;
        int routineLength = 20;
        
        TrapCode = insCommonResources.GetPartofBytes(words, 12, 15);
        iTrapCode = insCommonResources.setByteToInteger(TrapCode);
        insCommonResources.GUI.AddContectToPane("[GenerateProcedures]", "Encode contents successfully: Trap Code:" + iTrapCode + "\n");
        
        //store the value of PC to MEM(42)       
        byte[] PCcontent = new byte [16];
        PCcontent = insCommonResources.setStringToByte(PCcontent, insCommonResources.VerifyMachineString(insCommonResources.setByteToString(insCommonResources.PC),16), 0, 15);
        //insCommonResources.setStorge(insCommonResources, PCcontent, 42);   
        insCommonResources.insCache.insMemory.Memory[42] = PCcontent;
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]PC->MEM(42): " + insCommonResources.setByteToString(PCcontent) +"\n");
        
        //load appropriate entry address of MEM(40) to PC
        tableEntry = insCommonResources.setByteToInteger(insCommonResources.getStorge(insCommonResources, 40)) + (iTrapCode * routineLength);
        insCommonResources.PC = insCommonResources.setIntegerToByte(insCommonResources.PC, tableEntry);
        insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]MEM(40)->PC: ", insCommonResources.setByteToString(insCommonResources.PC) +"\n");
        
        int pos = insCommonResources.setByteToInteger(insCommonResources.PC);
        pos = pos + 1;
        insCommonResources.setIntegerToByte(insCommonResources.PC, pos);
        insCommonResources.GUI.RefreshPane();
        
        //insCommonResources.GUI.RefreshPane();
        return true; 
    }
    
}
