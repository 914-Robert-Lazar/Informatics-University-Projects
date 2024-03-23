using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cache_Module.Entities;
using System.Collections.Specialized;

namespace Cache_Module
{
    internal class InMemoryCache
    {
        private int MaxKeySize { get; set; }
        private int ExpirationTimeInSeconds { get; set; }
        private OrderedDictionary cache;
        private Dictionary<string, bool> resetExpiration;
        private Repository repository;

        public InMemoryCache(int maxKeySize, int expirationTimeInSeconds, Repository repository)
        {
            this.MaxKeySize = maxKeySize;
            this.repository = repository;
            this.ExpirationTimeInSeconds = expirationTimeInSeconds;
            cache = new OrderedDictionary();
            resetExpiration = new Dictionary<string, bool>();
        }


        private void StoreNewData(string key, object value)
        {
            if (cache.Count == MaxKeySize)
            {
                cache.RemoveAt(0);
            }

            cache.Add(key, value);
        }

        public async Task<string> removeDataAfterExpire(string key)
        {
            await Task.Delay(ExpirationTimeInSeconds * 1000);
            if (cache.Contains(key))
            {
                if (resetExpiration.ContainsKey(key) && resetExpiration[key] == true) 
                {
                    resetExpiration[key] = false;
                    return "The expiration time has been reset.\n";
                }
                
                
                cache.Remove(key);
                resetExpiration.Remove(key);
                return "Removed cached data after expiration.\n";
            }
            
            return "Data was already removed.\n";
        }

        public async Task<Tuple<object, string>> HandleRequest(RequestObject request)
        {
            if (cache.Contains(request.getKey()))
            {
                resetExpiration[request.getKey()] = true;
                object dataToMove = cache[request.getKey()];
                cache.Remove(request.getKey());
                cache.Add(request.getKey(), dataToMove);
                return new Tuple<object, string>(dataToMove, "Data in cache.");
            }

            object data = repository.getDataFromRequest(request);
            
            StoreNewData(request.getKey(), data);
            if (resetExpiration.ContainsKey(request.getKey()))
            {
                resetExpiration[request.getKey()] = true;
            }
            else
            {
                resetExpiration.Add(request.getKey(), false);
            }
            return new Tuple<object, string>(data, "Cache miss.");
        }
    }
}
