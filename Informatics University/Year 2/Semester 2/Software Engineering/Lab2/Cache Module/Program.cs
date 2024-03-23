using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cache_Module
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Repository repository = new Repository();
            InMemoryCache cache = new InMemoryCache(2, 3, repository);
            Service service = new Service(cache);

            RequestObject requestObject1 = new RequestObject("Cat", new List<string> { "id", "name", "age"}, new List<string> { "age>5"});
            RequestObject requestObject2 = new RequestObject("Cat", new List<string> { "id", "age"}, new List<string> {"name.length<5"});
            RequestObject requestObject3 = new RequestObject("Dog", new List<string> { "id", "name", "age"}, new List<string> { "age>5"});

            //service.requestData(requestObject1);
            //service.requestData(requestObject2);
            //service.requestData(requestObject3);
            //service.requestData(requestObject1);
            //service.requestData(requestObject2);
            //service.requestData(requestObject3);
            //service.requestData(requestObject2);
            //service.requestData(requestObject3);
            //service.requestData(requestObject2);
            //service.requestData(requestObject1);
            //service.requestData(requestObject2);
            //service.requestData(requestObject1);

            service.requestData(requestObject1);
            service.requestData(requestObject1);
            service.requestData(requestObject1);
            service.requestData(requestObject1);
            service.requestData(requestObject1);
            service.requestData(requestObject2);
            service.requestData(requestObject2);
            service.requestData(requestObject2);
            Console.ReadLine();
        }
    }
}
