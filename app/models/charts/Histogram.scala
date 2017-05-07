package models.charts

/**
  * Created by Andrew on 5/7/17.
  */
case class Histogram(titleAndAxesLabels: TitleAndAxesLabels, data: Seq[LabelAndDataValue]) extends Chart {

}
