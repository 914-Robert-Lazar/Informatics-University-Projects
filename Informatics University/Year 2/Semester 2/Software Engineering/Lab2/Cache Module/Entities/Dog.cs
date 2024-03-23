using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cache_Module.Entities
{
    internal class Dog
    {
        private int? Id { get; set; }
        private string Name { get; set; }

        private int? Age { get; set; }

        public Dog(int? id, string name, int? age)
        {
            this.Id = id;
            this.Name = name;
            this.Age = age;
        }

        public override string ToString()
        {
            string dogString = "";
            if (Id != null)
            {
                dogString += "Id: " + this.Id + ", ";
            }
            if (Name != null)
            {
                dogString += "Name: " + this.Name + ", ";
            }

            if (Age != null)
            {
                dogString += "Age: " + this.Age + ", ";
            }
            return dogString.Remove(dogString.Length - 2);
        }
    }
}
