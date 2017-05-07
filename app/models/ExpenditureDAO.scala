package models

import java.sql._
import javax.inject.{Inject, Singleton}

import models.db.QueryHandler
import play.api.Logger
import play.api.db.Database

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created during design-drafting mode. Will likely be refactored and reorganized.
  *
  * Store housing all expenditure information. Can query by date, category, etc.
  *
  * Created by Andrew on 2/5/17.
  */

//TODO - initialize on application start
@Singleton
class ExpenditureDAO @Inject()(val db: Database,
                               val queryHandler: QueryHandler)
                              (implicit exec: ExecutionContext){

  private val logger: Logger = Logger(this.getClass.getSimpleName)

  def getExpendituresByCategoryForCurrentMonth(): Future[Seq[ExpenditureCategoryAndDollarAmount]] = Future {
    try {
      val preparedStatement: PreparedStatement = queryHandler.getPreparedStatementForQuery(db, ExpenditureQueries.QUERY_EXPENDITURES_FOR_CURRENT_MONTH)
      val resultSetStream: Stream[ResultSet] = queryHandler.executeQueryAndGetResultSetAsStream(preparedStatement)
      val test: String = ""
      convertResultSetToExpenditureDollarAmountSequence(resultSetStream)
    }
    catch {
      case e: SQLException => {
        Logger.error("Failed to execute query " + ExpenditureQueries.QUERY_EXPENDITURES_FOR_CURRENT_MONTH + " due to SQLException.", e)
        Seq()
      }
      case e: IllegalArgumentException => {
        Logger.error("Failed to convert DB category values to ExpenditureCategoriesEnum.")
        throw e
      }
    }
  }

  def getExpendituresByCategoryForMonth(month: Int, year: Int): Future[Seq[ExpenditureCategoryAndDollarAmount]] = Future {
    try {
      val preparedStatement: PreparedStatement = queryHandler.getPreparedStatementForQuery(db, ExpenditureQueries.QUERY_EXPENDITURES_FOR_SELECT_MONTH)
      preparedStatement.setInt(1, month)
      preparedStatement.setInt(2, year)
      val resultSetStream: Stream[ResultSet] = queryHandler.executeQueryAndGetResultSetAsStream(preparedStatement)
      convertResultSetToExpenditureDollarAmountSequence(resultSetStream)
    }
    catch {
      case e: SQLException => {
        Logger.error("Failed to execute query " + ExpenditureQueries.QUERY_EXPENDITURES_FOR_CURRENT_MONTH + " due to SQLException.", e)
        Seq()
      }
      case e: IllegalArgumentException => {
        Logger.error("Failed to convert DB category values to ExpenditureCategoriesEnum.")
        throw e
      }
    }
  }

  def testConnection(): Unit = {
    val connection: Connection = db.getConnection()
    val dbInfo: String = connection.getClientInfo.toString
    println(dbInfo)

    val resultSet: ResultSet = connection.createStatement().executeQuery("SELECT * FROM t_expenditures LIMIT 1")
    while (resultSet.next()){
      logger.info("test test")
    }
    connection.close()
  }

  def testFutureString(): Future[String] = Future {
    "Hear me roar!"
  }

  private def convertResultSetToExpenditureDollarAmountSequence(resultSetStream: Stream[ResultSet]): Seq[ExpenditureCategoryAndDollarAmount] = {
    resultSetStream.map(categoryAndAmount =>
    {
      ExpenditureCategoryAndDollarAmount(ExpenditureCategories.fromStringThrowsException(categoryAndAmount.getString(1)),
        categoryAndAmount.getDouble(2))
    })
  }
}
