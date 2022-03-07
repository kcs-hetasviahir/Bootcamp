using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Day1_Array
{
    class Program
    {
        static void Main(string[] args)
        {
            #region Single Dimensional Array
            try
            {
                #region Explicitly Array Declaration & Initializing

                //String Array initialization syntax using the new keyword.
                string[] stringArray = new string[] { "one", "two", "three" };
                Console.WriteLine("\nstringArray elements are:" + " " + stringArray[0] + " " + stringArray[1] + " " + stringArray[2]);
                Console.WriteLine("Total stringArray has {0} elements", stringArray.Length);

                //Boolean Array initialization syntax without using the new keyword.
                bool[] boolArray = { false, false, true };
                Console.WriteLine("\nBoolean Array elements are:" + " " + boolArray[0] + " " + boolArray[1] + " " + boolArray[2]);
                Console.WriteLine("boolArray has {0} elements", boolArray.Length);

                //Int Array initialization with new keyword and size.
                int[] intArray = new int[4] { 20, 22, 23, 0 };
                Console.WriteLine("\nInteger Array elements are:" + " " + intArray[0] + " " + intArray[1] + " " + intArray[2]);
                Console.WriteLine("intArray has {0} elements", intArray.Length);

                #endregion

                #region Implicitly Array Declaration & Initializing
                var Demo = new[] { 1, 2, 3, 4, 5, 6 };
                Console.WriteLine("\nImplicit Array elements are:" + " " + Demo[0] + " " + Demo[1] + " " + Demo[2] + " " + Demo[3] + " " + Demo[4] + " " + Demo[5]);
                Console.WriteLine("Demo has {0} elements", Demo.Length);
                #endregion

                #region Object Array Declaration & Initializing
                Object[] O = new Object[] { true, false, "Abhishek", 42, 12.22 };
                Console.WriteLine("\nObjects Array elements are:" + " " + O[0] + " " + O[1] + " " + O[2] + " " + O[3]);
                Console.WriteLine("Object Array has {0} elements", O.Length);
                Console.WriteLine("Object Array type 1 is {0} ", O[0].GetType());
                Console.WriteLine("Object Array type 2 is {0} ", O[1].GetType());
                Console.WriteLine("Object Array type 3 is {0} ", O[2].GetType());
                Console.WriteLine("Object Array type 4 is {0} ", O[3].GetType());
                #endregion

                Console.ReadKey();
            }
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
            #endregion

            #region Multi Dimensional Array
            try
            {
                Console.WriteLine("Multi Dimensional Array:");
                int[,] myMatrix;
                myMatrix = new int[6, 6];

                // Populate (6 * 6) array.
                for (int i = 0; i < 6; i++)
                    for (int j = 0; j < 6; j++)
                        myMatrix[i, j] = i * j;

                // Print (6 * 6) array.
                for (int i = 0; i < 6; i++)
                {
                    for (int j = 0; j < 6; j++)
                        Console.Write(myMatrix[i, j] + "\t");
                    Console.WriteLine();
                }
                Console.WriteLine();
                Console.ReadKey();
            }
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
            #endregion
            
            #region Jagged Array
            try
            {
                Console.WriteLine("=> Jagged multidimensional array.");
                // A jagged MD array (i.e., an array of arrays).
                // Here we have an array of 5 different arrays. 
                int[][] myJagArray = new int[5][];

                // Create the jagged array.
                for (int i = 0; i < myJagArray.Length; i++)
                            myJagArray[i] = new int[i + 7]; 
 
                // Print each row (remember, each element is defaulted to zero!).
                for(int i = 0; i < 5; i++)
                {
                    for(int j = 0; j < myJagArray[i].Length; j++)
                    Console.Write(myJagArray[i][j] + " ");
                    Console.WriteLine(); 
                } 
                Console.WriteLine();
                Console.ReadKey();
             } 
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
            #endregion

        }
    }

}