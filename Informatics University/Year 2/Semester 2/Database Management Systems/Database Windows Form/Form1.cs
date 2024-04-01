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

namespace Database_Windows_Form
{
    public partial class Form1 : Form
    {
        SqlConnection conn = null;
        SqlDataAdapter adapterForWorkoutDay = null;
        SqlDataAdapter adapterForCalendarDay = null;
        DataSet dataSet;
        BindingSource bindingSourceForWorkoutDay;
        BindingSource bindingSourceForCalendarDay;

        SqlCommandBuilder commandBuilder;

        string queryWorkoutDay;
        string queryCalendarDay;
        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            conn = new SqlConnection(GetConnectionString());

            queryWorkoutDay = ConfigurationManager.AppSettings["query1"];
            queryCalendarDay = ConfigurationManager.AppSettings["query2"];

            adapterForWorkoutDay = new SqlDataAdapter(queryWorkoutDay, conn);
            adapterForCalendarDay = new SqlDataAdapter(queryCalendarDay, conn);
            dataSet = new DataSet();
            adapterForWorkoutDay.Fill(dataSet, ConfigurationManager.AppSettings["table1"]);
            adapterForCalendarDay.Fill(dataSet, ConfigurationManager.AppSettings["table2"]);

            commandBuilder = new SqlCommandBuilder(adapterForCalendarDay);

            dataSet.Relations.Add(ConfigurationManager.AppSettings["connectedTable"], 
                dataSet.Tables[ConfigurationManager.AppSettings["table1"]].Columns[ConfigurationManager.AppSettings["columnToConnect"]], 
                dataSet.Tables[ConfigurationManager.AppSettings["table2"]].Columns[ConfigurationManager.AppSettings["columnToConnect"]]);


            bindingSourceForWorkoutDay = new BindingSource
            {
                DataSource = dataSet.Tables[ConfigurationManager.AppSettings["table1"]]
            };

            bindingSourceForCalendarDay = new BindingSource(bindingSourceForWorkoutDay, ConfigurationManager.AppSettings["connectedTable"]);

            this.dataGridView1.DataSource = bindingSourceForWorkoutDay;
            this.dataGridView2.DataSource = bindingSourceForCalendarDay;

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
                adapterForCalendarDay.Update(dataSet, ConfigurationManager.AppSettings["table2"]);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}
