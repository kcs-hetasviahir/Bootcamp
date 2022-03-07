using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Enums
{
    enum Months
    {
        Jan=1,
        Feb,
        Mar,
        Apr=20,
        May,
        Jun,
        Jul,
        Aug,
        Sep,
        Oct,
        Nov,
        Dec
    }
         
    

    class Program
    {
        public void DemoExceptional()
        {
            try
            {
                int a = 100;
                Console.WriteLine("Value of a is:" + a);
            }
            catch(Exception E)
            {
                Console.WriteLine(E.Message);
            }
            finally
            {
                Console.WriteLine("Finally");
            }
        }

        static void Main(string[] args)
        {
            Console.WriteLine(Convert.ToInt32(Months.May)+11);
            
            Console.ReadKey();
        }
    }
}
