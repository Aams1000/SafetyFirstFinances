package models

/**
  * Created by Andrew on 2/5/17.
  */
sealed case class ExpenditureCategory(name: String)

object ExpenditureCategories {
  object GROCERIES extends ExpenditureCategory("Groceries")
  object RESTAURANTS extends ExpenditureCategory("Restaurants")
  object TRAVEL extends ExpenditureCategory("Travel")
  object VACATION extends ExpenditureCategory("Vacation")
  object GIFTS extends ExpenditureCategory("Gifts")
  object MISCELLANEOUS extends ExpenditureCategory("Miscellaneous")
  object PERSONAL_EXPENDITURES extends ExpenditureCategory("Personal Expenditures")
  object PHARMACY_AND_HOME_SUPPLIES extends ExpenditureCategory("Pharmacy & Home Supplies")
  object NEWSPAPERS_AND_PUBLICATIONS extends ExpenditureCategory("Newspapers & Publications")
  object ENTERTAINMENT extends ExpenditureCategory("Entertainment")
  object HEALTH_AND_FITNESS extends ExpenditureCategory("Health & Fitness")
  object VIDEO_GAMES extends ExpenditureCategory("Video Games")
  object HOUSING_AND_RENT extends ExpenditureCategory("Housing & Rent")
  object CLOTHING extends ExpenditureCategory("Clothing")
  object CHARITY extends ExpenditureCategory("Charity")
  object INVESTMENTS extends ExpenditureCategory("Investments")
  object CASH_EXPENDITURES extends ExpenditureCategory("Cash Expenditures")
  object INSURANCE extends ExpenditureCategory("Insurance")
  object MEDICAL extends ExpenditureCategory("Medical Expenses")
  object UTILTIES extends ExpenditureCategory("Internet & Utilities")

  @throws(classOf[IllegalArgumentException])
  def fromStringThrowsException(name: String): ExpenditureCategory = {
    name match {
      case GROCERIES.name => GROCERIES
      case RESTAURANTS.name => RESTAURANTS
      case TRAVEL.name => TRAVEL
      case VACATION.name => VACATION
      case GIFTS.name => GIFTS
      case MISCELLANEOUS.name => MISCELLANEOUS
      case PERSONAL_EXPENDITURES.name => PERSONAL_EXPENDITURES
      case PHARMACY_AND_HOME_SUPPLIES.name => PHARMACY_AND_HOME_SUPPLIES
      case NEWSPAPERS_AND_PUBLICATIONS.name => NEWSPAPERS_AND_PUBLICATIONS
      case ENTERTAINMENT.name => ENTERTAINMENT
      case HEALTH_AND_FITNESS.name => HEALTH_AND_FITNESS
      case VIDEO_GAMES.name => VIDEO_GAMES
      case HOUSING_AND_RENT.name => HOUSING_AND_RENT
      case CLOTHING.name => CLOTHING
      case CHARITY.name => CHARITY
      case INVESTMENTS.name => INVESTMENTS
      case CASH_EXPENDITURES.name => CASH_EXPENDITURES
      case INSURANCE.name => INSURANCE
      case MEDICAL.name => MEDICAL
      case UTILTIES.name => UTILTIES
      case _ => throw new IllegalArgumentException("Illegal ExpenditureCategory name " + name + " is not a valid category.")
    }
  }
}
