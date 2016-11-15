/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;

import java.util.ArrayList;
import java.util.List;

/**
* This class defines the instructions list which contains names of each instruction
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
*/
public class InstructionsList {
    List instructions = new ArrayList();
    
    //contructor of thie class, defines all the instrcutions and add them into List
    InstructionsList () {
        for (int m = 0; m < 100; m++) {
            instructions.add("NULL");
        }
            
        instructions.set(0,"HLT");
        instructions.set(1,"LDR");
        instructions.set(2,"STR");
        instructions.set(3,"LDA");
        instructions.set(41,"LDX");
        instructions.set(42,"STX");
        instructions.set(7,"SIR");
        instructions.set(6,"AIR");
        instructions.set(4,"AMR");
        instructions.set(5,"SMR");
        instructions.set(10, "JZ");
        instructions.set(11, "JNE");
        instructions.set(12, "JCC");
        instructions.set(13, "JMA");
        instructions.set(14, "JSR");
        instructions.set(15, "RFS");
        instructions.set(16, "SOB");
        instructions.set(17, "JGE");
        instructions.set(20, "MLT");
        instructions.set(21, "DVD");
        instructions.set(22, "TRR");
        instructions.set(23, "AND");
        instructions.set(24, "ORR");
        instructions.set(25, "NOT");
        instructions.set(31, "SRC");
        instructions.set(32, "RRC");
        instructions.set(36, "TRAP");
        instructions.set(61, "IN");
        instructions.set(62, "OUT");
        instructions.set(63, "CHK");
        
        instructions.set(33, "FADD");
        instructions.set(34, "FSUB");
        instructions.set(35, "VADD");
        instructions.set(38, "VSUB");
        
        instructions.set(37, "CNVRT");
        instructions.set(50, "LDFR");
        instructions.set(51, "STFR");
    }
    
    /**
        * @description check the instruction if it contains an effective operation code
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param instruction the instruction you type in
        * @param insCommonResources public resource pool
        * @return 1-success 0-fail
        * @edit record:
    */
    public boolean VerifyOpcode(String instruction, CommonResources insCommonResources) {
        if(instruction.isEmpty()) {
            return false;
        }
       
        if (instruction.equalsIgnoreCase("HLT")) {
            insCommonResources.GUI.AddContectToPane("[VerifyOpcode]","Encode instruction successfully:" + instruction.toUpperCase() + "\n");
            insCommonResources.opcode = 0;
            insCommonResources.opcode_str = "HLT";
        
            System.out.println("VerifyOpcode:Set opcode:" + 0);
            System.out.println("VerifyOpcode:Set opcode_str:" + "HLT");
            return true;
        }
        
        String temp[] = instruction.split(" ");  
        insCommonResources.instruction1 = temp[0].toUpperCase();
        insCommonResources.instruction2 = temp[1];
        
        if(temp.length != 2 || temp[0].isEmpty() || temp[1].isEmpty()) {
            return false;
        }
        
        System.out.println("VerifyOpcode:Encode instruction1:" + insCommonResources.instruction1);
        System.out.println("VerifyOpcode:Encode instruction1:" + insCommonResources.instruction2);
        insCommonResources.GUI.AddContectToPane("[VerifyOpcode]","Encode 1st instruction successfully:" + insCommonResources.instruction1 + "\n");
        insCommonResources.GUI.AddContectToPane("[VerifyOpcode]","Encode 2nd instruction successfully:" + insCommonResources.instruction2 + "\n");        
        
        int res = FindInstrcution(insCommonResources);
        if( 0 == res){
            return false;
        }
        insCommonResources.opcode = res;
        insCommonResources.opcode_str = insCommonResources.instruction1;
        
        System.out.println("VerifyOpcode:Set opcode:" + res);
        System.out.println("VerifyOpcode:Set opcode_str:" + insCommonResources.instruction1);
            
        
       return true;
   }
   
    /**
        * @description find the instructionss in the List
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param insCommonResources public resource pool
        * @return the index of the instruction in the List
        * @edit record:
    */
   public int FindInstrcution(CommonResources insCommonResources){
       //System.out.println("XXX   " + this.instructions.size());
        for(int i = 0; i < this.instructions.size(); i++) {
           if(this.instructions.get(i).equals(insCommonResources.instruction1)) {
               System.out.println("FindInstrcution:successfully find the index of instruction:" + i);
               insCommonResources.GUI.AddContectToPane("[FindInstrcution]","match operation code successfully\n");  
               return i;
           }
        }
        System.out.println("FindInstrcution:Failed to find the index of instruction.");
        insCommonResources.GUI.AddContectToPane("[FindInstrcution]","failed match operation code\n");  
        return 0;
   }
   
   /**
        * @description get the name of the instruction by index
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param num index
        * @return the name of the instruction in the List
        * @edit record:
    */
   public String getInstructionName (int num) {
       String str = this.instructions.get(num).toString();
       
       return str;
   }
   
   /**
        * @description get the index of the instruction by name
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param src the name of the instruction
        * @return the index of the instruction in the List
        * @edit record:
    */
   public int getInstructionNumber(String src) {
       for(int i = 0; i < this.instructions.size(); i++) {
           if(this.instructions.get(i).equals(src)) {
               return i;
           }
        }
       return -1;
   }
}
