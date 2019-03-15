package KOS;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
public class CPU
{
    public int programCounter;
    public static int count = 0;
    public final int registrySize = 16;

    public int procSize;
    public int addr;
    public long stateAddress;
    public short op, s1Registery, s2Registry, registry1, registry2, dataRegistry, bufferRegistry, instructionType;
    public int[] registry;
    public short[] buffer;
    public int inputBuffer;
    public int outputBuffer;
    public int tempBuffer;
    public ProcessControl pcb;
    public FileWriter writer;

    CPU()
    {
        registry = new int[500];
        registry[0] = 0;
        try
        {
            writer = new FileWriter("CPUOUTPUT.txt", true);
        }
        catch(IOException ex)
        {

        }
    }
    private void writeToFile(String a)
    {
        try
        {
            writer.write(a);
        }
        catch (IOException ex)
        {

        }
    }
    private void execute(int id, int decodedInstructions)
    {
        if((this.op < 26 || this.op > 0))
        {
            if(op == 0)
            {
                writeToFile("Read content of Input/Output buffer to Accumulator.");
            }
            else if(op == 1)
            {
                writeToFile("Read content of Input/Output buffer to Accumulator.");

            }
            else if(op == 2)
            {
                writeToFile("Writes the content of accumulator into O/P buffer");
            }
            else if(op == 3)
            {
                writeToFile("Stores content of a reg. into an address");
            }
            else if(op == 4)
            {
                writeToFile("Transfers the content of one register into another");
            }
            else if(op == 5)
            {
                writeToFile("Adds content of two S-regs into D-reg");
            }
            else if(op == 6)
            {
                writeToFile("Subtracts content of two S-regs into D-reg");
            }
            else if(op == 7)
            {
                writeToFile("Multiplies content of two S-regs into D-reg");
            }
            else if(op == 8)
            {
                writeToFile("Divides content of two S-regs into D-reg");
            }
            else if(op == 9)
            {
                writeToFile("Logical AND of two S-regs into D-reg");
            }
            else if(op == 10)
            {
                writeToFile("Logical OR of two S-regs into D-reg");
            }
            else if(op == 11)
            {
                writeToFile("Transfers address/data directly into a register");
            }
            else if(op == 12)
            {
                writeToFile("Adds a data value directly to the content of a register");
            }
            else if(op == 13)
            {
                writeToFile("Multiplies a data value directly with the content of a register");
            }
            else if(op == 14)
            {
                writeToFile("Divides a data directly to the content of a register");
            }
            else if(op == 15)
            {
                writeToFile("Loads a data/address directly to the content of a register");
            }
            else if(op == 16)
            {
                writeToFile("Sets the D-reg to 1 if first S-reg is less than the B-reg; 0 otherwise");
            }
            else if(op == 17)
            {
                writeToFile("Sets the D-reg to 1 if first S-reg is less than a data; 0 otherwise");
            }
            else if(op == 18)
            {
                writeToFile("Logical end of program");
            }
            else if(op == 19)
            {
                writeToFile("Does nothing and moves to next instruction");
            }
            else if(op == 21)
            {
                writeToFile("Jumps to a specified location");
            }
            else if(op == 22)
            {
                writeToFile("Branches to an address when content of B-reg = D-reg");
            }
            else if(op == 23)
            {
                writeToFile("Branches to an address when content of B-reg <> D-reg");
            }
            else if(op == 24)
            {
                writeToFile("Branches to an address when content of B-reg = 0");
            }
            else if(op == 25)
            {
                writeToFile("Branches to an address when content of B-reg <> 0");
            }
            else if(op == 26)
            {
                writeToFile("Branches to an address when content of B-reg > 0");
            }
        }
    }


    private int decode(String instruction)
    {
        this.op = Short.parseShort(instruction.substring(2,8), 2);
        this.instructionType = Short.parseShort(instruction.substring(0,2), 2);
        if(instructionType == 0)
        {
            dataRegistry  = Short.parseShort(instruction.substring(16,20), 2);
            s1Registery = Short.parseShort(instruction.substring(8,12), 2);
            s2Registry = Short.parseShort(instruction.substring(12,16), 2);
        }
        else if(instructionType == 1)
        {
            bufferRegistry = Short.parseShort(instruction.substring(8,12), 2);
            dataRegistry = Short.parseShort(instruction.substring(12,16), 2);
            stateAddress = Long.parseLong(instruction.substring(16,32), 2);
        }
        else if(instructionType == 2)
        {
            stateAddress = Long.parseLong(instruction.substring(16,32), 2);

        }
        else if(instructionType == 3)
        {
            //registry1 = Short.parseShort(instruction.substring(8,12), 2);
            //registry2 = Short.parseShort(instruction.substring(12,16), 2);
            //stateAddress =  Long.parseLong(instruction.substring(16,32), 2);
            count = count+1;
        }
        else
        {
            //Nothing
        }
        return op;
    }
    private String fetchInstructions(int programCounter)
    {
        String first, second = "";
        first = Integer.toBinaryString(Main.memory.getRamInformation(programCounter));
        programCounter = programCounter + 1;
        int firstLength = first.length();
        if( first.length() < Utility.BIT8 )
        {
            for(int backward = 0; backward < Utility.BIT8 - firstLength; backward++)
            {
                first = "0" + first;
            }
            second = second + "" + first;
        }
        else
        {
            second = second +"" + first;
        }
        return second;
    }
    public int push_Address(int address)
    {
        return Math.abs(address - procSize*Utility.BIT4);
    }
    public int getBufferedData(long address)
    {
        return push_Address((int)address);
    }
    public void cpu_Start(ProcessControlVariables pcb)
    {
        writeToFile("Process #: " + pcb.getProcId());
        inputBuffer = pcb.getIBufferSize();
        tempBuffer = pcb.getOBufferSize();
        outputBuffer = pcb.getOBufferSize();
        buffer = pcb.getCBuffer();
        procSize = pcb.getProcSize();
        programCounter = pcb.getBeginMemory();

        writeToFile("Program Counter = " + programCounter);
        for(int i = programCounter; i < pcb.getEndMemory(); i++)
        {
            String getInstructions = fetchInstructions(programCounter);
            execute(pcb.getProcId(), decode(getInstructions));
            programCounter = programCounter + 4;

        }
    }
}