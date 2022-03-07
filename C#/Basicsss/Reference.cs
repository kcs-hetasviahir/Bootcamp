using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Ref_Out
{
    class Program
    {
        static void Example1(ref int value) //called method
        {
            value = 1;
        }
        static void Example2(out int value) //called method
        {
            value = 2; //must be initialized 
        }


        // Parameter int value
        static void Example3(int value) //called method
        {
            Console.WriteLine(value);
            value = 3;
            Console.WriteLine(value);
        }
        

        static void Main(string[] args)
        {
                int val1=10; //must be initialized 
                int val2=20; //optional

                Program.Example1(ref val1);
                Console.WriteLine(val1); // val1=1

                Program.Example2(out val2);
                Console.WriteLine(val2); // val2=2

                int a = 30;
                //Argument int a
                Example3(a);
                Console.WriteLine(a); // val2=2

                var v = 10;
                v = 20;
                Console.WriteLine(v);   

                var v2 = "Jeel";
                v2 = "Saumil";
                Console.WriteLine(v2);

                Object O1 = 10;
                O1 = "Jeel";
                O1 = true;
                O1 = 20.20f;
                O1 = 'A';

            Console.ReadKey();
        }
    }
}
