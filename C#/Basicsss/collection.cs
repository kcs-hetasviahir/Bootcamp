using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ModelClassCollectionList
{

    class Program
    {
        static void Main(string[] args)
        {
            //Single Model Class Object.
            MemberModelcs MMC = new MemberModelcs();

            MMC.MemberId = 1;
            MMC.MemberFirstName = "Vaibhav";
            MMC.MemberLastName = "Oza";
            MMC.MemberContactNo = "9889988998";
            MMC.MemberEmailId = "vo@gmail.com";
            MMC.MemberGender = 'M';

            Console.WriteLine("Member Id:{0}", MMC.MemberId);
            Console.WriteLine("Member First Name:{0}", MMC.MemberFirstName);
            Console.WriteLine("Member Last Name:{0}", MMC.MemberLastName);
            Console.WriteLine("Member Contact No.:{0}", MMC.MemberContactNo);
            Console.WriteLine("Member Email Id:{0}", MMC.MemberEmailId);
            Console.WriteLine("Member Gender:{0}", MMC.MemberGender);

            //List Of Model Class Object - Multiple Model Class.
            int NoOfMember;
            List<MemberModelcs> MMCList = new List<MemberModelcs>();

            Console.WriteLine("Enter The Number Of Member You Want To Submit: ");
            NoOfMember = Convert.ToInt32(Console.ReadLine());

            for (int i=0;i<NoOfMember;i++)
            {
                MemberModelcs MMCListOBJ = new MemberModelcs();

                Console.WriteLine("Enter Member Id: ");
                MMCListOBJ.MemberId = Convert.ToInt32(Console.ReadLine());

                Console.WriteLine("Enter Member First Name: ");
                MMCListOBJ.MemberFirstName = Console.ReadLine();

                Console.WriteLine("Enter Member Last Name: ");
                MMCListOBJ.MemberLastName = Console.ReadLine();

                Console.WriteLine("Enter Member Contact No.: ");
                MMCListOBJ.MemberContactNo = Console.ReadLine();

                Console.WriteLine("Enter Member Email Id: ");
                MMCListOBJ.MemberEmailId = Console.ReadLine();

                Console.WriteLine("Enter Member Gender: ");
                MMCListOBJ.MemberGender = Convert.ToChar(Console.ReadLine());

                MMCList.Add(MMCListOBJ);
            }

            Console.WriteLine("Member Data Records Are:");
            for (int i = 0; i < MMCList.Count; i++)
            {
                Console.WriteLine("Member No.: {0}",i);

                Console.WriteLine("Member Id:{0}", MMCList[i].MemberId);
                Console.WriteLine("Member First Name:{0}", MMCList[i].MemberFirstName);
                Console.WriteLine("Member Last Name:{0}", MMCList[i].MemberLastName);
                Console.WriteLine("Member Contact No.:{0}", MMCList[i].MemberContactNo);
                Console.WriteLine("Member Email Id:{0}", MMCList[i].MemberEmailId);
                Console.WriteLine("Member Gender:{0}", MMCList[i].MemberGender);
            }
            Console.ReadKey();

        }
    }
}
