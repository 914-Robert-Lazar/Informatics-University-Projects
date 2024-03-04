using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using System.Data.SqlClient;

namespace Lab1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            string DatabaseConnectionString = "Data Source=DESKTOP-3HTGUDE\\SQLEXPRESS; Initial Catalog=Fitness_Guide;" +
                "Integrated Security=true";
            SqlConnection conn = new SqlConnection(DatabaseConnectionString);
            conn.Open();
            string comString = "SELECT * FROM Exercise";
            SqlCommand cmd = new SqlCommand(comString, conn);   

            //using (SqlDataReader reader = cmd.ExecuteReader()) { 
            //    while (reader.Read())
            //        Console.WriteLine("{0}, {1}", reader[0], reader[1]); 
            //}

            SqlDataAdapter adapter = new SqlDataAdapter(comString, conn);
            DataSet  dset = new DataSet();

            adapter.Fill(dset, "MemoryExercise");
            foreach (DataRow row in dset.Tables["MemoryExercise"].Rows) {
                Console.WriteLine("{0}, {1}, {2}, {3}", row["ExerciseID"], row["Name"], row["Type"], row["Orientation"]);
            }
            conn.Close();
            Console.ReadLine();
            return;
        }
    }
}
