using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cache_Module.Entities;

namespace Cache_Module
{
    internal class Service
    {
        private InMemoryCache Cache;
        public Service(InMemoryCache cache) 
        {
            Cache = cache;
        }

        public async void requestData(RequestObject request)
        {
            Task.Delay(700).Wait();
            Task<Tuple<object, string>> handleTask = Cache.HandleRequest(request);
            Task<string> removeTask = Cache.removeDataAfterExpire(request.getKey());
            Tuple<object, string> handleTuple = await handleTask;
            Console.WriteLine(handleTuple.Item2);

            object data = handleTuple.Item1;
            if (request.ObjectName == "Cat")
            {
                List<Cat> transformedData = (List<Cat>)data;
                foreach (Cat cat in transformedData)
                {
                    Console.WriteLine(cat);
                }
            }
            else if (request.ObjectName == "Dog")
            {
                List<Dog> transformedData = (List<Dog>)data;
                foreach (Dog dog in transformedData)
                {
                    Console.WriteLine(dog);
                }
            }
            Console.WriteLine();
            string removeMessage = await removeTask;
            Console.WriteLine(removeMessage);
        }
    }
}
