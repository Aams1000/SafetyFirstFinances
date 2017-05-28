package models.charts

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode}

/**
  * Created by Andrew on 5/7/17.
  */
case class Histogram(titleAndAxesLabels: TitleAndAxesLabels, data: Seq[LabelAndDataValue]) extends Chart {

  def toJson(): String = {
    val mapper: ObjectMapper = new ObjectMapper()
    val retNode: ObjectNode = mapper.createObjectNode()
    val titleAndAxesLabelsNode: ObjectNode = mapper.createObjectNode()
    titleAndAxesLabelsNode.put("title", titleAndAxesLabels.title)
    titleAndAxesLabelsNode.put("x", titleAndAxesLabels.x)
    titleAndAxesLabelsNode.put("y", titleAndAxesLabels.y)

    val dataArrayNode: ArrayNode = mapper.createArrayNode()
    data.foreach(labelAndDataValue => {
      val dataPointNode: ObjectNode = mapper.createObjectNode()
      dataPointNode.put("label", labelAndDataValue.label)
      dataPointNode.put("value", labelAndDataValue.value)
      dataArrayNode.add(dataPointNode)
    })
    retNode.set("titlesAndAxesLabels", titleAndAxesLabelsNode)
    retNode.set("labelsAndDataValues", dataArrayNode)
    retNode.toString
  }
}
