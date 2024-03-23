using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cache_Module
{
    internal class RequestObject 
    {
        public string ObjectName { get; set; }
        private List<string> Attributes { get; set; }

        private List<string> Conditions { get; set; }

        public RequestObject(string objectName, List<string> attributes, List<string> conditions)
        {
            this.ObjectName = objectName;
            this.Attributes = attributes;
            this.Conditions = conditions;
        }

        public string getKey()
        {
            return this.ObjectName + ":" + String.Join(",", Attributes) + ";" + String.Join(",", Conditions);
        }
    }
}
