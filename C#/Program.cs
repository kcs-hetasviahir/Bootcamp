using System;


namespace OOPS
{

    class ClassObjectDeepDemo
    {
        public int a;
    }

    struct StructureReferenceDeepDemo {
        public int a;
    }

    
    public class Encapsulation {
        private int pr; 
        internal int i; 
        protected int prot;
        public int pu;
        protected internal int pi;
        private protected int pp;

       
        public Encapsulation() {
            pr = 100;
        }

        public void AccessibleDemoFun() {
            pr = 10; 
            i = 20;
            prot = 30; 
            pi = 40; 
            pp = 50;
        }
    }
    class EncapsulationAndInheritanceDemo:Encapsulation
    {
        
        public void AccessibleDemoFunOfDirectChildClass()
        {
            //pr = 10; 
            i = 20; 
            prot = 30;
            pi = 40;
            pp = 60;
        }
    }

    class EncapsulationAndInheritanceDemoSubChild : EncapsulationAndInheritanceDemo
    {
        public void AccessibleDemoFunOfSubChildClass()
        {
            
            //pr = 10; 
            prot = 20; 
            pi = 30; 
            i = 20; 
            pp = 60;
        }
    }

    
    class Polymorphism_FunctionOverloading
    {
        public void demo()
        {
            Console.WriteLine("1st demo function in Polymorphism_FunctionOverloading class - no return type, no parameter.");
        }
        public void demo(int a)
        {
            Console.WriteLine("2nd demo function in Polymorphism_FunctionOverloading class - no return type, 1 integer parameter");
        }
        public void demo(int a, char b)
        {
            Console.WriteLine("3rd demo function in Polymorphism_FunctionOverloading class - no return type, 1 integer parameter + 1 character parameter.");
        }
    }

    class Base_Cls
    {
        public virtual void DemoFunction()
        {
            Console.WriteLine("Base_Cls Saying Hello!");
        }
        public void DemoFunction2()
        {
            Console.WriteLine("Base_Cls Saying Hello second time!");
        }
    }
    class Derived_Cls : Base_Cls
    {
        public override void DemoFunction()
        {
            base.DemoFunction();
            Console.WriteLine("Derived_Cls Saying Hello!");
        }
        public void DemoFunction2()
        {
            Console.WriteLine("Derived_Cls Saying Hello second time!");
        }

    }
    class Derived_Sub_Cls : Derived_Cls
    {
        public override void DemoFunction()
        {
            base.DemoFunction();
            Console.WriteLine("Derived_Sub_Cls Saying Hello!");
        }
        public void DemoFunction2()
        {
            Console.WriteLine("Derived_Sub_Cls Saying Hello second time!");
        }
    }


    abstract class Parent_AbsCls
    {
        public void DemoParentAbsNormal_Fun()
        {
            Console.WriteLine("Normal Method of Normal Class Named - Parent_AbsCls is called.");
        }
        public abstract void DemoParentAbsAbstract_Fun();
    }

    abstract class Abstract_CLS:Parent_AbsCls
    {
        public abstract void DemoABS_Fun();
        public void DemoNormal_Fun() {
            Console.WriteLine("Normal Method of Abstract Class Named - Abstract_CLS is called.");
        }

        public override void DemoParentAbsAbstract_Fun()
        {
            Console.WriteLine("Abstract Method of Abstract Class Named - Parent_AbsCls is called IN Abstract_CLS.");
        }
    }

    class ChildCLS_AbsDemo : Abstract_CLS
    {
        public override void DemoABS_Fun()
        {
            Console.WriteLine("Abstract Method of Abstract Class Named - Abstract_CLS is called.");
        }
        public void DemoChildNormal_Fun()
        {
            Console.WriteLine("Normal Method of Child Class Named - ChildCLS_AbsDemo is called.");
        }

        public override void DemoParentAbsAbstract_Fun()
        {
            Console.WriteLine("Abstract Method of Abstract Class Named - Parent_AbsCls is called IN ChildCLS_AbsDemo.");
        }
    }

        interface IParent
    {
        void IAbsDemo();
    }
    interface IParent2
    {
        void IAbsDemo();
    }
    class InterfaceDemo_CLS:IParent,IParent2
    {
        void IParent.IAbsDemo()
        {
            Console.WriteLine("Interface IParent Method Override.");
        }
        void IParent2.IAbsDemo()
        {
            Console.WriteLine("Interface IParent2 Method Override.");
        }
    }
    class InterfaceDemo_CLS2 : IParent, IParent2
    {

        void IParent.IAbsDemo()
        {
            Console.WriteLine("Interface IParent Method Override.");
        }
        void IParent2.IAbsDemo()
        {
            Console.WriteLine("Interface IParent2 Method Override.");
        }
    }
    class InterfaceDemo_CLS3 : IParent, IParent2
    {

        void IParent.IAbsDemo()
        {
            Console.WriteLine("Interface IParent Method Override.");
        }
        void IParent2.IAbsDemo()
        {
            Console.WriteLine("Interface IParent2 Method Override.");
        }
    }

    
    static class DemoStaticCLS {
        public static int a;
    }

    class DemoStaticCLSWithNormal {
        public int a1;

       
        public static int b;
    }

    public class NormalSealedCLSDemo
    {
        public int a2=300;
    }

    sealed class DemoSealedCLS: NormalSealedCLSDemo
    {
        public int InheritDemo;
    }

    public partial class DemoParCLS {
        public int p1;
    }

    public partial class DemoParCLS
    {
        public int p2;
    }

    public class ChildCLS:DemoParCLS {
        public int child;
    }
    class Program
    {
        static void Main(string[] args)
        {
            
            ClassObjectDeepDemo ClassObjectDeepDemo1 = new ClassObjectDeepDemo();
            
            
            ClassObjectDeepDemo ClassObjectDeepDemo2 = new ClassObjectDeepDemo();

           
            ClassObjectDeepDemo ClassObjectDeepDemo3 = new ClassObjectDeepDemo();

            
            ClassObjectDeepDemo ClassObjectDeepDemo4 = new ClassObjectDeepDemo();

           
            ClassObjectDeepDemo ClassObjectDeepDemo5 = new ClassObjectDeepDemo();

            
            ClassObjectDeepDemo ClassObjectDeepDemo6 = ClassObjectDeepDemo1;

            
            ClassObjectDeepDemo ClassObjectDeepDemo7 = ClassObjectDeepDemo2;

           
            ClassObjectDeepDemo ClassObjectDeepDemo8 = ClassObjectDeepDemo3;

            ClassObjectDeepDemo ClassObjectDeepDemo9 = ClassObjectDeepDemo4;


            ClassObjectDeepDemo ClassObjectDeepDemo10 = ClassObjectDeepDemo5;

            ClassObjectDeepDemo ClassObjectDeepDemo11 = ClassObjectDeepDemo6;

         
            ClassObjectDeepDemo ClassObjectDeepDemo12 = ClassObjectDeepDemo7;


            ClassObjectDeepDemo1.a = 111;
            ClassObjectDeepDemo2.a = 222;
            ClassObjectDeepDemo3.a = 333;
            ClassObjectDeepDemo4.a = 444;
            ClassObjectDeepDemo5.a = 555;
            ClassObjectDeepDemo6.a = 666;
            ClassObjectDeepDemo7.a = 777;
            ClassObjectDeepDemo8.a = 888;
            ClassObjectDeepDemo9.a = 999;
            ClassObjectDeepDemo10.a = 1010;
            ClassObjectDeepDemo11.a = 1111;
            ClassObjectDeepDemo12.a = 1212;


            Console.WriteLine("Value of ClassObjectDeepDemo1.a:" + ClassObjectDeepDemo1.a);
            Console.WriteLine("Value of ClassObjectDeepDemo2.a:" + ClassObjectDeepDemo2.a);
            Console.WriteLine("Value of ClassObjectDeepDemo3.a:" + ClassObjectDeepDemo3.a);
            Console.WriteLine("Value of ClassObjectDeepDemo4.a:" + ClassObjectDeepDemo4.a);
            Console.WriteLine("Value of ClassObjectDeepDemo5.a:" + ClassObjectDeepDemo5.a);
            Console.WriteLine("Value of ClassObjectDeepDemo6.a:" + ClassObjectDeepDemo6.a);
            Console.WriteLine("Value of ClassObjectDeepDemo7.a:" + ClassObjectDeepDemo7.a);
            Console.WriteLine("Value of ClassObjectDeepDemo8.a:" + ClassObjectDeepDemo8.a);
            Console.WriteLine("Value of ClassObjectDeepDemo9.a:" + ClassObjectDeepDemo9.a);
            Console.WriteLine("Value of ClassObjectDeepDemo10.a:" + ClassObjectDeepDemo10.a);
            Console.WriteLine("Value of ClassObjectDeepDemo11.a:" + ClassObjectDeepDemo11.a);
            Console.WriteLine("Value of ClassObjectDeepDemo12.a:" + ClassObjectDeepDemo12.a);


            StructureReferenceDeepDemo StructureReferenceDeepDemo1 = new StructureReferenceDeepDemo();

            StructureReferenceDeepDemo StructureReferenceDeepDemo2 = new StructureReferenceDeepDemo();

            StructureReferenceDeepDemo StructureReferenceDeepDemo3 = new StructureReferenceDeepDemo();

            StructureReferenceDeepDemo StructureReferenceDeepDemo4 = new StructureReferenceDeepDemo();

            StructureReferenceDeepDemo StructureReferenceDeepDemo5 = new StructureReferenceDeepDemo();

         
            StructureReferenceDeepDemo StructureReferenceDeepDemo6 = StructureReferenceDeepDemo1;

           
            StructureReferenceDeepDemo StructureReferenceDeepDemo7 = StructureReferenceDeepDemo2;

           
            StructureReferenceDeepDemo StructureReferenceDeepDemo8 = StructureReferenceDeepDemo3;

            
            StructureReferenceDeepDemo StructureReferenceDeepDemo9 = StructureReferenceDeepDemo4;

            StructureReferenceDeepDemo StructureReferenceDeepDemo10 = StructureReferenceDeepDemo5;

            StructureReferenceDeepDemo StructureReferenceDeepDemo11 = StructureReferenceDeepDemo6;

            
            StructureReferenceDeepDemo StructureReferenceDeepDemo12 = StructureReferenceDeepDemo7;

            // Set values - struct.
            StructureReferenceDeepDemo1.a = 111;
            StructureReferenceDeepDemo2.a = 222;
            StructureReferenceDeepDemo3.a = 333;
            StructureReferenceDeepDemo4.a = 444;
            StructureReferenceDeepDemo5.a = 555;
            StructureReferenceDeepDemo6.a = 666;
            StructureReferenceDeepDemo7.a = 777;
            StructureReferenceDeepDemo8.a = 888;
            StructureReferenceDeepDemo9.a = 999;
            StructureReferenceDeepDemo10.a = 1010;
            StructureReferenceDeepDemo11.a = 1111;
            StructureReferenceDeepDemo12.a = 1212;

            // Get values - struct.
            Console.WriteLine("Value of StructureReferenceDeepDemo1.a:" + StructureReferenceDeepDemo1.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo2.a:" + StructureReferenceDeepDemo2.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo3.a:" + StructureReferenceDeepDemo3.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo4.a:" + StructureReferenceDeepDemo4.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo5.a:" + StructureReferenceDeepDemo5.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo6.a:" + StructureReferenceDeepDemo6.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo7.a:" + StructureReferenceDeepDemo7.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo8.a:" + StructureReferenceDeepDemo8.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo9.a:" + StructureReferenceDeepDemo9.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo10.a:" + StructureReferenceDeepDemo10.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo11.a:" + StructureReferenceDeepDemo11.a);
            Console.WriteLine("Value of StructureReferenceDeepDemo12.a:" + StructureReferenceDeepDemo12.a);


            //Encapsulation & Ingeritance in Class.
            Encapsulation En = new Encapsulation();
            En.i = 100; 
            En.pi = 101; 
            En.pu = 102;
            
            EncapsulationAndInheritanceDemo EnI = new EncapsulationAndInheritanceDemo();
            EnI.i = 100; 
            EnI.pi = 101; 
            EnI.pu = 102; 

            Console.WriteLine("Intenal Variable Value:" + En.i +"-"+EnI.i);
            Console.WriteLine("Protected Intenal Variable Value:" + En.pi + "-"+EnI.pi);
            Console.WriteLine("Public Variable Value:" + En.pu + "-"+EnI.pu);

           
            EncapsulationAndInheritanceDemoSubChild EnIDSC = new EncapsulationAndInheritanceDemoSubChild();
            EnIDSC.i = 100; 
            EnIDSC.pi = 101; 
            EnIDSC.pu = 102; 

            Console.WriteLine("Intenal Variable Value:" + En.i + "-" + EnI.i +"-" + EnIDSC.i);
            Console.WriteLine("Protected Intenal Variable Value:" + En.pi + "-" + EnI.pi + "-" + EnIDSC.pi);
            Console.WriteLine("Public Variable Value:" + En.pu + "-" + EnI.pu + "-" + EnIDSC.pu);

           
            OOPS_Demo_Inheritance.EncapsulationAndInheritanceDemoInDifferentNameSpace OOPSD_EnI = new OOPS_Demo_Inheritance.EncapsulationAndInheritanceDemoInDifferentNameSpace();
            OOPSD_EnI.i = 100; 
            OOPSD_EnI.pi = 101; 
            OOPSD_EnI.pu = 102;
            Console.WriteLine("Intenal Variable Value:" + En.i + "-" + EnI.i +"-"+ OOPSD_EnI.i);
            Console.WriteLine("Protected Intenal Variable Value:" + En.pi + "-" + EnI.pi + "-" + OOPSD_EnI.pi);
            Console.WriteLine("Public Variable Value:" + En.pu + "-" + EnI.pu + "-" + OOPSD_EnI.pu);


           
            Polymorphism_FunctionOverloading PolyFunOverLoading = new Polymorphism_FunctionOverloading();
            PolyFunOverLoading.demo(); 
            PolyFunOverLoading.demo(1);
            PolyFunOverLoading.demo(1,'A');

           
            Base_Cls B_CLS = new Base_Cls();
            B_CLS.DemoFunction(); 
            B_CLS.DemoFunction2(); 

            Derived_Cls D_CLS = new Derived_Cls();
            D_CLS.DemoFunction(); 
            D_CLS.DemoFunction2(); 

            Derived_Sub_Cls DS_CLS = new Derived_Sub_Cls();
            DS_CLS.DemoFunction(); 
            DS_CLS.DemoFunction2(); 


            
            ChildCLS_AbsDemo CCLS_AbsDemo = new ChildCLS_AbsDemo();
            CCLS_AbsDemo.DemoNormal_Fun();
            CCLS_AbsDemo.DemoABS_Fun();
            CCLS_AbsDemo.DemoChildNormal_Fun();
            CCLS_AbsDemo.DemoParentAbsNormal_Fun();
            CCLS_AbsDemo.DemoParentAbsAbstract_Fun();

            Abstract_CLS AbCLS = new ChildCLS_AbsDemo(); 
            AbCLS.DemoABS_Fun();
            AbCLS.DemoNormal_Fun();
            AbCLS.DemoParentAbsNormal_Fun();
            AbCLS.DemoParentAbsAbstract_Fun();


            IParent ICls = new InterfaceDemo_CLS();
            ICls.IAbsDemo();

            ICls = new InterfaceDemo_CLS2();
            ICls.IAbsDemo();

            ICls = new InterfaceDemo_CLS3();
            ICls.IAbsDemo();


          
            DemoStaticCLS.a = 10;

            
            DemoStaticCLSWithNormal DSWN = new DemoStaticCLSWithNormal();
            DSWN.a1 = 100;
            
           
            DemoStaticCLSWithNormal.b = 200;

            DemoParCLS DPC = new DemoParCLS();
            DPC.p1 = 300;
            DPC.p2 = 400;

            ChildCLS CCLS = new ChildCLS();
            CCLS.p1 = 1000;
            CCLS.p2 = 1000;

            Console.ReadKey();
        }
    }
}


namespace OOPS_Demo_Inheritance
{


    class EncapsulationAndInheritanceDemoInDifferentNameSpace : OOPS.Encapsulation
    {

        public void AccessibleDemoFunOfDirectChildClassInDifferentNameSpace()
        {
            
            prot = 20; 
            pi = 30; 
            i = 20;
            pp = 60;
        }
    }

}

