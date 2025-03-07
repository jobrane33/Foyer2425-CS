using System;
using System.Data;
using System.Data.SqlClient;
using System.Collections.Generic;
using System.Linq;
using Nest;
using Dapper;

class Program
{
    static void Main()
    {
        var tabelsList = new List<string> {
            "Employees",
            "Transactions",
            "Agence",
            "Clients"
        };
        string sqlConnectionString = "Data Source=DESKTOP-MOT8LB0;Initial Catalog=finance;Integrated Security=True;Encrypt=False";
        foreach (var tableName in tabelsList)
        {

            var esClient = new ElasticClient(new ConnectionSettings(new Uri("http://localhost:9200"))
                                .DefaultIndex(tableName.ToLower()));

            using (var connection = new SqlConnection(sqlConnectionString))
            {
                connection.Open();

                var columns = GetTableColumns(connection, tableName);
                if (columns.Count == 0)
                {
                    Console.WriteLine("No columns found!");
                    return;
                }

                string sqlQuery = $"SELECT * FROM {tableName}";
                var rows = connection.Query(sqlQuery).ToList();

                Console.WriteLine($"Importing {rows.Count} records from {tableName} to Elasticsearch...");

                foreach (var row in rows)
                {
                    var doc = new Dictionary<string, object>();
                    foreach (var col in columns)
                    {
                        doc[col] = ((IDictionary<string, object>)row).ContainsKey(col) ? ((IDictionary<string, object>)row)[col] : null;
                    }

                    var response = esClient.IndexDocument(doc);     
                    if (!response.IsValid)
                    {
                        Console.WriteLine($"Error: {response.OriginalException?.Message}");
                    }
                }
            }

            Console.WriteLine("Import Completed!");
        }
    }


    static List<string> GetTableColumns(SqlConnection connection, string tableName)
    {
        string query = $"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '{tableName}'";
        return connection.Query<string>(query).ToList();
    }
}
