package com.timain.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义导出Excel数据注解
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/18 0018 18:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

    /**
     * 导出时在excel中排序
     * @return
     */
    int sort() default Integer.MAX_VALUE;

    /**
     * 导出到Excel中的名字
     * @return
     */
    String name() default "";

    /**
     * 日期格式, 如: yyyy-MM-dd
     * @return
     */
    String dateFormat() default "";

    /**
     * 如果是字典类型，请设置字典的type值 (如: sys_user_sex)
     * @return
     */
    String dictType() default "";

    /**
     * 读取内容转表达式 (如: 0=男,1=女,2=未知)
     * @return
     */
    String readConverterExp() default "";

    /**
     * 分隔符，读取字符串组内容
     * @return
     */
    String separator() default ",";

    /**
     * 导出类型（0数字 1字符串）
     * @return
     */
    ColumnType cellType() default ColumnType.STRING;

    /**
     * 导出时在excel中每个列的高度 单位为字符
     * @return
     */
    double height() default 14;

    /**
     * 导出时在excel中每个列的宽 单位为字符
     * @return
     */
    double width() default 16;

    /**
     * 文字后缀,如% 90 变成90%
     * @return
     */
    String suffix() default "";

    /**
     * 当值为空时,字段的默认值
     * @return
     */
    String defaultValue() default "";

    /**
     * 提示信息
     * @return
     */
    String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容
     * @return
     */
    String[] combo() default {};

    /**
     * 是否导出数据,应对需求:有时我们需要导出一份模板,这是标题需要但内容需要用户手工填写
     * @return
     */
    boolean isExport() default true;

    /**
     * 另一个类中的属性名称,支持多级获取,以小数点隔开
     * @return
     */
    String targetAttr() default "";

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     * @return
     */
    Type type() default Type.ALL;

    enum Type {
        ALL(0),
        EXPORT(1),
        IMPORT(2);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        int value() {
            return this.value;
        }
    }

    enum ColumnType {
        NUMERIC(0),
        STRING(1);

        private final int value;

        ColumnType(int value) {
            this.value = value;
        }

        int value() {
            return this.value;
        }
    }
}
