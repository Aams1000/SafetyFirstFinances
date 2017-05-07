package models.db

import java.io.File
import java.sql.{Connection, DriverManager, SQLException, Statement}

/**
  * File to add contents to database.
  *
  * Hmm, I want to use the play Logger and the play.api.Configuration object, but play *really*
  * doesn't want me to use those. From my reading, play is designed as a web application only,
  * so offline jobs like this don't exist (they could be run via an appropriate request in
  * routes, but that's not how something like this should be initiated.
  *
  * Created by Andrew on 2/13/17.
  */
object DatabaseUpdater {

  val EXIT_SUCCESS: Int = 0
  val EXIT_FAILURE: Int = -1

  val CMD_LINE_ARGUMENT_DB_STRING: String = "-database"
  val CMD_LINE_ARGUMENT_TABLE_NAME: String = "-table"
  val CMD_LINE_ARGUMENT_DATA_DIRECTORY: String = "-dataDirectory"
  val CMD_LINE_ARGUMENT_DB_DRIVER: String = "-driver"

  val DATA_FILE_DELIMITER: String = "\t"
  val DATA_FILE_EXTENSION: String = ".tsv"

//  val logger: Logger = Logger("test")

  var databaseConnectionString: String = "jdbc:postgresql://localhost/finances"
  var databaseDriver: String = "org.postgresql.Driver"
  var tableToUpdate: String = "t_expenditures"
  var dataDirectoryLocation: String = "/Users/Andrew/FinancialData"

  //right now all arguments are coming in the form (flag, value), so this basic parser is sufficient
  //invalid arguments (such as a bad database connection string) will throw exceptions later
  private def parseArgs(args: Array[String]): Unit = {
    args.sliding(2).foreach(flagAndValue => {
      flagAndValue.head match {
        case CMD_LINE_ARGUMENT_DB_STRING => {
          databaseConnectionString = flagAndValue.last
          logCommandLineOverride(CMD_LINE_ARGUMENT_DB_STRING, flagAndValue.last)
        }
        case CMD_LINE_ARGUMENT_TABLE_NAME => {
          tableToUpdate = flagAndValue.last
          logCommandLineOverride(CMD_LINE_ARGUMENT_TABLE_NAME, flagAndValue.last)
        }
        case CMD_LINE_ARGUMENT_DATA_DIRECTORY => {
          dataDirectoryLocation = flagAndValue.last
          logCommandLineOverride(CMD_LINE_ARGUMENT_DATA_DIRECTORY, flagAndValue.last)
        }
        case CMD_LINE_ARGUMENT_DB_DRIVER => {
          databaseDriver = flagAndValue.last
          logCommandLineOverride(CMD_LINE_ARGUMENT_DB_DRIVER, flagAndValue.last)
        }
      }
    })
    println("Finished parsing command line arguments.")
  }

  private def logCommandLineOverride(variableName: String, value: String): Unit = {
    println(variableName + " set via command line arguments as " + value)
  }

  @throws(classOf[Exception])
  private def readDataFilesIntoTable(dataDirectory: String): Unit = {
    processFolder(new File(dataDirectory))
  }

  @throws(classOf[Exception])
  private def processFolder(folder: File): Unit = {
    println("Processing folder " + folder.getPath)
    for (file <- folder.listFiles()){
      if (file.isDirectory){
        processFolder(file)
      }
      else if (file.getName.endsWith(DATA_FILE_EXTENSION)){
        copyFileToTable(file)
      }
      else {
        println("Ignoring file " + file.getPath + " because it does not end with required " + DATA_FILE_EXTENSION)
      }
    }
  }

  @throws(classOf[Exception])
  private def copyFileToTable(file: File): Unit = {
    println("Copying data file " + file.getPath + " to table " + tableToUpdate)
    //load driver
    classOf[org.postgresql.Driver]
    val connection: Connection = DriverManager.getConnection(databaseConnectionString)
    try {
      val ps: Statement = connection.createStatement()
      val queryString: String ="COPY " + tableToUpdate + "(day, charger, category, amount) FROM '" + file.getAbsolutePath + "' WITH DELIMITER '" + DATA_FILE_DELIMITER + "'"
      println(queryString)
      ps.executeUpdate(queryString)
      connection.close()
    }
    catch {
      case e: SQLException => {
        println("Failed to add data from file " + file.getName + "to " + tableToUpdate + " due to SqlException. " +
          "Vacuuming table and failing.")
        e.printStackTrace()
        throw e
      }
      case e: Exception => {
        println("Failed to add data from file " + file.getName + "to " + tableToUpdate + " due to unexpected exception "
          + e.getClass.getSimpleName + ". Vacuuming table and failing.")
        e.printStackTrace()
        throw e
      }
    }
  }

  private def vacuumTable(): Unit = {
    println("Attempting to vacuum table " + tableToUpdate)
    //load driver
    classOf[org.postgresql.Driver]
    val connection: Connection = DriverManager.getConnection(databaseConnectionString)
    try {
      val ps: Statement = connection.createStatement()
      ps.executeUpdate("VACUUM ANALYZE " + tableToUpdate)
      connection.close()
    }
    catch {
      case e: SQLException => {
        println("Failed to vacuum table " + tableToUpdate + ". Exiting without cleaning table.")
        e.printStackTrace()
      }
    }
  }

  def main(args: Array[String]): Unit = {
    parseArgs(args)
    try {
      readDataFilesIntoTable(dataDirectoryLocation)
    }
    catch {
      case e: Exception => {
        println("Unexpected exception " + e.getClass.getSimpleName + " encountered when trying " +
          "to add data to database. Vacuuming " + tableToUpdate + " and failing." )
        vacuumTable()
        System.exit(EXIT_FAILURE)
      }
    }
    println("Successfully added data to table " + tableToUpdate)
    System.exit(EXIT_SUCCESS)
  }
}
