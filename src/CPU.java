import java.util.ArrayList;

public class CPU implements Runnable {

    private Process process;
    private int procMemory;
    private int type;
    private int op;
    private int[] registers;
    private int Registry1;
    private int Registry2;
    private int DataRegistry;
    private int BufferRegistry;
    private int SRegistry1;
    private int SRegistry2;
    private int Location;
    private int procCounter;
    private String[] CPUBuffer;
    private int tempMemory;
    private int tempBuffer;
    private int inputBuffer;
    private int outputBuffer;
    private int programCounter;
    private boolean jump;
    private int multiCPU;
    private int tempBIT = 4;
    private ArrayList<Integer> tempData = new ArrayList<>();

    public boolean[] state = {true,
            false,
            false,
            false,
            false};

    public CPU(int NUM_CPU)
    {
        this.multiCPU = NUM_CPU;
        jump = false;
        registers =  new int[16];
        registers[1]=0;
    }

    public String fetch(){
        String instruction = CPUBuffer[programCounter];
        return instruction;
    }
    public void load(Process process){

        procMemory = process.getProcID();

        this.process = process;
        tempBuffer = this.process.getTempBuffer();
        inputBuffer = this.process.getInBuffer();
        outputBuffer = this.process.getOutBuffer();
        procCounter = this.process.getProcDataSize();
        tempMemory = tempBuffer + inputBuffer + outputBuffer + procCounter;
        CPUBuffer = new String[tempMemory];
        tempData = process.getFilledPages();

        for (int i = 0; i < tempMemory; i++)
        {
            CPUBuffer[i] = retrieveMemoryData(i);
        }
        //System.out.println("[Process]]" + process.getProcID());
        programCounter = this.process.getPC();
        setStatusToReady(false);
        setStatusToEnd(false);
        setDataStatus(true);
        //System.out.println("[Process Initalized]");
        try
        {
            Thread.sleep(Main.delay * 3);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


        //System.out.println("[System Ready]");
    }

    public void run()
    {
        Main.processDataHandlers[getProcMemory()-1].setTimeRun(System.currentTimeMillis());
        setBusy(true);
        System.out.println("------------------------ Process: " + getProcMemory() + " ------------------------");
        System.out.println("Pages used: " + DataHandler.getPageUsed());


        while(programCounter < procCounter)
        {

            String instruction = fetch();
            int opCode = decode(instruction);
            //System.out.println("OPCODE: " + opCode);
            executeInstruction(opCode);
            if(!jump) {
                programCounter++;
            }
            else {
                jump = false;
            }

            try
            {
                Thread.sleep(Main.delay);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        setStatusToReady(true);
        setStatusToEnd(true);
        setStartCPU(true);
        setBusy(false);

        Main.processDataHandlers[getProcMemory()-1].setTimeEndRun(System.currentTimeMillis());

        //System.out.println("[Finished Processing]");
    }

    private String retrieveMemoryData(int loc)
    {
        return Memory.readMemoryData(tempData.get( loc/ Utility.PAGESIZE) * Utility.PAGESIZE + (loc % Utility.PAGESIZE));
    }
    public void stop(Process temp)
    {
        System.out.println("RUN TIME: ");
        System.out.print(Main.processDataHandlers[getProcMemory()-1].timeEndRun-Main.processDataHandlers[getProcMemory()-1].timeRun);
        System.out.println();
        System.out.println("IO: " + Main.processDataHandlers[getProcMemory()-1].InputOutput);
        //System.out.println("[REMOVE PROCESS]");
        if(programCounter < procCounter)
        {
            temp.setStatus(Process.STATE.WAIT);
        }
        else
        {
            temp.setStatus(Process.STATE.PROCEND);
        }
        ready();
        try
        {
            Thread.sleep(Main.delay * 2);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        temp.setTmpMemory(CPUBuffer);
        temp.setProcDataSize(procCounter);
        temp.setReg(registers);
        temp.setPC(programCounter);
        temp.setTmpMemorySize(tempMemory);
        temp.setTempBuffer(tempBuffer);
        temp.setInBuffer(inputBuffer);
        temp.setOutBuffer(outputBuffer);

        //System.out.println("[Removed Process]");

    }
    public int decode(String instr)
    {
        String toBinary = Utility.HexToBin(instr.substring(2));
        type =Integer.parseInt(toBinary.substring(0,2));
        op = Utility.binToDecimal(toBinary.substring(2, 8));

        if(type == 00)
        {
            SRegistry1 = Utility.binToDecimal(toBinary.substring(8,12));
            SRegistry2 = Utility.binToDecimal(toBinary.substring(12, 16));
            DataRegistry = Utility.binToDecimal(toBinary.substring(16, 20));
        }
        else if (type == 01)
        {
            BufferRegistry = Utility.binToDecimal(toBinary.substring(8, 12));
            DataRegistry = Utility.binToDecimal(toBinary.substring(12, 16));
            Location = Utility.binToDecimal(toBinary.substring(16));
        }
        else if (type == 10)
        {
            Location = Utility.binToDecimal(toBinary.substring(8));
        }
        else if (type == 11)
        {
            Registry1 = Utility.binToDecimal(toBinary.substring(8, 12));
            Registry2 = Utility.binToDecimal(toBinary.substring(12, 16));
            Location = Utility.binToDecimal(toBinary.substring(16));
        }
        else
        {

        }
        return op;
    }

    public void executeInstruction(int op)
    {
        int opcode=op;

        if(opcode == 0)
        {
            if(Registry2 > 0)
            {
                registers[Registry1] = Utility.hexToDex(CPUBuffer[registers[Registry2]/tempBIT].substring(2));
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
            else
            {
                registers[Registry1] = Utility.hexToDex( CPUBuffer[Location /tempBIT].substring(2));
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }

            Main.processDataHandlers[getProcMemory()-1].InputOutput++;

        }
        else if (opcode == 1)
        {
            if(Registry2 > 0)
            {
                registers[Registry2] =registers[Registry1];
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
            else
            {
                CPUBuffer[Location /tempBIT] = Utility.header + Utility.DecToHex(registers[Registry1]);
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }

            Main.processDataHandlers[getProcMemory()-1].InputOutput++;

        }
        else if (opcode == 2)
        {
            CPUBuffer[registers[DataRegistry]/tempBIT] = Utility.header + Utility.DecToHex( registers[BufferRegistry]);
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 3)
        {
            registers[DataRegistry]= Utility.hexToDex(CPUBuffer[(registers[BufferRegistry]/4) + Location].substring(2));
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 4)
        {
            registers[DataRegistry]=registers[BufferRegistry];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 5)
        {
            registers[DataRegistry]=registers[SRegistry1];
            registers[DataRegistry]+=registers[SRegistry2];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 6)
        {
            registers[DataRegistry]=registers[SRegistry1];
            registers[DataRegistry]=registers[DataRegistry]-registers[SRegistry2];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 7)
        {
            registers[DataRegistry]=registers[SRegistry1]*registers[SRegistry2];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 8)
        {
            registers[DataRegistry]=registers[SRegistry1]/registers[SRegistry2];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 9)
        {
            registers[DataRegistry]= registers[SRegistry1]&registers[SRegistry2];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 10)
        {
            registers[DataRegistry]=registers[SRegistry1]^registers[SRegistry2];
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 11)
        {
            registers[DataRegistry]= Location;
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 12)
        {
            registers[DataRegistry] = registers[DataRegistry] + Location;
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 13)
        {
            registers[DataRegistry] = registers[DataRegistry] * Location;
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 14)
        {
            registers[DataRegistry] = registers[DataRegistry] / Location;
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 15)
        {
            registers[DataRegistry] = (Location);
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 16)
        {
            if(registers[SRegistry1] < registers[SRegistry2]){
                registers[DataRegistry] = 1;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
            else{
                registers[DataRegistry] = 0;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }

        }
        else if (opcode == 17)
        {
            if(registers[SRegistry1] < (Location /tempBIT)){
                registers[DataRegistry] = 1;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
            else{
                registers[DataRegistry] = 0;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }

        }
        else if (opcode == 18)
        {
            programCounter = procCounter;
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 19)
        {
            System.out.println("Skipped OPCode!");
        }
        else if (opcode == 20)
        {
            programCounter = Location/tempBIT;
            jump = true;
            //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
        }
        else if (opcode == 21)
        {
            if(registers[BufferRegistry] == registers[DataRegistry]){
                programCounter = Location/tempBIT;
                jump = true;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
        }
        else if (opcode == 22)
        {
            if(registers[BufferRegistry] != registers[DataRegistry])
            {
                programCounter = Location/tempBIT;
                jump = true;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);

            }
            else{

            }
        }
        else if (opcode == 23)
        {
            if(registers[BufferRegistry] == 0){
                programCounter = Location/tempBIT;
                jump = true;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }

        }
        else if (opcode == 24)
        {
            if(registers[BufferRegistry] != 0){
                programCounter = Location/tempBIT;
                jump = true;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
        }
        else if (opcode == 25)
        {
            if(registers[BufferRegistry] > 0){

                programCounter = Location/tempBIT;
                jump = true;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }

        }
        else if (opcode == 26)
        {
            if (registers[BufferRegistry] < 0) {
                //branch
                programCounter = Location / tempBIT;
                jump = true;
                //System.out.println("Process: " + getProcMemory() + " - CPU: "+ multiCPU + " - OPCODE: " + opcode);
            }
        }
        else
        {
            System.out.println("Opcode does not exist!");
        }

    }
    private void setStatusToReady(boolean state)
    {
        this.state[0] = state;
    }
    private void setStatusToEnd(boolean state)
    {
        this.state[1] = state;
    }
    private void setStartCPU(boolean State)
    {
        state[2] = State;
    }
    public boolean getStartCPU()
    {
        return state[2];
    }
    public boolean getReadyStatus()
    {
        return state[0];
    }
    public boolean getDataStatus()
    {
        return state[3];
    }
    public void setDataStatus(boolean State)
    {
        state[3] = State;
    }
    public boolean cpuDONE()
    {
        return state[1];
    }
    public void setBusy(boolean State)
    {
        state[4] = State;
    }
    public int getProcMemory()
    {
        return procMemory;
    }
    private void ready()
    {
        setStatusToReady(true);
        setStartCPU(false);
        setStatusToEnd(false);
        setDataStatus(false);
    }

}
