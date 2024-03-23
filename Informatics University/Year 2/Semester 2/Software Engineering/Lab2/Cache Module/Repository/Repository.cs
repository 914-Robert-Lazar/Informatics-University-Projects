using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cache_Module.Entities;

namespace Cache_Module
{
    internal class Repository
    {
        public Repository() { }

        public object getDataFromRequest(RequestObject request)
        {
            if (request.getKey() == "Cat:id,name,age;age>5")
            {
                List<Cat> cats = new List<Cat>
                {
                    new Cat(1, "Cirmi", 6),
                    new Cat(5, "Mici", 8),
                    new Cat(6, "Pici", 6)
                };

                return cats;
            }
            else if (request.getKey() == "Cat:id,age;name.length<5")
            {
                List<Cat> cats = new List<Cat>
                {
                    new Cat(5, null, 8),
                    new Cat(6, null, 6)
                };

                return cats;
            }
            else if (request.getKey() == "Dog:id,name,age;age>5")
            {
                List<Dog> cats = new List<Dog>
                {
                    new Dog(2, "Bodri", 8),
                    new Dog(6, "Vakkancs", 6)
                };

                return cats;
            }

            return null;
        }
    }
}
