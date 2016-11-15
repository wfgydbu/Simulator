/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;
import project.demo.InstructionsList;
/**
 *
 * @author 一天
 */
public class ErrorControl {
        /**
        * @description Handle the error including TRAP and Machine Fault.
        * @author Yitian Huang
        * @version 1.0
        * @since Nov 3
        * @param insCommonResources public resources pool
        * @param code if type=0, code=0,3; if type=1, code=0,15
        * @return 1-success 0-fail
        * @edit record:
    */
    public boolean MachineFaultHandler (CommonResources insCommonResources, int code) {
        //set MSR
        switch(code) {
        	case 0:
            		insCommonResources.MSR = insCommonResources.setIntegerToByte(insCommonResources.MSR, 0);
            		insCommonResources.GUI.AddContectToPaneForERROR("[Machine Fault] Illegal Memory Address to Reserved Locations.\n");
            		break;
		case 1: 
			insCommonResources.MSR = insCommonResources.setIntegerToByte(insCommonResources.MSR, 1);
			insCommonResources.GUI.AddContectToPaneForERROR("[Machine Fault] Illegal TRAP code.\n");
			break;
		case 2:
			insCommonResources.MSR = insCommonResources.setIntegerToByte(insCommonResources.MSR, 2);
			insCommonResources.GUI.AddContectToPaneForERROR("[Machine Fault] Illegal Operation Code.\n");
			break;
		case 3:
			insCommonResources.MSR = insCommonResources.setIntegerToByte(insCommonResources.MSR, 3);
			insCommonResources.GUI.AddContectToPaneForERROR("[Machine Fault] Illegal Memory Address beyond 4096.\n");
			break;
		default:
			break;
			
        }
        
        
        //store PC into MEM(44)
        byte[] PCcontent = new byte [16];
        PCcontent = insCommonResources.setStringToByte(PCcontent, insCommonResources.VerifyMachineString(insCommonResources.setByteToString(insCommonResources.PC),16), 0, 15);
        //insCommonResources.setStorge(insCommonResources, PCcontent, 44);
        insCommonResources.insCache.insMemory.Memory[44] = PCcontent;
	//insCommonResources.setStorge(insCommonResources, insCommonResources.PC, 44);
        insCommonResources.GUI.AddContectToPane("[MachineFault]PC->MEM(44): " + insCommonResources.setByteToString(PCcontent) +"\n");

		
        //load MEM(41) to PC
        insCommonResources.PC = insCommonResources.GetPartofBytes(insCommonResources.getStorge(insCommonResources,41), 4, 15);       
        insCommonResources.GUI.AddContectToPane("[MachineFault]MEM(41)->PC: " + insCommonResources.setByteToString(insCommonResources.PC) +"\n");
        
        int pos = insCommonResources.setByteToInteger(insCommonResources.PC);
        pos = pos + 1;
        insCommonResources.setIntegerToByte(insCommonResources.PC, pos);

        insCommonResources.GUI.RefreshPane();
        
        
        return true;
    }
}
