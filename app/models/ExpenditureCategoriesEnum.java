package models;

/**
 * Created by Andrew on 2/5/17.
 */
@Deprecated
public enum ExpenditureCategoriesEnum {

    GROCERIES("Groceries"),
    RESTAURANTS("Restaurants"),
    TRAVEL("Travel"),
    VACATION("Vacation"),
    GIFTS("Gifts"),
    MISCELLANEOUS("Miscellaneous"),
    PERSONAL_EXPENDITURES("Personal Expenditures"),
    PHARMACY_AND_HOME_SUPPLIES("Pharmacy & Home Supplies"),
    NEWSPAPERS_AND_PUBLICATIONS("Newspapers & Publications"),
    ENTERTAINMENT("Entertainment"),
    HEALTH_AND_FITNESS("Health & Fitness"),
    VIDEO_GAMES("Video Games"),
    HOUSING_AND_RENT("Housing & Rent"),
    CLOTHING("Clothing"),
    CHARITY("Charity"),
    INVESTMENTS("Investments"),
    CASH_EXPENDITURES("Cash Expenditures"),
    EDUCATION("Education"),
    INSURANCE("Insurance"),
    MEDICAL_EXPENSES("Medical Expenses");

    private String name;

    ExpenditureCategoriesEnum(final String name){
        this.name = name;
    }
}
