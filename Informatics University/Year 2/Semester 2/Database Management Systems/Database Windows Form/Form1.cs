using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Security.Cryptography.X509Certificates;
using System.Configuration;
using System.Runtime.CompilerServices;

namespace Database_Windows_Form
{
    public partial class Form1 : Form
    {
        SqlConnection conn = null;
        SqlDataAdapter adapterForTable1 = null;
        SqlDataAdapter adapterForTable2 = null;
        DataSet dataSet;
        BindingSource bindingSourceForTable1;
        BindingSource bindingSourceForTable2;
        SqlCommandBuilder commandBuilder;

        string queryTable1;
        string queryTable2;
        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            conn = new SqlConnection(GetConnectionString());

            queryTable1 = ConfigurationManager.AppSettings["query1"];
            queryTable2 = ConfigurationManager.AppSettings["query2"];

            adapterForTable1 = new SqlDataAdapter(queryTable1, conn);
            adapterForTable2 = new SqlDataAdapter(queryTable2, conn);
            dataSet = new DataSet();
            adapterForTable1.Fill(dataSet, ConfigurationManager.AppSettings["table1"]);
            adapterForTable2.Fill(dataSet, ConfigurationManager.AppSettings["table2"]);

            commandBuilder = new SqlCommandBuilder(adapterForTable2);

            dataSet.Relations.Add(ConfigurationManager.AppSettings["connectedTable"], 
                dataSet.Tables[ConfigurationManager.AppSettings["table1"]].Columns[ConfigurationManager.AppSettings["columnToConnect"]], 
                dataSet.Tables[ConfigurationManager.AppSettings["table2"]].Columns[ConfigurationManager.AppSettings["columnToConnect"]]);


            bindingSourceForTable1 = new BindingSource
            {
                DataSource = dataSet.Tables[ConfigurationManager.AppSettings["table1"]]
            };

            bindingSourceForTable2 = new BindingSource(bindingSourceForTable1, ConfigurationManager.AppSettings["connectedTable"]);

            this.dataGridView1.DataSource = bindingSourceForTable1;
            this.dataGridView2.DataSource = bindingSourceForTable2;

            commandBuilder.GetUpdateCommand();
        }

        string GetConnectionString()
        {
            return "Data Source=DESKTOP-3HTGUDE\\SQLEXPRESS; Initial Catalog=Fitness_Guide;" +
                "Integrated Security=true";
        }

        private void UpdateButton_Click(object sender, EventArgs e)
        {
            try
            {
                adapterForTable2.Update(dataSet, ConfigurationManager.AppSettings["table2"]);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}
