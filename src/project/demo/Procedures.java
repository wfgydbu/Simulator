/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;

/**
* To simulate the Procedures in the computer.
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
*/
public abstract class Procedures {
    
    public abstract boolean EncodeContent(CommonResources insCommonResources, byte[] specificword);
    
    public boolean LoadIntoMemory(CommonResources insCommonResources, byte[] words){
        
        if(insCommonResources.flag_content == false) {
            return false;
        }
                
        //insCommonResources.DisplayStrInStyles(2,insCommonResources.words);
        
        int pos = insCommonResources.setByteToInteger(insCommonResources.PC);  
        pos = pos + 1;
        insCommonResources.PC = insCommonResources.setIntegerToByte(insCommonResources.PC, pos);
        
        if (false == insCommonResources.setStorge(insCommonResources, words, pos)) {
            return false;
        }
        insCommonResources.GUI.AddContectToPane("[LoadIntoMemory]", "Load words into storge successfully." + "\n");
        insCommonResources.GUI.AddContectToPane("[LoadIntoMemory]", "Load words:" + insCommonResources.setByteToString(words)+ "\n");
        insCommonResources.flag_content = false;
        return true;
    }
    
    public abstract boolean GenerateProcedures(CommonResources insCommonResources, byte[] words);
    
    
    
    
}
