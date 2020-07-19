package com.timain.system.utils;

import com.timain.common.constant.Constants;
import com.timain.common.utils.CacheUtils;
import com.timain.common.utils.StringUtils;
import com.timain.system.domain.SysDictData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字典工具类
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/19 0019 12:19
 */
@Component
public class DictUtils {

    /**
     * 分隔符
     */
    private static final String SEPARATOR = ",";

    /**
     * 设置字典缓存
     * @param key  参数键
     * @param dictDataList 字典数据列表
     */
    public static void setDictCache(String key, List<SysDictData> dictDataList) {
        CacheUtils.put(getCacheName(), getCacheKey(key), dictDataList);
    }

    /**
     * 获取字典缓存
     * @param key 参数键
     * @return 字典数据列表
     */
    public static List<SysDictData> getDictCache(String key) {
        Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            List<SysDictData> dictDataList = StringUtils.cast(cacheObj);
            return dictDataList;
        }
        return null;
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue) {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel) {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator) {
        StringBuilder sb = new StringBuilder();
        List<SysDictData> dictDataList = getDictCache(dictType);
        if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(dictDataList)) {
            for (SysDictData sysDictData : dictDataList) {
                for (String value : dictValue.split(separator)) {
                    if (value.equals(sysDictData.getDictValue())) {
                        sb.append(sysDictData.getDictLabel() + separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDictData sysDictData : dictDataList) {
                if (dictValue.equals(sysDictData.getDictValue())) {
                    return sysDictData.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(sb.toString(), separator);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator) {
        StringBuilder sb = new StringBuilder();
        List<SysDictData> dictDataList = getDictCache(dictType);
        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(dictDataList)) {
            for (SysDictData sysDictData : dictDataList) {
                for (String label : dictLabel.split(separator)) {
                    if (label.equals(sysDictData.getDictLabel())) {
                        sb.append(sysDictData.getDictValue() + separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDictData sysDictData : dictDataList) {
                if (dictLabel.equals(sysDictData.getDictLabel())) {
                    return sysDictData.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(sb.toString(), separator);
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache() {
        CacheUtils.removeAll(getCacheName());
    }

    /**
     * 获取cache name
     * @return 缓存名
     */
    public static String getCacheName() {
        return Constants.SYS_DICT_CACHE;
    }

    /**
     * 设置cache key
     * @param configKey  参数键
     * @return  缓存键key
     */
    public static String getCacheKey(String configKey) {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
