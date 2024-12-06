package org.redfx.qcsamples.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.redfx.strange.Block;
import org.redfx.strange.Complex;
import org.redfx.strange.ControlledBlockGate;
import org.redfx.strange.Gate;
import org.redfx.strange.Program;
import org.redfx.strange.QuantumExecutionEnvironment;
import org.redfx.strange.Qubit;
import org.redfx.strange.Result;
import org.redfx.strange.Step;
import org.redfx.strange.gate.Add;
import org.redfx.strange.gate.AddInteger;
import org.redfx.strange.gate.AddModulus;
import org.redfx.strange.gate.Cnot;
import org.redfx.strange.gate.Hadamard;
import org.redfx.strange.gate.InvFourier;
import org.redfx.strange.gate.MulModulus;
import org.redfx.strange.gate.Swap;
import org.redfx.strange.gate.X;
import org.redfx.strange.local.Computations;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;
import org.redfx.strangefx.render.Renderer;

public class Addition {

    public static void main (String[] args) {
        main2(args);
//        Main st = new Main();
//        st.findPeriod7_15();
  //      st.simple1();
    }
    
    public void simple1() {
        Program p = new Program(2);
        Step step1 = new Step(new X(1));
        Step pr0 = new Step(Step.Type.PROBABILITY);
        Step step2 = new Step(new Hadamard(0));
        p.addSteps(step1, pr0, step2);
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        
        Renderer.renderProgram(p);  
    }
    
    public void mul() {
        int n = 2;
        Program p = new Program(n*2 + 3);
        int mul = 1;
        int N = 3;
        Step prep = new Step();
     //   prep.addGates(new X(4), new X(5)); // 3 in high register
        prep.addGates(new X(3)); // 3 in high register
        Step s1 = new Step (new AddModulus(0,2,3,5,N));
        Step s2 = new Step (new Swap(0,3));
        Step s3 = new Step (new Swap(1,4));
        Step s4 = new Step (new AddModulus(0,2,3,5,N).inverse());
        
        p.addStep(prep);
        p.addStep(s1);
        p.addStep(new Step(Step.Type.PSEUDO));
        p.addStep(s2);
        p.addStep(new Step(Step.Type.PROBABILITY));
        p.addStep(s3);
        p.addStep(new Step(Step.Type.PSEUDO));
        p.addStep(s4);
         QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        
        Renderer.renderProgram(p);  
    }
    
    public void mul1() {
        int n = 2;
        Program p = new Program(n*2 + 3);
        int mul = 1;
        int N = 3;
        Step prep = new Step();
     //   prep.addGates(new X(4), new X(5)); // 3 in high register
        prep.addGates(new X(4)); // 3 in high register
        Step s1 = new Step (new AddModulus(0,2,3,5,N));
        Step s2 = new Step (new Swap(0,3));
        Step s3 = new Step (new Swap(1,4));
        Step s4 = new Step (new AddModulus(0,2,3,5,N).inverse());
        
        p.addStep(prep);
        p.addStep(s1);
        p.addStep(new Step(Step.Type.PSEUDO));
        p.addStep(s2);
        p.addStep(new Step(Step.Type.PSEUDO));
        p.addStep(s3);
        p.addStep(new Step(Step.Type.PSEUDO));
        p.addStep(s4);
         QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        
        Renderer.renderProgram(p);  
    }
    
    public void mul2() {
        int n = 2;
        Program p = new Program(n*2 + 3);
        int mul = 1;
        int N = 3;
        Step prep = new Step();
     //   prep.addGates(new X(4), new X(5)); // 3 in high register
        prep.addGates(new X(3)); // 3 in high register
        Step s = new Step(new MulModulus(0,n,mul, N));
        p.addStep(prep);
        p.addStep(new Step(Step.Type.PSEUDO));
        p.addStep(s);
         QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        
        Renderer.renderProgram(p);  
    }
    
    public void expon1() {
        int offset = 2;
        int n = 2;
        int dim = 2 * n + offset + 3;
        int mul = 1;
        int mod = 3;
        Program p = new Program(dim);
        Step prep = new Step();
        for (int i = 0; i <offset; i++) {
            prep.addGate(new X(i));
        }
        prep.addGates( new X(offset + n+1));
        p.addStep(prep);
        
//        MulModulus mulModulus = new MulModulus(0,n,mul, mod);
//        BlockGate bg = new BlockGate(mulModulus.getBlock(), offset);
MulModulus mulModulus = new MulModulus(offset,n+ offset,mul, mod);
        p.addStep(new Step(mulModulus));
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        
        Renderer.renderProgram(p);  
    }
    
    public void expon1cbg() {
        int offset = 2;
        int n = 2;
        int dim = 2 * n + offset + 3;
        int mul = 1;
        int mod = 3;
        Program p = new Program(dim);
        Step prep = new Step();
        for (int i = 0; i <offset; i++) {
            prep.addGate(new X(i));
        }
        prep.addGates( new X(offset + n+1));
        p.addStep(prep);
        
        MulModulus mulModulus = new MulModulus(0,n,mul, mod);
        ControlledBlockGate cbg = new ControlledBlockGate(mulModulus, offset, 0);

        p.addStep(new Step(cbg));
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        
        Renderer.renderProgram(p);  
    }
    
    public void expon() {
         QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();

        int mod = 15;
        int a = 7;
        int length = (int) Math.ceil(Math.log(mod) / Math.log(2));
        int offset = length;
        Program p = new Program(2 * length + 3 + offset);
        Step prep = new Step();
        prep.addGates(new X(0), new X(2));
        Step prepAnc = new Step(new X(length + 1 + offset));
        p.addStep(prep);
        p.addStep(prepAnc);
        for (int i = length - 1; i > length - 1 - offset; i--) {
            int m = 1;
            for (int j = 0; j < 1 << i; j++) {
                m = m * a % mod;
            }
            System.err.println("Create MulModulus, i = "+i+", m = "+m+", a = "+a+", mod = "+mod);
            MulModulus mul = new MulModulus(length, 2 * length, m, mod);
            ControlledBlockGate cbg = new ControlledBlockGate(mul, offset, i);
            p.addStep(new Step(cbg));
        }
        p.addStep(new Step(new InvFourier(offset, 0)));
        System.err.println("Calculate periodicity Using "+qee);
        Result result = qee.runProgram(p);
        Complex[] probs = result.getProbability();
        System.err.println("Length probs = "+probs.length);
        for (int i = 0 ; i <  probs.length; i++) {
            Complex prob = probs[i];
            if (prob.abssqr() > .001 ) {
                System.err.println("PROBold["+i+"], "+i%16+", = "+ prob.abssqr());
            }
        }
        Qubit[] q = result.getQubits();
        int answer = 0;
        double[] amps = new double[16];
        for (int i = 0; i < probs.length; i++) {
            amps[i%16] = amps[i%16] + probs[i].abssqr();
//            answer = answer + q[i].measure()*(1<< i);
        }
        for (int i = 0; i < 16; i ++) {
            System.err.println("p["+i+"] = "+ amps[i]);
        }
        
    }
    
    public void oneone() {
        int x1 = 1;
        int x0 = 0;
        int y0 = 2;
        int y1 = 3;
        int n = x1-x0;
        int dim = 2 * (n+1)+1;
        Program answer = new Program(dim);

        answer.addStep(new Step (new X(0),  new X(2)));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        Add add3 = new Add(x0,x1,y0,y1);
        answer.addStep (new Step(add3.inverse()));
        
        Add add2 = new Add(x0,x1,y0,y1);
        answer.addStep (new Step(add2));
        
        
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(answer);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(answer);  
    }
    
    public void blockinv1() {
        int x1 = 2;
        int x0 = 0;
        int y0 = 3;
        int y1 = 5;
        int n = x1-x0;
        int N = 3;
        int dim = 2 * (n+1)+1;
        Program answer = new Program(7);

        answer.addStep(new Step (new X(0), new X(1), new X(2), new X(4)));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        Add add1 = new Add(x0,x1,y0,y1);
        
     //   answer.addStep (new Step(add3));
        answer.addStep (new Step(add1.inverse().inverse()));
        
        
        Add add2 = new Add(x0,x1,y0,y1);
        
     //   answer.addStep (new Step(add3));
        answer.addStep (new Step(add2.inverse()));
        
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(answer);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(answer);  
    }
    
    
    public void blockinv2() {
        int x1 = 2;
        int x0 = 0;
        int y0 = 3;
        int y1 = 5;
        int n = x1-x0;
        int N = 3;
        int dim = 2 * (n+1)+1;
        Program answer = new Program(7);

        answer.addStep(new Step (new X(0), new X(4)));
//        answer.addStep(new Step (new X(0), new X(1), new X(2), new X(4), new X(6)));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
//        Block block = new Block(1);
//        block.addStep(new Step(new X(0)));
//        ControlledBlockGate cbg2 = new ControlledBlockGate(block, dim-1, x1);
//
//        answer.addStep(new Step(cbg2));
//        answer.addStep(new Step(Step.Type.PSEUDO));
//        

        Add add3 = new Add(x0,x1,y0,y1);
        answer.addStep (new Step(add3.inverse()));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        Block blockinv = new Block(1);
        blockinv.addStep(new Step(new X(0)));
        Gate cbginv = new ControlledBlockGate(blockinv, dim-1, x1).inverse();
        answer.addStep(new Step(cbginv));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(answer);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(answer);  
    }
    
    public void addmod() {
        int x1 = 2;
        int x0 = 0;
        int y0 = 3;
        int y1 = 5;
        int n = x1-x0;
        int N = 3;
        int dim = 2 * (n+1)+1;
        Program answer = new Program(7);

        answer.addStep(new Step (new X(1), new X(4)));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Add add = new Add(x0, x1, y0, y1);
        answer.addStep(new Step(add));
        answer.addStep(new Step(Step.Type.PSEUDO));

        AddInteger min = new AddInteger(x0,x1,N).inverse();
        answer.addStep(new Step(min));
                answer.addStep(new Step(Step.Type.PSEUDO));

        answer.addStep(new Step(new Cnot(x1,dim-1)));
                answer.addStep(new Step(Step.Type.PSEUDO));

        AddInteger addN = new AddInteger(x0,x1,N);
        ControlledBlockGate cbg = new ControlledBlockGate(addN, x0,dim-1);
        answer.addStep(new Step(cbg));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Add add2 = new Add(x0,x1,y0,y1).inverse();
        answer.addStep(new Step(add2));
                answer.addStep(new Step(Step.Type.PSEUDO));

        answer.addStep(new Step(new X(dim-1)));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Block block = new Block(1);
        block.addStep(new Step(new X(0)));
        ControlledBlockGate cbg2 = new ControlledBlockGate(block, dim-1, x1);

        answer.addStep(new Step(cbg2));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Add add3 = new Add(x0,x1,y0,y1);
        answer.addStep (new Step(add3));
        answer.addStep(new Step(Step.Type.PSEUDO));

        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(answer);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(answer);
    }
    
    public void addmodinvaddmod() {
        int x1 = 2;
        int x0 = 0;
        int y0 = 3;
        int y1 = 5;
        int n = x1-x0;
        int N = 3;
        int dim = 2 * (n+1)+1;
        Program answer = new Program(7);

        answer.addStep(new Step (new X(1), new X(4)));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Add add = new Add(x0, x1, y0, y1);
        answer.addStep(new Step(add));
        answer.addStep(new Step(Step.Type.PSEUDO));

        AddInteger min = new AddInteger(x0,x1,N).inverse();
        answer.addStep(new Step(min));
                answer.addStep(new Step(Step.Type.PSEUDO));

        answer.addStep(new Step(new Cnot(x1,dim-1)));
                answer.addStep(new Step(Step.Type.PSEUDO));

        AddInteger addN = new AddInteger(x0,x1,N);
        ControlledBlockGate cbg = new ControlledBlockGate(addN, x0,dim-1);
        answer.addStep(new Step(cbg));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Add add2 = new Add(x0,x1,y0,y1).inverse();
        answer.addStep(new Step(add2));
        answer.addStep(new Step(Step.Type.PSEUDO));

        answer.addStep(new Step(new X(dim-1)));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Block block = new Block(1);
        block.addStep(new Step(new X(0)));
        ControlledBlockGate cbg2 = new ControlledBlockGate(block, dim-1, x1);

        answer.addStep(new Step(cbg2));
        answer.addStep(new Step(Step.Type.PSEUDO));

        Add add3 = new Add(x0,x1,y0,y1);
        answer.addStep (new Step(add3));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        // and now inverse
        
        Add add3i = new Add(x0,x1,y0,y1).inverse();
        answer.addStep (new Step(add3i));
        answer.addStep(new Step(Step.Type.PSEUDO));

        
        Block blocki = new Block(1);
        blocki.addStep(new Step(new X(0)));
        Gate cbg2i = new ControlledBlockGate(blocki, dim-1, x1).inverse();

        answer.addStep(new Step(cbg2i));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        answer.addStep(new Step(new X(dim-1)));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        
        Add add2i = new Add(x0,x1,y0,y1).inverse().inverse();
        answer.addStep(new Step(add2i));
        answer.addStep(new Step(Step.Type.PSEUDO));

        AddInteger addNi = new AddInteger(x0,x1,N);
        Gate cbgi = new ControlledBlockGate(addN, x0,dim-1).inverse();
        answer.addStep(new Step(cbgi));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        Gate cnoti = new Cnot(x1,dim-1);
        answer.addStep(new Step(cnoti));
        answer.addStep(new Step(Step.Type.PSEUDO));

        
        AddInteger mini = new AddInteger(x0,x1,N).inverse().inverse();
        answer.addStep(new Step(mini));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        
        Add addi = new Add(x0, x1, y0, y1).inverse();
        answer.addStep(new Step(addi));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(answer);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(answer);
  
    }
    public void invaddmod() {
        int x1 = 2;
        int x0 = 0;
        int y0 = 3;
        int y1 = 5;
        int n = x1-x0;
        int N = 3;
        int dim = 2 * (n+1)+1;
        Program answer = new Program(7);

        answer.addStep(new Step (new X(0), new X(4)));
        answer.addStep(new Step(Step.Type.PSEUDO));

        // and now inverse
        
        Add add3i = new Add(x0,x1,y0,y1).inverse();
        answer.addStep (new Step(add3i));
        answer.addStep(new Step(Step.Type.PSEUDO));

        
        Block blocki = new Block(1);
        blocki.addStep(new Step(new X(0)));
        Gate cbg2i = new ControlledBlockGate(blocki, dim-1, x1).inverse();

        answer.addStep(new Step(cbg2i));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        answer.addStep(new Step(new X(dim-1)));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        
        Add add2i = new Add(x0,x1,y0,y1).inverse().inverse();
        answer.addStep(new Step(add2i));
        answer.addStep(new Step(Step.Type.PSEUDO));

        AddInteger addNi = new AddInteger(x0,x1,N);
        Gate cbgi = new ControlledBlockGate(addNi, x0,dim-1).inverse();
        answer.addStep(new Step(cbgi));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        Gate cnoti = new Cnot(x1,dim-1);
        answer.addStep(new Step(cnoti));
        answer.addStep(new Step(Step.Type.PSEUDO));

        
        AddInteger mini = new AddInteger(x0,x1,N).inverse().inverse();
        answer.addStep(new Step(mini));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        
        Add addi = new Add(x0, x1, y0, y1).inverse();
        answer.addStep(new Step(addi));
        answer.addStep(new Step(Step.Type.PSEUDO));
        
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(answer);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(answer);
  
    }
    
    public void findPeriod7_15() { // 5 x 3 mod 6 = 3
        Program p = new Program(9);
        int mul = 2;
        int n = 5;
        Step prep = new Step();
        prep.addGates(new X(4), new X(5)); // 3 in high register
        Step s = new Step(new MulModulus(0,3,mul, n));
        p.addStep(prep);
        List<Step> modSteps = createBlock(0,3,mul,n);
        for (Step step : modSteps) {
            p.addStep(step);
          //  p.addStep(new Step(new ProbabilitiesGate(0)));
            p.addStep(new Step(Step.Type.PROBABILITY));
        //   p.addStep(new Step(Step.Type.PSEUDO));
        }
//        p.addStep(s);
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(p);
        Qubit[] q = result.getQubits();
        Renderer.renderProgram(p);
        for (int i = 0; i < 4; i++) {
            if (q[0].measure() != 0) {
                System.err.println("NOT NULL!! " + i);
            }
        }
    }
    
 public List<Step> createBlock(int y0, int y1, int mul, int mod) {
        List<Step> answer = new ArrayList<>();
        int x0 = y0;
        int x1 = y1-y0;
        int size = 1 + x1-x0;
//        Block answer = new Block("MulModulus", 2 * size+1);
        for (int i = 0; i < mul; i++) {
            AddModulus add = new AddModulus(x0, x1, x1+1, x1 + size, mod);
            answer.add(new Step(add));
        }

        for (int i = x0; i < x1+1; i++) {
            answer.add(new Step (new Swap(i, i + size)));
        }

        int invsteps = Computations.getInverseModulus(mul,mod);
        for (int i = 0; i < invsteps; i++) {
            AddModulus add = new AddModulus(x0, x1, x1+1, x1 + size, mod).inverse();
            answer.add(new Step(add));
        }

        return answer;
    }
  
 
    public static void expmul2p2mod3gen() { // 3^4 = 81 -> mod 7 = 4
        Qubit[] q = expmod(2, 3, 2, 2);
        for (int i = 0; i < q.length; i++) {
            System.err.println("m[" + i + "]: " + q[i].measure());
        }
    }
    
    public static void expmul2p3mod7gen() { // 3^4 = 81 -> mod 7 = 4
        Qubit[] q = expmodNum(2, 7, 3);
        for (int i = 0; i < q.length; i++) {
            System.err.println("m[" + i + "]: " + q[i].measure());
        }
    }
    
    public static void expmul7p4mod15gen() { // 3^4 = 81 -> mod 7 = 4
        Qubit[] q = expmodNum(7, 15, 4);
        for (int i = 0; i < q.length; i++) {
            System.err.println("m[" + i + "]: " + q[i].measure());
        }
    }
    
    private static Qubit[] expmod(int a, int mod, int length, int n0) {
        Program p = new Program(n0 + 2 * length+3);
        Step prep = new Step(new X(0)); // exp = 1
   //     Step prep = new Step(new X(1)); // exp = 2
   //     Step prep = new Step(new X(0), new X(1)); // exp = 3
   //     Step prep = new Step(new X(2)); // exp = 4
        Step prepAnc = new Step(new X(n0+  length+1)); 
        p.addStep(prep);
        p.addStep(prepAnc);
        for (int i = length - 1; i > -1; i--) {
            int m = 1;
            for (int j = 0; j < 1 << i; j++) {
                m = m*a %mod;
            }
            System.err.println("M = "+m);
            MulModulus mul = new MulModulus(0, length, m, mod);
            ControlledBlockGate cbg = new ControlledBlockGate(mul, n0, i);
            p.addStep(new Step(cbg));
        }
         SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
        Result result = sqee.runProgram(p);
         Renderer.renderProgram(p);
        Qubit[] q = result.getQubits();
        return q;
    }
        
    private static Qubit[] expmodNum(int a, int mod, int length) {
        Program p = new Program(2 * length+3);
        Step prepAnc = new Step(new X(length+1)); 
        p.addStep(prepAnc);
        for (int i = length - 1; i > -1; i--) {
            int m = 1;
            for (int j = 0; j < 1 << i; j++) {
                m = m*a %mod;
            }
            System.err.println("i = "+i+", M = "+m);
            MulModulus mul = new MulModulus(0, length, m, mod);
            p.addStep(new Step(mul));
        }
         SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
        Result result = sqee.runProgram(p);
         Renderer.renderProgram(p);
        Qubit[] q = result.getQubits();
        return q;
    }

    public static void main1(String[] args) {
        Program p2 = new Program(2, new Step(new X(0)), new Step(new Hadamard(0), new X(1)));
        SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
        Result res = sqee.runProgram(p2);
        Qubit[] qubits = res.getQubits();
        Renderer.renderProgram(p2);
        Arrays.asList(qubits).forEach(q -> System.out.println("qubit with probability on 1 = "+q.getProbability()+", measured it gives "+ q.measure()));
    }

    public static void main2(String[] args) {
        AddInteger ai = new AddInteger(0,1, 1);
        Program p2 = new Program(3,new Step(new X(0)), new Step(ai));
        SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
        Result res = sqee.runProgram(p2);
        Qubit[] qubits = res.getQubits();
        Renderer.renderProgram(p2);
        Arrays.asList(qubits).forEach(q -> System.out.println("qubit with probability on 1 = "+q.getProbability()+", measured it gives "+ q.measure()));
    }
}

