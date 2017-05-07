package controllers

import java.text.DateFormatSymbols

import models.{ExpenditureCategoryAndDollarAmount, ExpenditureDAO}
import javax.inject._

import models.charts.{Histogram, LabelAndDataValue, TitleAndAxesLabels}
import play.api.Logger
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
  * Controller for expenditure displays (graphs, tables, etc).
  *
  * Created by Andrew 2/27/17.
  *
  */
@Singleton
class ExpenditureDisplayController @Inject() (val expenditureDAO: ExpenditureDAO)
                                             (implicit exec: ExecutionContext) extends Controller {

  private val METHOD_GET: String = "GET"
  private val METHOD_POST: String = "POST"

  private val PARAM_ACTION: String = "Action"

  private val logger: Logger = Logger(this.getClass.getSimpleName)

  //Hmm, I'm not entirely sure I actually need Action.async on each of these functions
  //(it seems rather wrong), but that's what Play seems to want
  def delegateRequest(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] => {
      val action: String = getActionFromRequest(request).getOrElse("")
      action match {
        case _ => {
          for {result <- initialLoad.apply(request)}
            yield {
              result
            }
        }
      }
    }
  }

  def delegateRequestTest(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] => {
      val action: String = getActionFromRequest(request).getOrElse("")
      action match {
        case _ => {
          for {result <- initialLoad.apply(request)}
            yield {
              result
            }
        }
      }
    }
  }

  private def initialLoad(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] => {
//      val paramMap: Map[String, Seq[String]] = getParamMapFromRequest(request)
      for { categoriesAndExpenditureAmounts: Seq[ExpenditureCategoryAndDollarAmount] <- expenditureDAO.getExpendituresByCategoryForMonth(10, 2016) }
        yield {
          val title: String = "Expenditures by category " + new DateFormatSymbols().getMonths().array(9) + ", " + "2016"
          val histogram: Histogram = Histogram(
            TitleAndAxesLabels(title, "Category", "Dollars"),
          categoriesAndExpenditureAmounts.map(categoryAndAmount => LabelAndDataValue(categoryAndAmount.expenditureCategory.name, categoryAndAmount.dollarAmount)))
          Ok(views.html.charts("I am a page title", histogram))
        }
    }
  }

//  private def testResponse(): Action[AnyContent] = Action.async {
//    implicit request: Request[AnyContent] => {
//      val paramMap: Map[String, Seq[String]] = getParamMapFromRequest(request)
//      for { testString : String <- expenditureDAO.testFutureString()}
//        yield {
//          Ok(views.html.charts(testString))
//        }
//    }
//  }

  private def getActionFromRequest(request: Request[AnyContent]): Option[String] = {
    request.method match {
      case METHOD_GET => getActionFromGetRequest(request)
      case METHOD_POST => getActionFromPostRequest(request)
      case _ => {
        logger.error("Unexpected request type '" + request.method + ".' Not parsing request as a security "
        + "precaution.")
        Option.empty
      }
    }
  }

  private def getActionFromGetRequest(request: Request[AnyContent]): Option[String] = {
    request.getQueryString(PARAM_ACTION)
  }

  private def getActionFromPostRequest(request: Request[AnyContent]): Option[String] = {
    request.body.asFormUrlEncoded.getOrElse(Map()).getOrElse(PARAM_ACTION, Seq()).headOption
  }

  private def getParamMapFromRequest(request: Request[AnyContent]): Map[String, Seq[String]] = {
    request.method match {
      case METHOD_GET => getParamMapFromGetRequest(request)
      case METHOD_POST => getParamMapFromPostRequest(request)
      case _ => {
        logger.error("Unexpected request type '" + request.method + ".' Not parsing request as a security "
          + "precaution.")
        Map()
      }
    }
  }

  private def getParamMapFromGetRequest(request: Request[AnyContent]): Map[String, Seq[String]] = {
   request.queryString
  }

  private def getParamMapFromPostRequest(request: Request[AnyContent]): Map[String, Seq[String]] = {
    request.body.asFormUrlEncoded.getOrElse(Map())
  }
}
