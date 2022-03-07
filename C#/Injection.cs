using System;

namespace DependencyInjectionDemo
{
    #region Construtor Injection.
    
    public interface I1
    {
        void Demo();
    }
    
    // Child1 Class.
    class Child1:I1
    {
        public void Demo()
        {
            Console.WriteLine("Demo Function Called of Child1 Class - Constructor Injection.");
        }
    }
    
 
    class Child2 : I1
    {
        public void Demo()
        {
            Console.WriteLine("Demo Function Called of Child2 Class - Constructor Injection.");
        }
    }


    public class ConstructorInjection
    {
        private I1 _i1;

        public ConstructorInjection(I1 _i1)
        {
            this._i1 = _i1;
        }
        public void Demo()
        {
            _i1.Demo();
        }
    }
    #endregion

    #region Property Injection.

    public interface I2
    {
        void DemoPropertyInjection(string message);
    }

    class LogWriterClass : I2
    {
        public void DemoPropertyInjection(string message)
        {
            Console.WriteLine("DemoPropertyInjection Method Called of LogWriterClass - Property Injection.");
        }
    }
    class LogWriterClass2 : I2
    {
        public void DemoPropertyInjection(string message)
        {
            Console.WriteLine("DemoPropertyInjection Method Called of LogWriterClass2 - Property Injection.");
        }
    }
    class PropertyInjectionClass
    {
        I2 _i2 = null;
        public void DemoPropertyInjectionFunctionOfClass(I2 _i2, string messages)
        {
            this._i2 = _i2;
            _i2.DemoPropertyInjection(messages);
        }
    }
    
    #endregion

    #region Method Injection.


    public interface I3
    {
        void Demo3();
    }

    public class ServiceClass : I3
    {
        public void Demo3()
        {
            Console.WriteLine("Demo3 Method Overriding - Service Class.");
        }
    }

    public class ClientClass
    {
        private I3 _i3;
        public void ClientClassMethod(I3 _i3)
        {
            this._i3 = _i3;
            Console.WriteLine("Client Class Method Statement Called.");
            this._i3.Demo3();
        }
    }

    
    
    #endregion

    class Program
    {
        static void Main(string[] args)
        {
            #region Construtor Injection.
           
            ConstructorInjection cs = null;

            cs = new ConstructorInjection(new Child1());
 
            cs.Demo();

            
            cs = new ConstructorInjection(new Child2());
        
            cs.Demo();
            #endregion

            #region Property Injection.
            
            PropertyInjectionClass PIC = new PropertyInjectionClass();
            
            
            PIC.DemoPropertyInjectionFunctionOfClass(new LogWriterClass(), "Message - Property Value Passed.");
            PIC.DemoPropertyInjectionFunctionOfClass(new LogWriterClass2(), "Message - Property Value Passed.");
            #endregion

            #region Method Injection.
   
            ClientClass CCLS = new ClientClass();
           
            CCLS.ClientClassMethod(new ServiceClass());
            #endregion

            Console.ReadKey();
        }
    }
}
