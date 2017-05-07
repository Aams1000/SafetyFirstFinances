package models

/**
  * Created by Andrew on 5/6/17.
  */
object ExpenditureQueries
{
  //language=SQL
  val QUERY_EXPENDITURES_FOR_CURRENT_MONTH: String = "SELECT category, SUM(amount) from t_expenditures " +
                                                            "WHERE EXTRACT(MONTH FROM day) = EXTRACT(MONTH FROM CURRENT_DATE) " +
                                                            "AND EXTRACT(YEAR FROM day) = EXTRACT(YEAR FROM CURRENT_DATE) " +
                                                            "GROUP BY category"

  //language=SQL
  val QUERY_EXPENDITURES_FOR_SELECT_MONTH: String = "SELECT category, SUM(amount) from t_expenditures " +
                                                            "WHERE EXTRACT(MONTH FROM day) = ? " +
                                                            "AND EXTRACT(YEAR FROM day) = ? " +
                                                            "GROUP BY category"
}
