package com.saith.dota;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.saith.dota.config.DotaUnit;
import com.saith.dota.utils.CommonUtils;

import java.nio.file.InvalidPathException;
import java.util.*;

/**
 * @author zhangjiawen
 * @version 1.0
 * @date 2021/1/25 2:06 下午
 */
public class AiExp {

    public static Map<String, String> BASE_MAP = new HashMap<>();

    static {
        BASE_MAP.put("1_1", "69");
        BASE_MAP.put("1_2", "43");
        BASE_MAP.put("1_3", "52");

        BASE_MAP.put("2_1", "22");
        BASE_MAP.put("2_2", "19");
        BASE_MAP.put("2_3", "25");

        BASE_MAP.put("3_1", "22");
        BASE_MAP.put("3_2", "19");
        BASE_MAP.put("3_3", "25");

        BASE_MAP.put("4_1", "57");
        BASE_MAP.put("4_2", "34");
        BASE_MAP.put("4_3", "39");

        BASE_MAP.put("5_1", "25");
        BASE_MAP.put("5_2", "-1");
        BASE_MAP.put("5_3", "26");

        BASE_MAP.put("6_1", "25");
        BASE_MAP.put("6_2", "20");
        BASE_MAP.put("6_3", "26");

        BASE_MAP.put("7_1", "88");
        BASE_MAP.put("7_2", "59");
        BASE_MAP.put("7_3", "72");

        BASE_MAP.put("8_1", "88");
        BASE_MAP.put("8_2", "59");
        BASE_MAP.put("8_3", "72");

        BASE_MAP.put("9_1", "88");
        BASE_MAP.put("9_2", "59");
        BASE_MAP.put("9_3", "72");
    }

    public static void main(String[] args) {
        System.out.println("欢迎使用 AI修改倍率脚本...");
//        String[] argv = {"-input", "/Users/saith/GitHub/dota_ai/src/main/resources/dota/npc_units.txt", "-multiple", "4"};
        DotaUnit dotaUnit = parseCommand(args, DotaUnit.class);
        if (dotaUnit == null) {
            return;
        }

        String fileName = "";
        try {
            fileName = CommonUtils.getFileName(dotaUnit.getInputPath());
        } catch (InvalidPathException e) {
            System.out.println("文件不存在: " + dotaUnit.getInputPath());
        }

        System.out.println("修改为倍率: " + dotaUnit.getMultiple());

        List<String> unitList = CommonUtils.readList(dotaUnit.getInputPath());
        //按行号获取
        parseLine(unitList.subList(512, 515), "1", dotaUnit);
        parseLine(unitList.subList(605, 608), "2", dotaUnit);
        parseLine(unitList.subList(698, 701), "3", dotaUnit);

        parseLine(unitList.subList(1070, 1075), "4", dotaUnit);
        parseLine(unitList.subList(1163, 1166), "5", dotaUnit);
        parseLine(unitList.subList(1256, 1260), "6", dotaUnit);

        parseLine(unitList.subList(4912, 4918), "7", dotaUnit);
        parseLine(unitList.subList(5009, 5012), "8", dotaUnit);
        parseLine(unitList.subList(5102, 5108), "9", dotaUnit);

        String outputPath = Optional.ofNullable(dotaUnit.getOutputPath()).orElse(fileName);
        CommonUtils.writeToList(outputPath, unitList);

        System.out.println("写入成功: " + outputPath);
    }

    private static void parseLine(List<String> baseLine, String base, DotaUnit dotaUnit) {
        Double multiple = dotaUnit.getMultiple();
        for (int i = 0; i < baseLine.size(); ++i) {
            String baseStr = baseLine.get(i);
            if (baseStr.contains("BountyXP")) {
                String baseNum = BASE_MAP.get(base + "_" + "1");
                if (Objects.equals(baseNum, "-1")) {
                    continue;
                }
                String replace = baseStr.replaceAll("\\d+", String.valueOf(Math.round(Integer.parseInt(baseNum) * multiple)));
                System.out.println("当前行: " + baseStr + "\t  写入行:" + replace);
                baseLine.set(i, replace);
            } else if (baseStr.contains("BountyGoldMin")) {
                String baseNum = BASE_MAP.get(base + "_" + "2");
                if (Objects.equals(baseNum, "-1")) {
                    continue;
                }
                String replace = baseStr.replaceAll("\\d+", String.valueOf(Math.round(Integer.parseInt(baseNum) * multiple)));
                System.out.println("当前行: " + baseStr + "\t  写入行:" + replace);
                baseLine.set(i, replace);
            } else if (baseStr.contains("BountyGoldMax")) {
                String baseNum = BASE_MAP.get(base + "_" + "3");
                if (Objects.equals(baseNum, "-1")) {
                    continue;
                }
                String replace = baseStr.replaceAll("\\d+", String.valueOf(Math.round(Integer.parseInt(baseNum) * multiple)));
                System.out.println("当前行: " + baseStr + "\t  写入行:" + replace);
                baseLine.set(i, replace);
            }
        }
    }


    private static <T> T parseCommand(String[] args, Class<T> tClass) {
        JCommander commander = JCommander.newBuilder().build();
        try {
            T unit = tClass.newInstance();
            commander.addObject(unit);
            commander.parse(args);
            return unit;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParameterException e) {
            e.printStackTrace();
            commander.usage();
        }
        return null;
    }

}
