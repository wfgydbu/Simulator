/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.demo;
import project.demo.Instructions.*;

/**
* This class contains the execute operation with the instrucions in the Memory
*
* @author Yitian Huang
* @verison 1.0 
* @since SEP 3,2015
* 
*/
public class ExecuteInstructions {
    /**
        * @description Get the instructions need to be executed by using PC, and decode and execute them.
        * @author Yitian Huang
        * @version 1.0
        * @since Sep 3
        * @param insCommonResources the public resources pool
        * @return 1-success 0-fail
        * @edit record:
    */
    public boolean Execute(CommonResources insCommonResources, boolean flag){

        int instructiontype = 0;
        int mempos = 0;
        
        while(true) {
            insCommonResources.ClockConter = 0;
            
            //get the PC value, to find out what instrucion need to be used
            mempos = insCommonResources.setByteToInteger(insCommonResources.PC);
            insCommonResources.GUI.AddContectToPane("[Execute]", "Get Current PC value:" + insCommonResources.VerifyMachineString(Integer.toBinaryString(mempos),insCommonResources.PC.length) + "\n");
            
            //check if all the instructions in the memory has been executed.
            if( mempos == 1000) { 
                insCommonResources.GUI.AddContectToPane("[Execute]", "PC is at the top of memory, all the instrucions have been executed.\n");
                return true;
            }
            
            //put the content in position PC into MAR register
            insCommonResources.MAR = insCommonResources.setIntegerToByte(insCommonResources.MAR, mempos);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]PC->MAR ", insCommonResources.setByteToString(insCommonResources.MAR)+ "\n");
            
            
            //put content into MBR according the address in MAR
            insCommonResources.MBR = insCommonResources.getStorge(insCommonResources, mempos);
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]MEM->MBR:", insCommonResources.setByteToString(insCommonResources.MBR)+ "\n");
            
            //put the instruction in MBR into IR
            insCommonResources.IR = insCommonResources.MBR;
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]MBR->IR:", insCommonResources.setByteToString(insCommonResources.IR)+ "\n");

            //decode the instruction in IR, and check what type of instructions it is
            instructiontype = insCommonResources.setByteToInteger(insCommonResources.GetPartofBytes(insCommonResources.IR, 0, 5));
            insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter + "]extract IR, opcode:",  insCommonResources.VerifyMachineString(Integer.toBinaryString(instructiontype),insCommonResources.IOR.length) + "\n");
            
            //Match the operation type, then execute it
            switch(instructiontype) {
                case 0:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is HLT.", "\n");
                    HLT insHLT = new HLT();
                    insHLT.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 1: 
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is LDR.", "\n");
                    LDR insLDR = new LDR();
                    insLDR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 2:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is STR.", "\n");
                    STR insSTR = new STR();
                    insSTR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 3:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is LDA.", "\n");
                    LDA insLDA = new LDA();
                    insLDA.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 4:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is AMR.", "\n");
                    AMR insAMR = new AMR();
                    insAMR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 5:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is SMR.", "\n");
                    SMR insSMR = new SMR();
                    insSMR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 6:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is AIR.", "\n");
                    AIR insAIR = new AIR();
                    insAIR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 7:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is SIR.", "\n");
                    SIR insSIR = new SIR();
                    insSIR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 10:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is JZ.", "\n");
                    JZ insJZ = new JZ();
                    insJZ.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 11:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is JNE.", "\n");
                    JNE insJNE = new JNE();
                    insJNE.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 12:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is JCC.", "\n");
                    JCC insJCC = new JCC();
                    insJCC.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 13:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is JMA.", "\n");
                    JMA insJMA = new JMA();
                    insJMA.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 14:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is JSR.", "\n");
                    JSR insJSR = new JSR();
                    insJSR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 15:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is RFS.", "\n");
                    RFS insRFS = new RFS();
                    insRFS.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 16:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is SOB.", "\n");
                    SOB insSOB = new SOB();
                    insSOB.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 17:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is JGE.", "\n");
                    JGE insJGE = new JGE();
                    insJGE.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 20:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is MLT.", "\n");
                    MLT insMLT = new MLT();
                    insMLT.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 21:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is DVD.", "\n");
                    DVD insDVD = new DVD();
                    insDVD.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 22:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is TRR.", "\n");
                    TRR insTRR = new TRR();
                    insTRR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 23:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is AND.", "\n");
                    AND insAND = new AND();
                    insAND.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 24:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is ORR.", "\n");
                    ORR insORR = new ORR();
                    insORR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 25:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is NOT.", "\n");
                    NOT insNOT = new NOT();
                    insNOT.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 31:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is SRC.", "\n");
                    SRC insSRC = new SRC();
                    insSRC.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 36:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is TRAP.", "\n");
                    TRAP insTRAP = new TRAP();
                    insTRAP.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 32:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is RRC.", "\n");
                    RRC insRRC = new RRC();
                    insRRC.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 41:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is LDX.", "\n");
                    LDX insLDX = new LDX();
                    insLDX.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 42:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is STX.", "\n");
                    STX insSTX = new STX();
                    insSTX.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 61:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is IN.", "\n");
                    IN insIN = new IN();
                    insIN.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 62:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is OUT.", "\n");
                    OUT insOUT = new OUT();
                    insOUT.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 63:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is CHK.", "\n");
                    CHK insCHK = new CHK();
                    insCHK.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 33:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is FADD.", "\n");
                    FADD insFADD = new FADD();
                    insFADD.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 34:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is FSUB.", "\n");
                    FSUB insFSUB = new FSUB();
                    insFSUB.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 35:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is VADD.", "\n");
                    VADD insVADD = new VADD();
                    insVADD.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break; 
                case 38:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is VSUB.", "\n");
                    VSUB insVSUB = new VSUB();
                    insVSUB.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;
                case 37:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is CNVRT.", "\n");
                    CNVRT insCNVRT = new CNVRT();
                    insCNVRT.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break; 
                case 50:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is LDFR.", "\n");
                    LDFR insLDFR = new LDFR();
                    insLDFR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;  
                case 51:
                    insCommonResources.GUI.AddContectToPane("[CLOCK" + insCommonResources.ClockConter++ + "]Match successfully. Opeartion is STFR.", "\n");
                    STFR insSTFR = new STFR();
                    insSTFR.GenerateProcedures(insCommonResources,insCommonResources.IR);
                    break;    
                default:
                    insCommonResources.GUI.AddContectToPane("[Execute]", "Match failed. it's not a valid instruction, load next instruction.\n"); 
                    break;
            }

             
            insCommonResources.GUI.AddContectToPane("[Execute]Finish execute the instruction.","\n"); 

            //pc=pc-1, so it can execute the next instruction
            mempos = insCommonResources.setByteToInteger(insCommonResources.PC);
            mempos = mempos - 1;

            insCommonResources.PC = insCommonResources.setIntegerToByte(insCommonResources.PC, mempos);
            insCommonResources.GUI.AddContectToPane("[Execute]","PC decrement, current PC value is:" + insCommonResources.setByteToInteger(insCommonResources.PC) + "\n\n" );  
            //insCommonResources.SetZero(insCommonResources.words);
            if(flag == true) {
               return true;
            }
            
       }

    }
     
}
