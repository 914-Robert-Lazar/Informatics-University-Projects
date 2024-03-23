using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cache_Module.Entities
{
    internal class Cat
    {
        private int? Id { get; set; }
        private string Name { get; set; }

        private int? Age { get; set; }

        public Cat(int? id, string name, int? age) 
        {
            this.Id = id;
            this.Name = name;
            this.Age = age;
        }

        public override string ToString()
        {
            string catString = "";
            if (Id != null) 
            {
                catString += "Id: " + this.Id + ", ";
            }
            if (Name != null)
            {
                catString += "Name: " + this.Name + ", ";
            }

            if (Age != null)
            {
                catString += "Age: " + this.Age + ", ";
            }
            return catString.Remove(catString.Length - 2);
        }
    }
}
