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

namespace DBMS_Practical_Model
{
    public partial class Form1 : Form
    {
        SqlConnection conn = null;
        DataSet ds;
        SqlDataAdapter daUsers = null;
        SqlDataAdapter daPosts = null;
        SqlCommandBuilder sqlCommandBuilder = null;
        BindingSource bsUsers, bsPosts;
        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            conn = new SqlConnection(GetConnectionString());

            ds = new DataSet();
            daUsers = new SqlDataAdapter("SELECT * FROM Users", conn);
            daPosts = new SqlDataAdapter("SELECT * FROM Posts", conn);

            sqlCommandBuilder = new SqlCommandBuilder(daPosts);

            daUsers.Fill(ds, "Users");
            daPosts.Fill(ds, "Posts");

            DataRelation dr = new DataRelation("FK_Posts_Users", ds.Tables["Users"].Columns["UserID"], ds.Tables["Posts"].Columns["UserID"]);
            ds.Relations.Add(dr);

            bsUsers = new BindingSource
            {
                DataSource = ds,
                DataMember = "Users"
            };

            bsPosts = new BindingSource
            {
                DataSource = bsUsers,
                DataMember = "FK_Posts_Users"
            };

            dgvUsers.DataSource = bsUsers;
            dgvPosts.DataSource = bsPosts;

            sqlCommandBuilder.GetUpdateCommand();
        }

        private void Update_Click(object sender, EventArgs e)
        {
            try
            {
                daPosts.Update(ds, "Posts");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }

        string GetConnectionString()
        {
            return "Data Source=DESKTOP-3HTGUDE\\SQLEXPRESS; Initial Catalog=DBMS Practical-Model;" +
                "Integrated Security=true";
        }
    }
}
