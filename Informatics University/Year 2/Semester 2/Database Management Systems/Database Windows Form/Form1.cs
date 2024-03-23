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

            queryWorkoutDay = "SELECT * FROM DayOfTrainingWeek";
            queryCalendarDay = "SELECT * FROM FitnessCalendar";

            adapterForWorkoutDay = new SqlDataAdapter(queryWorkoutDay, conn);
            adapterForCalendarDay = new SqlDataAdapter(queryCalendarDay, conn);
            dataSet = new DataSet();
            adapterForWorkoutDay.Fill(dataSet, "WorkoutDay");
            adapterForCalendarDay.Fill(dataSet, "CalendarDay");

            commandBuilder = new SqlCommandBuilder(adapterForCalendarDay);

            dataSet.Relations.Add("Days", 
                dataSet.Tables["WorkoutDay"].Columns["DayOfWeekID"], 
                dataSet.Tables["CalendarDay"].Columns["DayOfWeekID"]);


            bindingSourceForWorkoutDay = new BindingSource
            {
                DataSource = dataSet.Tables["WorkoutDay"]
            };

            bindingSourceForCalendarDay = new BindingSource(bindingSourceForWorkoutDay, "Days");

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
                adapterForCalendarDay.Update(dataSet, "CalendarDay");

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}
