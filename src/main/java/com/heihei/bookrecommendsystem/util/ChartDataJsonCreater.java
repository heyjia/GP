package com.heihei.bookrecommendsystem.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;

public class ChartDataJsonCreater {
    public static String getPieJson(double[] data,String [] backgroundColor,String[] labels,String title,String position) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //添加数据
        ArrayNode dataList = mapper.createArrayNode();
        for (double datum : data) {
            dataList.add(datum);
        }

        ArrayNode backgroundColorList = mapper.createArrayNode();
        for (String color : backgroundColor) {
            backgroundColorList.add(color);
        }

        ArrayNode labelsList = mapper.createArrayNode();
        for (String label : labels) {
            labelsList.add(label);
        }

        ObjectNode dataSetNode = mapper.createObjectNode();
        dataSetNode.put("data",dataList);
        dataSetNode.put("backgroundColor",backgroundColorList);
        ArrayNode dataSetsList = mapper.createArrayNode();
        dataSetsList.add(dataSetNode);
        ObjectNode dataRoot = mapper.createObjectNode();
        dataRoot.put("datasets", dataSetsList);
        dataRoot.put("labels", labelsList);
//		System.out.println(mapper.writeValueAsString(datasetsList));

        ObjectNode positionNode = mapper.createObjectNode();
        positionNode.put("position", position);

        ObjectNode titleNode = mapper.createObjectNode();
        titleNode.put("display", true);
        titleNode.put("text", title);

        ObjectNode optionsNode = mapper.createObjectNode();
        optionsNode.put("legend", positionNode);
        optionsNode.put("title", titleNode);
        optionsNode.put("responsive", true);

        // 1级孩子
        ObjectNode root = mapper.createObjectNode();
        root.put("type", "pie");
        root.put("data", dataRoot);
        root.put("options", optionsNode);

        return mapper.writeValueAsString(root);
    }
}
