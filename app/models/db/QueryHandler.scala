package models.db

import java.sql.{PreparedStatement, ResultSet, SQLException}
import javax.inject.Singleton

import play.api.db.Database

/**
  * Class to prepare and execute queries, return ResultSets as Streams
  * Created by Andrew on 5/6/17.
  */
@Singleton
class QueryHandler {
  //letting the user add params, as types and order matter based on the query
  @throws(classOf[SQLException])
  def getPreparedStatementForQuery(db: Database, query: String): PreparedStatement = {
    db.getConnection().prepareStatement(query)
  }

  @throws(classOf[SQLException])
  def executeQueryAndGetResultSetAsStream(preparedStatement: PreparedStatement): Stream[ResultSet] = {
    val resultSet: ResultSet = preparedStatement.executeQuery()
    new Iterator[ResultSet] {
      override def hasNext(): Boolean = {
        resultSet.next()
      }
      //not entirely sure why this works. Perhaps this never gets called and toStream takes care of things?
      override def next(): ResultSet = {
        resultSet
      }
    }.toStream
  }


//    Stream[ResultSet] = withStatement { s =>
//      val rs = s executeQuery str
//      new Iterator[ResultSet] { def hasNext = rs.next ;  def next = rs }.toStream
//    }
//  }

  // loan a sql statement
//  def withStatement[R]( f:(Statement) => R ): R =
//    withConn { c => f( c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY) ) }
//
//  // loan a sql connection
//  def withConn[R]( f:(java.sql.Connection) => R ):R = {
//    val conn = DriverManager getConnection "jdbc://..."
//    try f(conn) finally conn.close()
//  }
}
