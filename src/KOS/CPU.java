package KOS;

import java.io.FileWriter;
import java.io.IOException;
public class CPU implements Runnable
{
    public int programCounter, address;
    public static int count = 0;
    public final int registrySize = 16;

    public int procSize;
    public int location;
    public int procLocation;
    public long stateAddress;
    public short op, s1Registery, s2Registry, registry1, registry2, dataRegistry, bufferRegistry, instructionType;
    public int[] registry;
    public short[] buffer;
    public int inputBuffer;
    public int outputBuffer;
    public int tempBuffer;
    public ProcessControlVariables pcb;
    public FileWriter writer;
    public boolean status, jump;
    public int procId, trollCounter;
    CPU()
    {
        status = false;

    }
    public synchronized void load(ProcessControlVariables pcb_)
    {
        status = true;
        pcb = pcb_;
        jump = false;
        registry = new int[registrySize];
        registry[1] = 0;
        try
        {
            writer = new FileWriter("CPUOUTPUT.txt", true);
        }
        catch(IOException ex)
        {

        }
        run();

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

        System.out.println("OPCODE: " + this.op);

        if((this.op < 26 || this.op > 0))
        {
            if(this.op == 0)
            {
                System.out.println("Read content of Input/Output buffer to Accumulator.");
                if(stateAddress > 0)
                {
                    registry[registry1] = buffer[push_Address((int)stateAddress)];
                }
                else
                {
                    registry[registry1] = (int)buffer[push_Address(registry[registry2])];
                }
            }
            else if(this.op == 1)
            {
                System.out.println("Writes the content of accumulator into O/P buffer.");
                buffer[push_Address((int)stateAddress)] = (short) registry[registry2];

            }
            else if(this.op == 2)
            {
                System.out.println("Stores content of a reg. into an address");
                buffer[push_Address(registry[dataRegistry])] = (short)registry[bufferRegistry];
            }
            else if(this.op == 3)
            {
                System.out.println("Transfers the content of one register into another");
                registry[dataRegistry] = buffer[push_Address(bufferRegistry)];

            }
            else if(this.op == 4)
            {
                System.out.println("Adds content of two S-regs into D-reg");
                int tmp = registry[s1Registery];
                registry[s1Registery] = registry[s2Registry];
                registry[s2Registry] = tmp;
            }
            else if(this.op == 5)
            {
                System.out.println("Adds content of two S-regs into D-reg");
                //0
                registry[dataRegistry] = (short)(registry[s1Registery] + registry[s2Registry]);
            }
            else if(this.op == 6)
            {
                System.out.println("Subtracts content of two S-regs into D-reg");
                registry[dataRegistry] = (short)(registry[s1Registery] - registry[s2Registry]);
                //1
            }
            else if(this.op == 7)
            {
                System.out.println("Multiplies content of two S-regs into D-reg");
                registry[dataRegistry] = (short)(registry[s1Registery] * registry[s2Registry]);
                //2
            }
            else if(this.op == 8)
            {
                if(registry[s2Registry] != 0)
                {
                    registry[dataRegistry] = (short)(registry[s1Registery] / registry[s2Registry]);
                }
                //3
            }
            else if(this.op == 9)
            {
                System.out.println("Logical AND of two S-regs into D-reg");
                registry[dataRegistry] = (short)(registry[s1Registery] & registry[s2Registry]);
            }
            else if(this.op == 10)
            {
                System.out.println("Logical OR of two S-regs into D-reg");
                registry[dataRegistry] = (short)(registry[s1Registery] | registry[s2Registry]);
            }
            else if(this.op == 11)
            {
                System.out.println("Transfers address/data directly into a register");
                registry[dataRegistry] = (int)stateAddress;
            }
            else if(this.op == 12)
            {
                System.out.println("Adds a data value directly to the content of a register");
                registry[dataRegistry] = registry[dataRegistry] + (int) stateAddress;

            }
            else if(this.op == 13)
            {
                System.out.println("Multiplies a data value directly with the content of a register");
                registry[dataRegistry] = registry[dataRegistry] * (int) stateAddress;
            }
            else if(this.op == 14)
            {
                System.out.println("Divides a data directly to the content of a register");
                registry[dataRegistry] = registry[dataRegistry] / (int) stateAddress;
            }
            else if(this.op == 15)
            {
                System.out.println("Loads a data/address directly to the content of a register");
                registry[dataRegistry] = (int)stateAddress;
            }
            else if(this.op == 16)
            {
                System.out.println("Sets the D-reg to 1 if first S-reg is less than the B-reg; 0 otherwise");
                if(registry[s2Registry] > registry[s1Registery])
                {
                    registry[dataRegistry] = 0;
                }
                else
                {
                    registry[dataRegistry] = 1;
                }
            }
            else if(this.op == 17)
            {
                System.out.println("Sets the D-reg to 1 if first S-reg is less than a data; 0 otherwise");
                if((int)stateAddress > registry[s1Registery])
                {
                    registry[dataRegistry] = 0;
                }
                else
                {
                    registry[dataRegistry] = 1;
                }
            }
            else if(this.op == 18)
            {
                System.out.println("Logical end of program");
                ProcessControlVariables copy = Main.process.getJob(id - 1);
                copy.setStatus(ProcessControlVariables.STATE.END);
                status = false;

            }
            else if(this.op == 19)
            {
                System.out.println("Does nothing and moves to next instruction");
            }
            else if(this.op == 20)
            {
                System.out.println("Jumps to a specified location");
                programCounter = (int)stateAddress;
                address = Main.memory.getAddress(programCounter,pcb);
                jump = true;
            }
            else if(this.op == 21)
            {
                System.out.println("Branches to an address when content of B-reg = D-reg");
                if(registry[dataRegistry] == registry[bufferRegistry])
                {
                    programCounter = (int) stateAddress;
                    address = Main.memory.getAddress(programCounter,pcb);
                    jump = true;
                }
            }
            else if(this.op == 22)
            {
                System.out.println("Branches to an address when content of B-reg != D-reg");
                if(registry[bufferRegistry] != registry[dataRegistry])
                {
                    programCounter = (int) stateAddress;
                    address = Main.memory.getAddress(programCounter,pcb);
                    jump = true;
                }
            }
            else if(this.op == 23)
            {
                System.out.println("Branches to an address when content of B-reg = 0");
                if(registry[dataRegistry] == 0)
                {
                    programCounter = (int) stateAddress;
                    address = Main.memory.getAddress(programCounter,pcb);
                    jump = true;
                }
            }
            else if(this.op == 24)
            {
                System.out.println("Branches to an address when content of B-reg != 0");
                if(registry[bufferRegistry] != 0)
                {
                    programCounter = (int) stateAddress;
                    address = Main.memory.getAddress(programCounter,pcb);
                }

            }
            else if(this.op == 25)
            {
                System.out.println("Branches to an address when content of B-reg <> 0");
                if(registry[bufferRegistry] > 0)
                {
                    programCounter = (int) stateAddress;
                    address = Main.memory.getAddress(programCounter,pcb);
                }
            }
            else if(this.op == 26)
            {
                System.out.println("Branches to an address when content of B-reg > 0");
                if(registry[bufferRegistry] < 0)
                {
                    programCounter = (int) stateAddress;
                    address = Main.memory.getAddress(programCounter,pcb);
                }
            }
        }
    }


    private int decode(String instruction)
    {
        System.out.println("Instruction: " + instruction);
        this.instructionType = Short.parseShort(instruction.substring(0,2), 2);
        this.op = Short.parseShort(instruction.substring(2,8), 2);
        if(instructionType == 0)
        {
            s1Registery = Short.parseShort(instruction.substring(8,12), 2);
            s2Registry = Short.parseShort(instruction.substring(12,16), 2);
            dataRegistry  = Short.parseShort(instruction.substring(16,20), 2);
            System.out.println("s1: " + s1Registery + "; s2: " + s2Registry + "; d_reg: " + dataRegistry);
        }
        else if(instructionType == 1)
        {
            bufferRegistry = Short.parseShort(instruction.substring(8,12), 2);
            dataRegistry = Short.parseShort(instruction.substring(12,16), 2);
            stateAddress = Long.parseLong(instruction.substring(16,32), 2);
            System.out.println("bufferRegistry: " + bufferRegistry + "; dataRegistry: " + dataRegistry + "; stateAddress: " + stateAddress);
        }
        else if(instructionType == 2)
        {
            stateAddress = Long.parseLong(instruction.substring(16,32), 2);
            System.out.println("Jumped!");

        }
        else if(instructionType == 3)
        {
            registry1 = Short.parseShort(instruction.substring(8,12), 2);
            registry2 = Short.parseShort(instruction.substring(12,16), 2);
            stateAddress =  Long.parseLong(instruction.substring(16,32), 2);
            System.out.println("registry1: " + registry1 + "; registry2: " + registry2 + "; stateAddress: " + stateAddress);
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
        for(int i = 0; i < 4; i++)
        {
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
        }
        return second;
    }
    public int push_Address(int address)
    {
        return Math.abs(address - procSize*Utility.BIT4);
    }
    public synchronized void run()
    {

        procId = pcb.getProcId();

        programCounter = 0;
        inputBuffer = pcb.getIBufferSize();
        tempBuffer = pcb.getOBufferSize();
        outputBuffer = pcb.getOBufferSize();
        buffer = pcb.getCBuffer();
        procSize = pcb.getProcSize();
        address = Main.memory.getAddress(programCounter, pcb);

        while(status == true)
        {
            System.out.println("");
            System.out.println("Troll Counter: " + trollCounter);
            System.out.println("Executing job: " + procId);
            String getInstructions = fetchInstructions(address);
            System.out.println("Physical: " + address);
            System.out.println("Program Counter = " + programCounter);
            //System.out.println("Fetched Instruction: " + getInstructions);
            execute(pcb.getProcId(), decode(getInstructions));
            if(!jump && status == true)
            {
                programCounter = programCounter +4;
                address = Main.memory.getAddress(programCounter, pcb);
            }
            else
            {
                jump = false;
            }
            trollCounter++;
        }
    }
}